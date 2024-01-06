package es.iesjandula.Horarios.rest;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.AttitudePoints;
import es.iesjandula.Horarios.models.Classroom;
import es.iesjandula.Horarios.models.Course;
import es.iesjandula.Horarios.models.Hour;
import es.iesjandula.Horarios.models.xml.Aula;
import es.iesjandula.Horarios.models.xml.InfoCentro;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.TramoHorario;
import es.iesjandula.Horarios.models.xml.horarios.Actividad;
import es.iesjandula.Horarios.models.RolReaktor;
import es.iesjandula.Horarios.models.Student;
import es.iesjandula.Horarios.utils.HorariosUtils;
import es.iesjandula.Horarios.utils.XmlParser;
import jakarta.servlet.http.HttpSession;


@RequestMapping(value = "/horarios", produces = { "application/json" })
@RestController

/**
 * @author Alejandro Cazalla Pérez
 */
public class RestHandlerHorarios
{
	final static Logger logger = LogManager.getLogger();
	
	private List<AttitudePoints> listaPuntos = new ArrayList<AttitudePoints>(List.of(
			new AttitudePoints(75, "Agredir al profesor"),
			new AttitudePoints(25, "Movil"),
			new AttitudePoints(3, "Faltar el respeto a un compañero")
			));
	
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
	    try {
	    	
	    	XmlParser parser = new XmlParser();
	    	InfoCentro info = parser.parseDataFromXmlFile(file);
	    	session.setAttribute("info", info);
	    	InfoCentro info2 = (InfoCentro) session.getAttribute("info");
	    	
	        return ResponseEntity.ok().body(info2);
	    } catch (HorarioError horarioError) {
	        return ResponseEntity.status(400).body(horarioError.getBodyExceptionMessage());
	    }catch (Exception e) {
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
		}
		catch (HorarioError exception)
		{
			String message = "Client error";
			logger.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyExceptionMessage());
		} 
		catch (Exception exception)
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
	public ResponseEntity<?> getRoles(
	        @RequestHeader(required = true) String email,
	        HttpSession httpSession
			) 
	{
		InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
    	List<Profesor> profesoresList = new ArrayList<>(infoCentro.getDatos().getProfesores().values());
    	try 
	    {
	    	
	    	
	        return ResponseEntity.ok(this.getProfesorByEmail(email, profesoresList).getListaRoles());
	    } 
	    catch (HorarioError horarioError) 
	    {
	    	
	        return ResponseEntity.status(400).body(horarioError);
	    }
	    catch (Exception e) 
	    {
		    		return ResponseEntity.status(500).body(e.getMessage());
		}
	
		
    }

	private Profesor getProfesorByEmail(String email,List<Profesor> profesoresList ) throws HorarioError
	{
		for (Profesor profesor : profesoresList) 
		{
			if(profesor.getCuentaDeCorreo().equals(email)) 
			{
				return profesor;
			}
			
			
		}
		throw new HorarioError(1, "no se encuentra profesor con este email", null);
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/teachers" ,produces="application/json")
    public ResponseEntity<?> getTeachers(
    		
    		HttpSession httpSession
    		) 
    {
        try 
        {
        	InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
        	
        	// Para pasar los valoresde un mapa a lista ->List<Integer> valuesAsList = new ArrayList<>(map.values());
        	List<Profesor> profesoresList = new ArrayList<>(infoCentro.getDatos().getProfesores().values());
        	
		    return ResponseEntity.ok().body(profesoresList);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
     }
	
	//enpoint 8 MMM
	@RequestMapping(method = RequestMethod.GET ,value = "/teacher/get/Classroom/tramo" ,produces="application/json")
	public ResponseEntity<?> getClassroomTeacherSchedule(
			@RequestHeader(required = true) String name,
    		@RequestHeader(required = true) String lastName,
    		@RequestHeader(required = true) Hour hora,
    		@RequestHeader(required = true) String day,
			HttpSession httpSession
			) 
    {
		try
		{
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			// Para pasar los valoresde un mapa a lista ->List<Integer> valuesAsList = new ArrayList<>(map.values());
        	List<Profesor> profesoresList = new ArrayList<>(infoCentro.getDatos().getProfesores().values());
        	return ResponseEntity.ok(this.getClassroom(name, lastName, hora, profesoresList, infoCentro, day).getAbreviatura());
	
		}catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
    }
	
	private Aula getClassroom(String name, String lastName,Hour hora, List<Profesor> profesoresList, InfoCentro info, String day) throws HorarioError
	{
		
		String nombreCompleto = lastName+", "+name;
		for (Profesor profesor : profesoresList) 
		{
			if(profesor.getNombre().equals(name))
			{
				List<Actividad>listActiviadades = info.getHorarios().getHorariosProfesores().get(profesor);
				return this.getActividadByHour(listActiviadades, hora, day, info).getAula();
			}
			
		}
		
		throw new HorarioError(1, "no se encuentra ninguna profesor con estas caracteristicas", null);
		
	}
	
	private Actividad getActividadByHour(List<Actividad> listActividades, Hour hour, String day, InfoCentro info) throws HorarioError
	{
		
		TramoHorario tramo = new HorariosUtils(info).getTramo(hour, day);
		for(Actividad actividad : listActividades)
		{
			if(actividad.getTramo().equals(tramo)) {
				return actividad;
			}
		}
		
		throw new HorarioError(3, "no se encontro la actividad concordante con esa hora", null);
	}
	@RequestMapping(method = RequestMethod.GET ,value = "/get/hours" ,produces="application/json")
	public ResponseEntity<?> getListHours(
			HttpSession httpSession
			) 
    {
		try 
        {
			InfoCentro infoCentro = (InfoCentro) httpSession.getAttribute("info");
			// Para pasar los valores de un mapa a lista -> List<Integer> valuesAsList = new ArrayList<>(map.values());
        	List<TramoHorario> tramosList =infoCentro.getDatos().getTramos();
        	List<Hour>hourList= new ArrayList<Hour>();
        	for(int i=0; i<tramosList.size(); i++)
        	{
        	
        		switch (i) {
				case 1: {
					
					Hour hora = new Hour("primera",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				case 2: {
					
					Hour hora = new Hour("segunda",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				case 3: {
					
					Hour hora = new Hour("tercera",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				case 4: {
					
					Hour hora = new Hour("recreo",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				case 5:{
					Hour hora = new Hour("cuarta",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				case 6:{
					Hour hora = new Hour("quinta",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				case 7:{
					Hour hora = new Hour("sexta",tramosList.get(i).getHoraInicio(),tramosList.get(i).getHoraFinal());
        			hourList.add(hora);
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + i);
				}
        			
        	}
		    return ResponseEntity.ok().body(hourList);
        } 
        catch (Exception exception)
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
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/points" ,produces="application/json")
	public ResponseEntity<?> getListPointsCoexistence() 
    {
		try 
        {
		    return ResponseEntity.ok().body(this.listaPuntos);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
    }
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/ClassroomCourse" ,produces="application/json")
	public ResponseEntity<?> getClassroomCourse(
				@RequestHeader(value = "course", required = true) String course
			) 
    {
		try 
        {
			

			for(int i = 0; i< listaCursos.size(); i++)
			{
				if(course.equals(listaCursos.get(i).getName()))
				{
					listaCursosFinal.add(listaCursos.get(i));
				
				}
			}
			return ResponseEntity.ok().body(this.listaCursosFinal);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
    }
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/pdf" ,produces="application/json")
	public ResponseEntity<?> getPdf(HttpSession session) 
    {
		try 
        {
			
			InfoCentro info = (InfoCentro)session.getAttribute("info");
			
			new HorariosUtils().getInfoPdf(this.getProfesor("Marie", "Curie Mendel", info), info);
			return ResponseEntity.ok().build();
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
		
		String nombreCompleto = lastName+", "+name;
		System.out.println(nombreCompleto);
		for (Profesor profesor : info.getDatos().getProfesores().values())
		{
			if(profesor.getNombre().equals(nombreCompleto))
			{
				return profesor;
			}
			
		}
		
		throw new HorarioError(1, "no se encuentra ninguna profesor con estas caracteristicas", null);
		
	}
}
