package es.iesjandula.Horarios.rest;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
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
import es.iesjandula.Horarios.models.xml.InfoCentro;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.TramoHorario;
import es.iesjandula.Horarios.models.RolReaktor;
import es.iesjandula.Horarios.models.Student;
import es.iesjandula.Horarios.utils.HorariosCheckers;
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
	    	session.setAttribute("info", parser.parseDataFromXmlFile(file));
	    	
	        return ResponseEntity.ok().build();
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
	        @RequestHeader(required = true) String email
	) 
	{
		List<Roles> listRoles = new ArrayList<Roles>();
	    try 
	    {
	    	
	    	checkEmail(email);
	    	if(toDoCorreoRoles.get(email).isEmpty()) {
	    		listRoles= toDoCorreoRoles.get(email);
	    		toDoCorreoRoles.get(email).remove(0);
	    	}
	    	
	        return ResponseEntity.ok(listRoles);
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

	private void checkEmail(String email) throws HorarioError 
	{
		if(email.isBlank() || !this.toDoCorreoRoles.containsKey(email))
		{
			throw new HorarioError(2, "Invalid correo");
		}
		
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
}
