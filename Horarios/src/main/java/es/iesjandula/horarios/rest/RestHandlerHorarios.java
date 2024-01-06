package es.iesjandula.horarios.rest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.horarios.constants.Constantes;
import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.Alumno;
import es.iesjandula.horarios.models.Puntos;
import es.iesjandula.horarios.models.csv.ModelCSV;
import es.iesjandula.horarios.models.xml.Aula;
import es.iesjandula.horarios.models.xml.Centro;
import es.iesjandula.horarios.models.xml.Profesor;
import es.iesjandula.horarios.utils.ICSVParser;
import es.iesjandula.horarios.utils.IChecker;
import es.iesjandula.horarios.utils.IParserXML;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 *
 */
@RestController
@RequestMapping( value = "/horarios",produces = "application/json")
public class RestHandlerHorarios implements IParserXML,ICSVParser,IChecker
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	/**Datos del xml parseado */
	private Centro centro;
	/**Modelos del csv para guardarlos en sesion */
	private List <ModelCSV> modelos;
	/**Lista de alumnos cargados por csv */
	private List<Alumno> alumnos;
	/**Lista de puntos cargados en constantes */
	private List<Puntos> puntos;
	/**
	 * Constructor por defecto
	 */
	public RestHandlerHorarios() 
	{
		//public constructor
		this.centro = null;
		this.modelos = new LinkedList<ModelCSV>();
		this.alumnos = new LinkedList<Alumno>();
		this.puntos = new LinkedList<Puntos>();
	}
	/**
	 * Endpoint que recibe un xml como parametro con el que se recogen todos los datos de un centro
	 * @param xml
	 * @return Informacion del xml parseada o un error en caso de que este mal compuesto
	 * @author Javier Martinez Megias
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = "multipart/form-data")
    public ResponseEntity<?> parseXML(@RequestPart(value = "xml",required = true)MultipartFile xml)
    {
		try
		{
			this.checkIfEmptyAndXml(xml);
			centro = this.parserFileToObject(xml);
			return ResponseEntity.ok(centro.getDatos());
		}
		catch (HorarioError exception)
		{
			String error = "Is not a xml or is empty";
			HorarioError horarioError = new HorarioError(404, error);
			return ResponseEntity.status(404).body(horarioError.getBodyMessageException());
		}
        catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	/**
	 * Endpoint que recibe un fichero csv y lo parsea internamente en sesion
	 * @param csvFile fichero csv que recibe el servidor
	 * @return ok si el fichero cumple las condiciones,404 si el formato o la estructura esta mal o 500 si hubo un error interno
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/send/csv" ,consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvTo(@RequestPart(value = "fichero",required = true) final MultipartFile csvFile)
	{
		try
		{
			//Se comprueba que el fichero sea csv y cumpla con la estructura establecida
			boolean isTeacher = this.checkCSVFile(csvFile);
			if(!isTeacher)
			{
				log.error("El csv pasado no corresponde al de los profesores");
				throw new HorarioError(5,"Error la estructura del csv no es correcta, los campos principales han de ser apellidos,nombre, apellidos, email y roles");
			}
			//Parseamos el fichero y guardamos los datos
			this.modelos = this.parseCSV();
			return ResponseEntity.ok().body("Fichero recibido con exito");
		}
		catch(HorarioError ex)
		{
			log.error("Error a la hora de parsear el fichero",ex);
			return ResponseEntity.status(415).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error interno de servidor",ex);
			return ResponseEntity.status(500).body("Error interno de servidor");
		}
	}
	/**
	 * Endpoint que recibe un csv de alumnos y lo parsea internamente
	 * @param csvFile
	 * @return ok si el fichero cumple las condiciones,404 si el formato o la estructura esta mal o 500 si hubo un error interno
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.POST,value = "send/csv-alumnos",consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvStudents(@RequestPart(value = "fichero",required = true) final MultipartFile csvFile)
	{
		try
		{
			//Se comprueba que el fichero sea csv y cumpla con la estructura establecida
			//Ademas se añade un booleano para comprobar que el ficehero sea el de estudiantes
			//Ya que este metodo se usa en el endpoint de profesores
			boolean isStudent = this.checkCSVFile(csvFile);
			if(isStudent)
			{
				log.error("El csv pasado no corresponde al de los alumnos");
				throw new HorarioError(5,"Error la estructura del csv no es correcta, los campos principales han de ser apellidos,nombre,dni/pasaporte y curso");
			}
			//Parseamos el fichero y guardamos los datos
			this.alumnos = this.parseAlumnos();
			return ResponseEntity.ok().body("Alumnos cargados con exito");
		}
		catch(HorarioError ex)
		{
			log.error("Error a la hora de parsear el fichero",ex);
			return ResponseEntity.status(415).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error interno de servidor",ex);
			return ResponseEntity.status(500).body("Error interno de servidor");
		}
	}
	/**
	 * Endpoint que recibe un email y busca a partir de el la lista de roles de la cuenta asociada
	 * @param email de la cuenta a buscar 
	 * @return ok si encontro los roles, 404 si el email no es valido o 500 si hubo un error interno
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/get/roles")
	public ResponseEntity<?> getRoles(@RequestHeader(value = "email",required = true) final String email)
	{
		try
		{
			//Comprobacion del email y si existe en los modelos
			this.checkParam(email, this.modelos);
			//Obtencion de los roles
			String [] roles = this.searchRol(email, modelos);
			return ResponseEntity.ok().body(roles);
		}
		catch(HorarioError ex)
		{
			log.error("Error en el email o al buscar el modelo",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error interno de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor");
		}
	}
	/**
	 * Endpoint que devuelve una lista de alumnos ordenados
	 * @return Lista de alumnos ordenados
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/sort-students")
	public ResponseEntity<?> getListStudentsAlphabetically()
	{
		try
		{
			Alumno [] alumnosToSort = null;
			//Se ordena y se devuelven en fotmato json
			alumnosToSort = this.sortAlumno(alumnos);
			Arrays.sort(alumnosToSort);
			return ResponseEntity.ok().body(alumnosToSort);
		}
		catch(HorarioError ex)
		{
			log.error("Los alumnos no han sido cargados anteriormente",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error de servidor",ex);
			return ResponseEntity.status(500).body("Error de servidor "+ex.getMessage());
		}
	}
	
	/**
	 * Enpoint que devuelve el aula en la que imparte clase un profesor usando sus nombres apellidos y el dia que el usuario elija
	 * @param profesorName
	 * @param profesorSurname
	 * @param dia
	 * @return Aula correspondiente al profesor o un valor nulo si los parametros estan mal o un error por error de servidor o la causa anterior
	 * @author Javier Martinez Megias
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/classroom", produces = "application/json")
    public ResponseEntity<?> getClassroomTeacher(
            @RequestHeader(value= "profesorName", required = true) String profesorName,
            @RequestHeader (value= "profesorSurname", required = true)String profesorSurname,
            @RequestHeader (value= "dia", required = false)Integer dia)
    {
		try
		{			
			//Comprobamos que los parametros esten bien compuestos y que el profesor exista
			this.checkNameSurnameDay(profesorName, profesorSurname, dia, centro.getDatos().getProfesor());
			Aula aula = this.getAulaByProfesorName(profesorName, profesorSurname, dia, this.centro, null);
			if (aula == null)
			{
				//Si no encuentra el aula devuelve un error
				log.warn("No se ha encontrado el aula");
				throw new HorarioError(17, "No se ha encontrado el aula");
			}
			else
			{
				//Si la encuentra la devuelve
				return ResponseEntity.ok().body(aula); 
			}
						
		}
		catch(HorarioError exception)
		{
			log.error("Error en uno de los parametros o profesor no encontrado",exception);
			return ResponseEntity.status(404).body(exception.getBodyMessageException());
		}
		catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	
	/**
	 * Endpoint que devuelve un aula pasandole un profesor y un tramo horario, este endpoint deriva de {@link #getClassroomTeacher(String, String, Integer)}
	 * @param profesorName
	 * @param profesorSurname
	 * @param time
	 * @param dia
	 * @return ok si los parametros estan bien introducidos con un aula como json, 404 si los parametros fallan o 500 si hay un error de servidor
	 * @author Pablo Ruiz Canovas 
	 * @see derivacion {@link #getClassroomTeacher(String, String, Integer)}
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/classroom/{time}")
	public ResponseEntity<?> getClassroomTeacherTime(
			@RequestHeader(value = "profesorName",required = true)String profesorName,
			@RequestHeader(value = "profesorSurname",required = true)String profesorSurname,
			@PathVariable(name = "time",value = "time",required = true)String time,
			@RequestHeader(value = "dia",required = false)Integer dia
			)
	{
		try
		{
			//Comprobamos que la hora este bien compuesta
			this.checkHora(time);
			//Comprobamos que el profesor exista 
			this.checkNameSurnameDay(profesorName, profesorSurname, dia, centro.getDatos().getProfesor());
			//Obtenemos el aula a partir de los parametros introducidos 
			Aula aula = this.getAulaByProfesorName(profesorName, profesorSurname, dia, this.centro, time);
			if (aula == null)
			{
				//Si no encuentra el aula devuelve un error
				log.warn("No se ha encontrado el aula");
				throw new HorarioError(17, "No se ha encontrado el aula");
			}
			else
			{
				//Si la encuentra la devuelve
				return ResponseEntity.ok().body(aula); 
			}
		}
		catch(HorarioError ex)
		{
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(500).body("Server error "+ex.getMessage());
		}
	}
	
	/**
	 * Endpoint que devuelve un mapa en el que por cada campo se encuentran las diferentes horas
	 * @return el mapa de horas
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/hours")
	public ResponseEntity<?> getHours()
	{
		try
		{
			Map<String,List<String>> horario = Constantes.cargarHoras();
			return ResponseEntity.ok().body(horario);
		}
		catch(Exception ex)
		{
			log.error("Error al cargar las listas ",ex);
			return ResponseEntity.status(500).body("Error de servidor "+ex.getMessage());
		}
	}
	/**
	 * Endpoint que mediante un curso filtra los alumnos guardados en el sistema y los ordena por apellido
	 * @param course
	 * @return ok mas la lista de alumnos si el curso esta bien introducido, 404 si el curso falla o 500 si hubo un error de servidor
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.GET, value = "get/sort-students/{course}")
	public ResponseEntity<?> getStudentByCourse(
			@PathVariable(name = "course",value = "course",required = true) String course
			)
	{
		try
		{
			Alumno [] alumnoSort = null;
			//Comprobamos que el curso exista y que se encuentra en la lista de alumnos
			this.checkCourse(alumnos, course);
			//Metemos los alumnos en el array y los ordenamos
			alumnoSort = this.getAlumnoByCourse(alumnos, course);
			//Ordenamos la lista y la devolvemos
			Arrays.sort(alumnoSort);
			return ResponseEntity.ok().body(alumnoSort);
		}
		catch(HorarioError ex)
		{
			log.error("Error al buscar el curso "+course,ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error interno de servidor");
			return ResponseEntity.status(500).body("Error de servidor "+ex);
		}
	}
	/**
	 * Enspoint que devuelve una lista de puntos almacenada en el sistema
	 * @return ok con la lista de puntos o 500 si hubo un error de servidor
	 * @author Pablo Ruiz Canovas
	 */
	@RequestMapping(method = RequestMethod.GET, value = "get/points")
	public ResponseEntity<?> getPoints()
	{
		try
		{
			//Cargamos los puntos en la lista usando la clase constantes
			this.puntos = Constantes.cargarPuntos();
			return ResponseEntity.ok().body(puntos); 
		}
		catch(Exception ex)
		{
			log.error("Error al cargar los puntos",ex);
			return ResponseEntity.status(500).body("Error de servidor"+ex);
		}
	}
	/**
	 * Endpoint que devuelve una lista de todos profesores
	 * @return Lista de todos los profesores
	 * @author Javier Martinez Megias
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/allTeachers", produces = "application/json")
    public ResponseEntity<?> getProfesores()
    {
		try
		{		
			//Se recogen los profesores de los datos del xml
			List<Profesor> profesores = centro.getDatos().getProfesor();
			return ResponseEntity.ok().body(profesores);    	
		}
		catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	
	/**
	 * Obtienes el profesor y la asignatura que tiene ese grupo ese dia a esa hora
	 * @param nombreDelCurso
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/teacher-subject")
    public ResponseEntity<?> getNombreProfesorAsignatura(
            @RequestHeader(value= "nombreDelCurso", required = true) String nombreDelCurso)
    {
		try
		{		
			
			String result = this.nombreProfesorAsignatura(nombreDelCurso, centro);
			if(result.equals(""))
			{
				throw new HorarioError(1, "No se imparte ninguna asignatura a ese grupo ahoramismo");
			}
			return ResponseEntity.ok().body(result);    	
		}
		catch (HorarioError exception)
		{
			String error = "No se imparte ninguna asignatura a ese grupo ahoramismo";
			HorarioError horarioError = new HorarioError(404, error);
			return ResponseEntity.status(404).body(horarioError.getBodyMessageException());
		}
		catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	
	
	
	/**
	 * Obtienes el aula que tiene ese grupo ese dia a esa hora
	 * @param nombreDelCurso
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/aula")
    public ResponseEntity<?> getAula(
            @RequestHeader(value= "nombreDelCurso", required = true) String nombreDelCurso)
    {
		try
		{		
			//Se obtiene el aula
			String result = this.nombreAula(nombreDelCurso, centro);
			// Se comprueba que no sea nulo

			if(result.equals(""))
			{
				throw new HorarioError(1, "No se encuentra en ningun aula ahoramismo");
			}
			return ResponseEntity.ok().body(result);    	
		}
		catch (HorarioError exception)
		{
			String error = "No se encuentra en ningun aula ahoramismo";
			HorarioError horarioError = new HorarioError(404, error);
			return ResponseEntity.status(404).body(horarioError.getBodyMessageException());
		}
		catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	
	
		
}