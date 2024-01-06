package es.iesjandula.horarios.rest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.xml.Actividad;
import es.iesjandula.horarios.models.xml.Asignatura;
import es.iesjandula.horarios.models.xml.Aula;
import es.iesjandula.horarios.models.xml.Centro;
import es.iesjandula.horarios.models.xml.Grupo;
import es.iesjandula.horarios.models.xml.Profesor;
import es.iesjandula.horarios.models.xml.TipoHorario;
import es.iesjandula.horarios.models.xml.Tramo;
import es.iesjandula.horarios.utils.ParserXml;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 *
 */
@RestController
@RequestMapping( value = "/horarios",produces = "application/json")
public class RestHandlerHorarios implements ParserXml 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	private static Centro centro= null;
	/**
	 * Constructor por defecto
	 */
	public RestHandlerHorarios() 
	{	
		
	}
	
	/**
	 * 
	 * @param xml
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = "multipart/form-data")
    public ResponseEntity<?> parseXML(
            @RequestPart MultipartFile xml)
    {
		try
		{
			if(chekIfEmptyAndXml(xml))
			{
				centro = this.parserFileToObject(xml);
				return ResponseEntity.ok(centro.getDatos());
	    	}
			else
			{
				throw new HorarioError(1,"El fichero tiene que ser xml");
			}
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
	 * @param xml
	 * @return
	 */
	private boolean chekIfEmptyAndXml(MultipartFile xml)
	{
		if(!xml.isEmpty())
		{
			
				return true;
			
		}
		return false;
	}
	
	/**
	 * 
	 * @param profesorName
	 * @param profesorSurname
	 * @param dia
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/classroom", produces = "multipart/form-data")
    public ResponseEntity<?> getClassroomTeacher(
            @RequestHeader(value= "profesorName", required = true) String profesorName,
            @RequestHeader (value= "profesorSurname", required = true)String profesorSurname,
            @RequestHeader (value= "dia", required = false)Integer dia)
    {
		try
		{		
				return ResponseEntity.ok(getAulaByProfesorName(profesorName, profesorSurname, dia));    	
		}
		catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	
	
	private String getAulaByProfesorName(String name, String surneme, Integer dia)
	{	
		String horaActual = getActualHour();
		
		if(dia == null)
		{
			dia = getDay();
		}
		
		List<Profesor> listaProfesores = centro.getDatos().getProfesor();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		List<TipoHorario> listaHorarioProf = centro.getDatos().getHorarios().getHorarioProfesores();
		List<Aula> listaGrupos = centro.getDatos().getAula();
		
		int numeroProfesor = 0;
		int numeroTramo = 0;
		int aula = 0;
		for(Profesor x : listaProfesores)
		{
			if(x.getNombre().equalsIgnoreCase(surneme+", "+name))
			{
				numeroProfesor=x.getNum_int_pr();
			}
		}
		numeroTramo = getTramoHorario(dia, horaActual, listaTramos);
		for(TipoHorario x :  listaHorarioProf)
		{
			if(x.getHor_num_int_typo() == numeroProfesor)
			{
				for(Actividad y : x.getActividades())
				{
					if(y.getTramo() == numeroTramo)
					{
						aula = y.getProfesorOAula();
					}
				}
			}
		}
		for(Aula x : listaGrupos)
		{
			if(x.getNum_int_au() == aula)
			{
				return x.getNombre();
			}
		}
		
		
		return "No se ecuentra en ningun aula";	
		
	}

	/**
	 * @param dia
	 * @param horaActual
	 * @param listaTramos
	 * @param numeroTramo
	 * @return
	 */
	private int getTramoHorario(Integer dia, String horaActual, List<Tramo> listaTramos)
	{
		int numeroTramo = 0;
		for(Tramo x : listaTramos)
		{
			String horaFinal = x.getHora_final();
			String horaInicio = x.getHora_inicio();
			int menorHoraFinal = horaFinal.compareTo(horaActual);
			int mayorHoraInicial = horaInicio.compareTo(horaActual);
			
			if(menorHoraFinal >= 0 && mayorHoraInicial <= 0 && x.getNumero_dia() == dia)
			{
				numeroTramo = x.getNum_tr();
			}
		}
		return numeroTramo;
	}

	/**
	 * Return the actual day
	 * @return integer
	 */
	private Integer getDay()
	{
		Integer dia;
		LocalDate date = LocalDate.now()  ;
		DayOfWeek day = date.getDayOfWeek();
		dia = day.getValue();
		return dia;
	}

	private String getActualHour()
	{
		
		LocalDateTime locaDate = LocalDateTime.now();
		int hours  = locaDate.getHour();
		int minutes = locaDate.getMinute();
		
		return  hours  +":"+ minutes ;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/allTeachers", produces = "application/json")
    public ResponseEntity<?> getProfesores()
    {
		try
		{		
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/teacher-subject")
    public ResponseEntity<?> getNombreProfesorAsignatura(
            @RequestHeader(value= "nombreDelCurso", required = true) String nombreDelCurso)
    {
		try
		{		
			String result = this.nombreProfesorAsignatura(nombreDelCurso);
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
	
	private String nombreProfesorAsignatura(String nombreDelCurso)
	{
		String hora = this.getActualHour();
		int dia = this.getDay();
		
		List<TipoHorario> listaHorarios = centro.getDatos().getHorarios().getHorarioGrupo();
		List<Grupo> listaGrupos = centro.getDatos().getGrupo();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		List<Profesor> listaProfesores = centro.getDatos().getProfesor();
		List<Asignatura> listaAsignaturas = centro.getDatos().getAsignatura();
		Integer numeroTramo = 0;
		Integer numeroGrupo = 0;
		Integer numeroProfesor = 0;
		Integer numeroAsignatura = 0;
		String nombreProfesor = "";
		String nombreAsignatura = "";
		String result = "";
		numeroTramo = getTramoHorario(dia, hora, listaTramos);
		
		for(Grupo grupo : listaGrupos)
		{
			if(nombreDelCurso.equals(grupo.getNombre()))
			{
				numeroGrupo = grupo.getNum_int_gr();
			}
		}
		
		for(TipoHorario tipoHorario : listaHorarios)
		{
			if(numeroGrupo == tipoHorario.getHor_num_int_typo())
			{
				for(Actividad actividad : tipoHorario.getActividades())
				{
					if(actividad.getTramo() == numeroTramo)
					{
						numeroProfesor = actividad.getProfesorOAula();
						numeroAsignatura = actividad.getAulaOAsignatura();
					}
				}
			}
		}
		
		for(Profesor profesor : listaProfesores)
		{
			if(profesor.getNum_int_pr() == numeroProfesor)
			{
				nombreProfesor = profesor.getNombre();
			}
		}
		
		for(Asignatura asignatura : listaAsignaturas)
		{
			if(asignatura.getNum_int_as() == numeroAsignatura)
			{
				nombreAsignatura = asignatura.getNombre();
			}
		}
		
		if(!nombreAsignatura.equals("") && !nombreProfesor.equals(""))
		{
			result = "Profesor: "+nombreProfesor+"\nAsignatura: "+nombreAsignatura+"\nDia: "+dia+"\nHora: "+hora;
		}
		
				
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/aula")
    public ResponseEntity<?> getAula(
            @RequestHeader(value= "nombreDelCurso", required = true) String nombreDelCurso)
    {
		try
		{		
			String result = this.nombreAula(nombreDelCurso);
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
	
	private String nombreAula(String nombreDelCurso)
	{
		String hora = this.getActualHour();
		int dia = 3;
		this.getDay();
		
		List<TipoHorario> listaHorarios = centro.getDatos().getHorarios().getHorarioGrupo();
		List<Grupo> listaGrupos = centro.getDatos().getGrupo();
		List<Aula> listaAulas = centro.getDatos().getAula();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		Integer numeroTramo = 0;
		Integer numeroGrupo = 0;
		Integer numeroAula = 0;
		String nombreAula = "";
		String abreviatura = "";
		String result = "";
		numeroTramo = getTramoHorario(dia, hora, listaTramos);
		
		for(Grupo grupo : listaGrupos)
		{
			if(nombreDelCurso.equals(grupo.getNombre()))
			{
				numeroGrupo = grupo.getNum_int_gr();
			}
		}
		
		for(TipoHorario tipoHorario : listaHorarios)
		{
			if(numeroGrupo == tipoHorario.getHor_num_int_typo())
			{
				for(Actividad actividad : tipoHorario.getActividades())
				{
					if(actividad.getTramo() == numeroTramo)
					{
						numeroAula = actividad.getProfesorOAula();
						
					}
				}
			}
		}
		
		for(Aula aula : listaAulas)
		{
			if(aula.getNum_int_au() == numeroAula)
			{
				nombreAula = aula.getNombre();
				abreviatura = aula.getAbreviatura();
			}
		}
		
		if(!nombreAula.equals("") && !abreviatura.equals(""))
		{
			result = "Nombre del Aula: "+nombreAula+"\nAula: "+abreviatura+"\nDia: "+dia+"\nHora: "+hora;
		}
		
				
		return result;
	}
}
