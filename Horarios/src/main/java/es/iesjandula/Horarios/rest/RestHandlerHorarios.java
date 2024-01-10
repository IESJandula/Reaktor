package es.iesjandula.Horarios.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.AttitudePoints;
import es.iesjandula.Horarios.models.Hour;
import es.iesjandula.Horarios.models.Student;
import es.iesjandula.Horarios.models.xml.Aula;
import es.iesjandula.Horarios.models.xml.Grupo;
import es.iesjandula.Horarios.models.xml.InfoCentro;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.TramoHorario;
import es.iesjandula.Horarios.models.xml.horarios.Actividad;
import es.iesjandula.Horarios.utils.HorariosUtils;
import es.iesjandula.Horarios.utils.XmlParser;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/horarios", produces =
{ "application/json" })
@RestController

/**
 * @author Alejandro Cazalla Pérez
 */
public class RestHandlerHorarios
{
	final static Logger logger = LogManager.getLogger();
	private List<String> registrosBano = new ArrayList<>();
	private List<AttitudePoints> listaPuntos = new ArrayList<AttitudePoints>(
			List.of(new AttitudePoints(75, "Agredir al profesor"), new AttitudePoints(25, "Movil"),
					new AttitudePoints(3, "Faltar el respeto a un compañero")));
	 private List<String> listaEstudiantes = new ArrayList<>();

	/**
	 * Public constructor
	 */
	public RestHandlerHorarios()
	{
		// Empty constructor
	}

//ENDPOINT 1
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = "multipart/form-data")
	public ResponseEntity<?> sendXmlToObject(@RequestPart(value = "xmlFile", required = true) final MultipartFile file,
			HttpSession session)
	{
		try
		{

			XmlParser parser = new XmlParser();
			InfoCentro info = parser.parseDataFromXmlFile(file);
			session.setAttribute("info", info);
			InfoCentro info2 = (InfoCentro) session.getAttribute("info");

			return ResponseEntity.ok().body(info2);
		} catch (HorarioError horarioError)
		{
			return ResponseEntity.status(400).body(horarioError.getBodyExceptionMessage());
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}

	}

	//ENDPOINT 2
	@RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvTo(@RequestPart(value = "csvFile", required = true) final MultipartFile csvFile,
			HttpSession session)
	{
		try
		{
			HorariosUtils horariosUtils = new HorariosUtils();
			List<Profesor> lista = horariosUtils.parseCsvFile(csvFile, session);
			System.out.println(lista);
			return ResponseEntity.ok().build();
		} catch (HorarioError exception)
		{
			String message = "Client error";
			logger.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyExceptionMessage());
		} catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	//ENDPOINT 3
	@RequestMapping(method = RequestMethod.GET, value = "/get/courses")
	public ResponseEntity<?> getListCourses(HttpSession httpSession)
	{
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			
			return ResponseEntity.ok().body(infoCentro.getDatos().getGrupos());
		} 
		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	//ENDPOINT 6
	@RequestMapping(method = RequestMethod.GET, value = "/get/sortstudents")
	public ResponseEntity<?> getListStudentsAlphabetically(HttpSession httpSession)
	{
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			Collections.sort(infoCentro.getDatos().getAlumnos());
			
			return ResponseEntity.ok().body(infoCentro.getDatos().getAlumnos());
		} 
		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	//ENDPOINT 7
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/classroom")
	public ResponseEntity<?> getClassroomTeacher(HttpSession httpSession,
			@RequestHeader(value = "name", required = true) String name,
			@RequestHeader(value = "lastName", required = true) String lastName)
	{
		try {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			
			Profesor profesor = this.getProfesor(name, lastName, infoCentro);
			
			TramoHorario tramoHorario = this.getTramo(new Date(), infoCentro);
			System.out.println(tramoHorario.getHoraInicio());
			
			if (tramoHorario != null)
			{
				
				Actividad actividad = this.getActividad(tramoHorario, infoCentro.getHorarios().getHorariosProfesores().get(profesor));
				
				Map<String, String> response = new TreeMap<String, String>();
				
				response.put("aula", actividad.getAula().getNombre());
				return ResponseEntity.ok().body(response);
			}
			
			return ResponseEntity.ok().build();
		}catch (HorarioError horarioError)
		{

			return ResponseEntity.status(400).body(horarioError);
		} catch (Exception e)
		{
			System.out.println(e.getStackTrace());
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	//ENDPOINT 4
	@RequestMapping(method = RequestMethod.GET, value = "/get/roles")
	public ResponseEntity<?> getRoles(@RequestHeader(value = "email", required = true) String email, HttpSession httpSession)
	{
		InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
		List<Profesor> profesoresList = new ArrayList<>(infoCentro.getDatos().getProfesores().values());
		try
		{

			return ResponseEntity.ok(this.getProfesorByEmail(email, profesoresList).getListaRoles());
		} catch (HorarioError horarioError)
		{

			return ResponseEntity.status(400).body(horarioError);
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}

	}

	private Profesor getProfesorByEmail(String email, List<Profesor> profesoresList) throws HorarioError
	{
		for (Profesor profesor : profesoresList)
		{
			if (profesor.getCuentaDeCorreo().equals(email))
			{
				return profesor;
			}

		}
		throw new HorarioError(1, "no se encuentra profesor con este email", null);

	}

	//ENDPOINT 5
	@RequestMapping(method = RequestMethod.GET, value = "/get/teachers", produces = "application/json")
	public ResponseEntity<?> getTeachers(

			HttpSession httpSession)
	{
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");

			// Para pasar los valoresde un mapa a lista ->List<Integer> valuesAsList = new
			// ArrayList<>(map.values());
			List<Profesor> profesoresList = new ArrayList<>(infoCentro.getDatos().getProfesores().values());

			return ResponseEntity.ok().body(profesoresList);
		} catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	// enpoint 8 MMM
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/Classroom/tramo", produces = "application/json")
	public ResponseEntity<?> getClassroomTeacherSchedule(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastName, @RequestHeader(required = true) Hour hora,
			@RequestHeader(required = true) String day, HttpSession httpSession)
	{
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			List<Profesor> profesoresList = new ArrayList<>(infoCentro.getDatos().getProfesores().values());
			return ResponseEntity.ok(this.getClassroom(name, lastName, hora, profesoresList, infoCentro, day).getAbreviatura());

		} 
		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	private Aula getClassroom(String name, String lastName, Hour hora, List<Profesor> profesoresList, InfoCentro info,
			String day) throws HorarioError
	{

		String nombreCompleto = lastName + ", " + name;
		for (Profesor profesor : profesoresList)
		{
			if (profesor.getNombre().equals(name))
			{
				List<Actividad> listActiviadades = info.getHorarios().getHorariosProfesores().get(profesor);
				return this.getActividadByHour(listActiviadades, hora, day, info).getAula();
			}

		}

		throw new HorarioError(1, "no se encuentra ninguna profesor con estas caracteristicas", null);

	}

	private Actividad getActividadByHour(List<Actividad> listActividades, Hour hour, String day, InfoCentro info)
			throws HorarioError
	{

		TramoHorario tramo = new HorariosUtils().getTramo(hour, day, info);
		for (Actividad actividad : listActividades)
		{
			if (actividad.getTramo().equals(tramo))
			{
				return actividad;
			}
		}

		throw new HorarioError(3, "no se encontro la actividad concordante con esa hora", null);
	}
	
	//endpoint 9 MMM
	@RequestMapping(method = RequestMethod.GET, value = "/get/teachersubject", produces = "application/json")
	public ResponseEntity<?> getTeacherSubject(
			@RequestHeader(required = true) String courseName,
			HttpSession httpSession
			)
	{
		try {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			
			Grupo grupo = this.getGrupo(courseName, infoCentro);
			
			TramoHorario tramoHorario = this.getTramo(new Date(), infoCentro);
			
			if (tramoHorario != null)
			{
				
				Actividad actividad = this.getActividad(tramoHorario, infoCentro.getHorarios().getHorariosGrupos().get(grupo));
				
				Map<String, String> response = new TreeMap<String, String>();
				
				response.put("profesor", actividad.getProfesor().getNombre());
				response.put("aula", actividad.getAula().getNombre());
				return ResponseEntity.ok().body(response);
			}
			
			return ResponseEntity.ok().build();
		}catch (HorarioError horarioError)
		{

			return ResponseEntity.status(400).body(horarioError);
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	
	//endpoint 10 MMM
		@RequestMapping(method = RequestMethod.GET, value = "/get/ClassroomCourse", produces = "application/json")
	public ResponseEntity<?> getClassroomCourse(
			@RequestHeader(value = "course", required = true) String course,
			HttpSession httpSession
			)
	{
			try {
				InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
				
				Grupo grupo = this.getGrupo(course, infoCentro);
				
				TramoHorario tramoHorario = this.getTramo(new Date(), infoCentro);
				
				if (tramoHorario != null)
				{
					
					Actividad actividad = this.getActividad(tramoHorario, infoCentro.getHorarios().getHorariosGrupos().get(grupo));
					
					Map<String, String> response = new TreeMap<String, String>();
					
					response.put("aula", actividad.getAula().getNombre());
					
					return ResponseEntity.ok().body(response);
				}
				
				return ResponseEntity.ok().body("El grupo no se encuentra en ninguna clase ahora mismo");
			}catch (HorarioError horarioError)
			{

				return ResponseEntity.status(400).body(horarioError.getBodyExceptionMessage());
			} catch (Exception e)
			{
				return ResponseEntity.status(500).body(e.getMessage());
			}
	}
	
	//ENDPOINT 11
	@RequestMapping(method = RequestMethod.GET, value = "/get/hours", produces = "application/json")
	public ResponseEntity<?> getListHours(HttpSession httpSession)
	{
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			// Para pasar los valores de un mapa a lista -> List<Integer> valuesAsList = new
			// ArrayList<>(map.values());
			List<TramoHorario> tramosList = new ArrayList<>(infoCentro.getDatos().getTramos().values());
			List<Hour> hourList = new ArrayList<Hour>();
			
			for (int i = 0 ; i < 7 ; i++)
			{
				System.out.println(tramosList.get(i).getDia());
				switch (i + 1)
				{
				case 1:
				{

					Hour hora = new Hour("primera", tramosList.get(i).getHoraInicio(),
							tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				case 2:
				{

					Hour hora = new Hour("segunda", tramosList.get(i).getHoraInicio(),
							tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				case 3:
				{

					Hour hora = new Hour("tercera", tramosList.get(i).getHoraInicio(),
							tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				case 4:
				{

					Hour hora = new Hour("recreo", tramosList.get(i).getHoraInicio(), tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				case 5:
				{
					Hour hora = new Hour("cuarta", tramosList.get(i).getHoraInicio(), tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				case 6:
				{
					Hour hora = new Hour("quinta", tramosList.get(i).getHoraInicio(), tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				case 7:
				{
					Hour hora = new Hour("sexta", tramosList.get(i).getHoraInicio(), tramosList.get(i).getHoraFinal());
					hourList.add(hora);
				}
				}

			}
			return ResponseEntity.ok().body(hourList);
		} catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	//ENDPOINT 12
	@RequestMapping(method = RequestMethod.GET ,value = "/get/course/sort/students" ,produces="application/json")
	public ResponseEntity<?> getListAlumCourseFirstSurname(
				@RequestHeader(value = "course", required = true) String course,
				HttpSession httpSession
			) 
    {
		try 
        {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			List<Student> listaEstudiantes = new ArrayList<Student>();
			for(int i = 0; i< infoCentro.getDatos().getAlumnos().size(); i++)
			{
				if(course.equals(infoCentro.getDatos().getAlumnos().get(i).getCourse())) 
				{
					listaEstudiantes.add(infoCentro.getDatos().getAlumnos().get(i));
				}
			}
			
			Collections.sort(listaEstudiantes);
			
		    return ResponseEntity.ok().body(listaEstudiantes);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
    }

	//ENDPOINT 13
	@RequestMapping(method = RequestMethod.GET, value = "/get/points", produces = "application/json")
	public ResponseEntity<?> getListPointsCoexistence(HttpSession httpSession)
	{
		try
		{
			return ResponseEntity.ok().body(this.listaPuntos);
		} catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	//ENDPOINT 14
	@RequestMapping(method = RequestMethod.GET, value = "/get/namelastname/reflexion", produces = "application/json")
	public ResponseEntity<?> getFirstNameSurname(HttpSession httpSession)
	{
		try {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			
			List<Profesor> profesores = new ArrayList<>(infoCentro.getDatos().getProfesores().values());
			
			TramoHorario tramoHorario = this.getTramo(new Date(), infoCentro);
			
			if (tramoHorario != null)
			{
				
				Actividad actividad = this.getActividad(tramoHorario, infoCentro.getHorarios().getHorariosProfesores().get(profesores));
				
				Map<String, String> response = new TreeMap<String, String>();
				
				String nombreProfesor = "";
				for(int i = 0; i < profesores.size(); i++) 
				{
					if(profesores.get(i).getId() == actividad.getAula().getId()) 
					{
						nombreProfesor = profesores.get(i).getNombre();
					}
				}
				
				response.put("nombreProfesor", nombreProfesor);
				return ResponseEntity.ok().body(response);
			}
			
			return ResponseEntity.ok().build();
		}catch (HorarioError horarioError)
		{

			return ResponseEntity.status(400).body(horarioError);
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	//ENDPOINT 15
	@RequestMapping(method = RequestMethod.GET, value = "/get/location/studentTutor", produces = "application/json")
	public ResponseEntity<?> getLocationStudentTutor(HttpSession httpSession,
			@RequestHeader(value = "name", required = true) String name,
			@RequestHeader(value = "lastName", required = true) String lastName)
	{
		try {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			
			Student student = this.getStudent(name, lastName, infoCentro);
			
			Grupo grupo = this.getGrupo(student.getCourse(), infoCentro);
			
			TramoHorario tramoHorario = this.getTramo(new Date(), infoCentro);
			
			if (tramoHorario != null)
			{
				
				Actividad actividad = this.getActividad(tramoHorario, infoCentro.getHorarios().getHorariosGrupos().get(grupo));
				
				Map<String, String> response = new TreeMap<String, String>();
				
				response.put("correoProfesor", actividad.getProfesor().getCuentaDeCorreo());
				response.put("curso", actividad.getAula().getNombre());
				
				return ResponseEntity.ok().body(response);
			}
			
			return ResponseEntity.ok().build();
		}catch (HorarioError horarioError)
		{

			return ResponseEntity.status(400).body(horarioError);
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	//ENDPOINT 16
	@RequestMapping(method = RequestMethod.GET, value = "/get/location/studentTutor/course", produces = "application/json")
	public ResponseEntity<?> getLocationStudentTutorCourse(HttpSession httpSession,
			@RequestHeader(value = "course", required = true) String course,
			@RequestHeader(value = "name", required = true) String name,
			@RequestHeader(value = "lastName", required = true) String lastName)
	{
		try {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			
			Student student = this.getStudent(name, lastName, infoCentro);
			
			Grupo grupo = this.getGrupo(student.getCourse(), infoCentro);
			
			TramoHorario tramoHorario = this.getTramo(new Date(), infoCentro);
			
			if (tramoHorario != null)
			{
				
				Actividad actividad = this.getActividad(tramoHorario, infoCentro.getHorarios().getHorariosGrupos().get(grupo));
				
				Map<String, String> response = new TreeMap<String, String>();
				
				response.put("correoProfesor", actividad.getProfesor().getCuentaDeCorreo());
				response.put("curso", actividad.getAula().getNombre());
				
				return ResponseEntity.ok().body(response);
			}
			
			return ResponseEntity.ok().build();
		}catch (HorarioError horarioError)
		{

			return ResponseEntity.status(400).body(horarioError);
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/teacher/Classroom", produces = "application/json")
	public ResponseEntity<?> getProfesorClass(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastName, HttpSession session)
	{
		try
		{
			
			InfoCentro info = (InfoCentro) session.getAttribute("info");
			
			Student student = this.getStudent(name, lastName, info);
			
			Grupo grupo = this.getGrupo(student.getCourse(), info);
			
			TramoHorario tramoHorario = this.getTramo(new Date(), info);
			
			if (tramoHorario != null)
			{
				
				Actividad actividad = this.getActividad(tramoHorario, info.getHorarios().getHorariosGrupos().get(grupo));
				
				Map<String, String> response = new TreeMap<String, String>();
				
				response.put("profesor", actividad.getProfesor().getNombre());
				response.put("aula", actividad.getAula().getNombre());
				return ResponseEntity.ok().body(response);
			}
			
			return ResponseEntity.ok().build();
		} catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	private Actividad getActividad(TramoHorario tramo , List<Actividad> lista) throws HorarioError {
		
		for (Actividad actividiad : lista)
		{
			
			if (actividiad.getTramo().equals(tramo))
			{
				return actividiad;
			}
			
		}
		throw new HorarioError(1, "no se encuentra actividad para este grupo", null);
		
	}

	private TramoHorario getTramo(Date date, InfoCentro info) throws HorarioError {
		for (TramoHorario tramoHorario : info.getDatos().getTramos().values())
		{
			if ((tramoHorario.getHoraInicio().equals(date) || tramoHorario.getHoraInicio().before(date)) && (tramoHorario.getHoraFinal().equals(date) || tramoHorario.getHoraFinal().after(date)) )
			{
				return tramoHorario;
			}
		}

		return null;
	}
	
	
	private Student getStudent(String name, String lastName, InfoCentro info) throws HorarioError {
		for (Student student : info.getDatos().getAlumnos())
		{
			if (student.getName().equals(name) && student.getLastname().equals(lastName))
			{
				return student;
			}
		}

		throw new HorarioError(1, "no se encuentra ninguna profesor con estas caracteristicas", null);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/horario/teacher/pdf", produces = "application/json")
	public ResponseEntity<?> getTeacherPdf(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastName, HttpSession session)
	{
		try
		{

			InfoCentro info = (InfoCentro) session.getAttribute("info");

			new HorariosUtils().getInfoPdfFromProfesor(this.getProfesor(name, lastName, info), info);

			File file = new File(lastName + ", " + name + "_Horario.pdf");

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Disposition", "attachment; filename=" + file.getName());

			byte[] bytesArray = Files.readAllBytes(file.toPath());

			return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
		}

		catch (IOException exception)
		{
			// --- ERROR ---
			String error = "ERROR GETTING THE BYTES OF PDF ";

			logger.info(error);

			HorarioError horariosError = new HorarioError(500, error, exception);
			logger.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}

		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	private Profesor getProfesor(String name, String lastName, InfoCentro info) throws HorarioError
	{

		String nombreCompleto = lastName + ", " + name;
		for (Profesor profesor : info.getDatos().getProfesores().values())
		{
			if (profesor.getNombre().equals(nombreCompleto))
			{
				return profesor;
			}
		}

		throw new HorarioError(1, "no se encuentra ninguna profesor con estas caracteristicas", null);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/teachers/pdf", produces = "application/json")
	public ResponseEntity<?> getTeachersPdfs(HttpSession session)
	{
		try
		{

			InfoCentro info = (InfoCentro) session.getAttribute("info");

			List<String> ficheros = new ArrayList<String>();

			for (Profesor profesor : info.getDatos().getProfesores().values())
			{
				new HorariosUtils().getInfoPdfFromProfesor(profesor, info);

				ficheros.add(profesor.getNombre() + "_Horario.pdf");
			}
			// Archivo ZIP de salida
			String archivoZip = "archivos.zip";

			// Buffer para la lectura y escritura
			byte[] buffer = new byte[1024];

			FileOutputStream fos = new FileOutputStream(archivoZip);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String archivo : ficheros)
			{
				ZipEntry ze = new ZipEntry(archivo);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(archivo);

				int len;
				while ((len = in.read(buffer)) > 0)
				{
					zos.write(buffer, 0, len);
				}

				in.close();
				zos.closeEntry();
			}

			// Cerrar el flujo de salida
			zos.close();

			File file = new File(archivoZip);

			byte[] bytesArray = Files.readAllBytes(file.toPath());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Disposition", "attachment; filename=" + file.getName());

			return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
		}

		catch (IOException exception)
		{
			// --- ERROR ---
			String error = "ERROR GETTING THE BYTES OF PDF ";

			logger.info(error);

			HorarioError horariosError = new HorarioError(500, error, exception);
			logger.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}

		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/horario/group/pdf", produces = "application/json")
	public ResponseEntity<?> getGroupPdf(@RequestHeader(required = true) String grupo, HttpSession session)
	{
		try
		{

			InfoCentro info = (InfoCentro) session.getAttribute("info");

			new HorariosUtils().getInfoPdfFromGrupo(this.getGrupo(grupo, info), info);

			File file = new File(grupo +  "_Horario.pdf");

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Disposition", "attachment; filename=" + file.getName());

			byte[] bytesArray = Files.readAllBytes(file.toPath());

			return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
		}

		catch (IOException exception)
		{
			// --- ERROR ---
			String error = "ERROR GETTING THE BYTES OF PDF ";

			logger.info(error);

			HorarioError horariosError = new HorarioError(500, error, exception);
			logger.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}

		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	private Grupo getGrupo(String nombre, InfoCentro info) throws HorarioError
	{

		for (Grupo grupo : info.getDatos().getGrupos().values())
		{
			if (grupo.getAbreviatura().equals(nombre))
			{
				return grupo;
			}
		}

		throw new HorarioError(1, "no se encuentra ninguna grupo con estas caracteristicas", null);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/groups/pdf", produces = "application/json")
	public ResponseEntity<?> getGroupsPdfs(HttpSession session)
	{
		try
		{

			InfoCentro info = (InfoCentro) session.getAttribute("info");

			List<String> ficheros = new ArrayList<String>();

			for (Grupo grupo : info.getDatos().getGrupos().values())
			{
				new HorariosUtils().getInfoPdfFromGrupo(grupo, info);

				ficheros.add(grupo.getNombre() + "_Horario.pdf");
			}
			// Archivo ZIP de salida
			String archivoZip = "archivos.zip";

			// Buffer para la lectura y escritura
			byte[] buffer = new byte[1024];

			FileOutputStream fos = new FileOutputStream(archivoZip);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String archivo : ficheros)
			{
				ZipEntry ze = new ZipEntry(archivo);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(archivo);

				int len;
				while ((len = in.read(buffer)) > 0)
				{
					zos.write(buffer, 0, len);
				}

				in.close();
				zos.closeEntry();
			}

			// Cerrar el flujo de salida
			zos.close();

			File file = new File(archivoZip);

			byte[] bytesArray = Files.readAllBytes(file.toPath());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Disposition", "attachment; filename=" + file.getName());

			return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
		}

		catch (IOException exception)
		{
			// --- ERROR ---
			String error = "ERROR GETTING THE BYTES OF PDF ";

			logger.info(error);

			HorarioError horariosError = new HorarioError(500, error, exception);
			logger.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}

		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	@RequestMapping(method = RequestMethod.POST, value = " /student/regreso/bathroom", produces = "application/json")
	public ResponseEntity<?> postReturnBathroom(@RequestHeader(required = true) String course, String name,String lastName)
	{
		try {  
			LocalDateTime horaActual = LocalDateTime.now();

	        // Formatea los datos en una cadena
	        String registro = "Curso: " + course + ", Nombre: " + name + ", Apellido: " + lastName + ", Hora: " + horaActual;
	        System.out.println(registro);
	        // Agrega el registro a la lista
	        registrosBano.add(registro);

	        // Devuelve una respuesta
	        return ResponseEntity.ok(registro);
	        
		}catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
		

		
	}
	@RequestMapping(method = RequestMethod.POST, value = " /student/visita/bathroom", produces = "application/json")
	public ResponseEntity<?> postVisit(@RequestHeader(required = true) String course, String name,String lastName)
	{

		try {  
			LocalDateTime horaActual = LocalDateTime.now();
			 String registroFiccion = "Curso: " + course + ", Nombre: " + name + ", Apellido: " + lastName + ", Hora: " + horaActual;
	        // Formatea los datos en una cadena
	        String registro = "Curso: " + course + ", Nombre: " + name + ", Apellido: " + lastName + ", Hora: " + horaActual;
	        if(registro.equals(registroFiccion))
	        {
	        	System.out.println(registro);
	        }
	    
	        

	        // Devuelve una respuesta
	        return ResponseEntity.ok(registro);
	        
		}catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getListTimesBathroom", produces = "application/json")
	public ResponseEntity<?> getListTimesBathroom(@RequestHeader String fechaInicio, @RequestHeader String fechaend) {
	    try {
	        List<String> studentVisits = new ArrayList<>();

	        studentVisits.add("John Doe: 5 visits");
	        studentVisits.add("Jane Smith: 3 visits");

	        return ResponseEntity.ok(studentVisits);
	    } catch (Exception exception) {
	        logger.error("Server Error", exception);
	        return ResponseEntity.status(500).body(exception.getMessage());
	    }
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/dias/studentBathroom", produces = "application/json")
	public ResponseEntity<?> getDayHourBathroom(@RequestHeader String name, @RequestHeader String lastname, @RequestHeader String fechaInicio, @RequestHeader String fechaend) {
	    try {
	        List<String> visitTimes = new ArrayList<>();

	        visitTimes.add(name + " " + lastname + " - Visit on 2024-01-10 at 10:00");
	        visitTimes.add(name + " " + lastname + " - Visit on 2024-01-11 at 11:30");

	        return ResponseEntity.ok(visitTimes);
	    } catch (Exception exception) {
	        logger.error("Server Error", exception);
	        return ResponseEntity.status(500).body(exception.getMessage());
	    }
	}

	

	
	
	
	

}
