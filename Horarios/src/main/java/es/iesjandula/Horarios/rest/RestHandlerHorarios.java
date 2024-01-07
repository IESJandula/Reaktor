package es.iesjandula.Horarios.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

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

	private List<AttitudePoints> listaPuntos = new ArrayList<AttitudePoints>(
			List.of(new AttitudePoints(75, "Agredir al profesor"), new AttitudePoints(25, "Movil"),
					new AttitudePoints(3, "Faltar el respeto a un compañero")));

	/**
	 * Public constructor
	 */
	public RestHandlerHorarios()
	{
		// Empty constructor
	}

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

//	@RequestMapping(method = RequestMethod.GET, value = "/get/sortstudents")
//	public ResponseEntity<?> getListStudentsAlphabetically()
//	{
//		try
//		{
//			Collections.sort(this.listaEstudiantes);
//			
//			return ResponseEntity.ok().body(this.listaEstudiantes);
//		} 
//		catch (Exception exception)
//		{
//			String message = "Server Error";
//			logger.error(message, exception);
//			return ResponseEntity.status(500).body(exception.getMessage());
//		}
//	}

	@RequestMapping(method = RequestMethod.GET, value = " /get/roles")
	public ResponseEntity<?> getRoles(@RequestHeader(required = true) String email, HttpSession httpSession)
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

		TramoHorario tramo = new HorariosUtils(info).getTramo(hour, day);
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
		InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
		return ResponseEntity.ok();
	}
	
	
	//endpoint 10 MMM
		@RequestMapping(method = RequestMethod.GET, value = "/get/ClassroomCourse", produces = "application/json")
		public ResponseEntity<?> getClassroomCourse(
				@RequestHeader(value = "course", required = true) String course,
				HttpSession httpSession
				)
		{
			try
			{

				InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
				
				return ResponseEntity.ok(this.getClassroomCourse(course).getNombre());
			} 
			catch (Exception exception)
			{
				String message = "Server Error";
				logger.error(message, exception);
				return ResponseEntity.status(500).body(exception.getMessage());
			}
		}
		
		private Aula getClassroom(InfoCentro info, String course) throws HorarioError
		{
			List<Actividad> listActiviadades = info.getDatos().getAulas();
			return this.getActividadByGrupo(listActiviadades,course, info).getAula();
			throw new HorarioError(1, "no se encuentra ninguna profesor con estas caracteristicas", null);
		}

	@RequestMapping(method = RequestMethod.GET, value = "/get/hours", produces = "application/json")
	public ResponseEntity<?> getListHours(HttpSession httpSession)
	{
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			// Para pasar los valores de un mapa a lista -> List<Integer> valuesAsList = new
			// ArrayList<>(map.values());
			List<TramoHorario> tramosList = infoCentro.getDatos().getTramos();
			List<Hour> hourList = new ArrayList<Hour>();
			for (int i = 0 ; i < tramosList.size() ; i++)
			{

				switch (i)
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
				default:
					throw new IllegalArgumentException("Unexpected value: " + i);
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

//	@RequestMapping(method = RequestMethod.GET ,value = "/get/course/sort/students" ,produces="application/json")
//	public ResponseEntity<?> getListAlumCourseFirstSurname(
//				@RequestHeader(value = "course", required = true) String course
//			) 
//    {
//		try 
//        {
//			for(int i = 0; i< listaEstudiantes.size(); i++)
//			{
//				if(course.equals(listaEstudiantes.get(i).getCourse().getName())) 
//				{
//					listaEstudiantesCurso.add(listaEstudiantes.get(i));
//					Collections.sort(this.listaEstudiantesCurso);
//					
//				}
//			}
//			
//			
//		    return ResponseEntity.ok().body(this.listaEstudiantesCurso);
//        } 
//        catch (Exception exception)
//		{
//			String message = "Server Error";
//			logger.error(message, exception);
//			return ResponseEntity.status(500).body(exception.getMessage());
//		}
//    }

	@RequestMapping(method = RequestMethod.GET, value = "/get/points", produces = "application/json")
	public ResponseEntity<?> getListPointsCoexistence()
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
			if ((tramoHorario.getHoraInicio().equals(date) || tramoHorario.getHoraInicio().before(date)) && (tramoHorario.getHoraFinal().equals(date) || tramoHorario.getHoraFinal().before(date)) )
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
			if (grupo.getNombre().equals(nombre))
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

}
