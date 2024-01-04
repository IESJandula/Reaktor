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
import es.iesjandula.Horarios.models.Classroom;
import es.iesjandula.Horarios.models.Course;
import es.iesjandula.Horarios.models.Hour;
import es.iesjandula.Horarios.models.Profesor;
import es.iesjandula.Horarios.models.RolReaktor;
import es.iesjandula.Horarios.models.Student;
import es.iesjandula.Horarios.utils.HorariosCheckers;
import es.iesjandula.Horarios.utils.HorariosUtils;
import es.iesjandula.Horarios.utils.XmlParser;


@RequestMapping(value = "/horarios", produces = { "application/json" })
@RestController

/**
 * @author Alejandro Cazalla Pérez
 */
public class RestHandlerHorarios
{
	final static Logger logger = LogManager.getLogger();
	
	private HorariosCheckers check = new HorariosCheckers();
	private HorariosUtils horariosUtils = new HorariosUtils();
	
	private List<Student> listaEstudiantes = new ArrayList<Student>(List.of(
			new Student("Alejandro", "Cazalla Perez", new Course("2DAM", new Classroom(3, 1))),
			new Student("Juan", "Sutil Mesa", new Course("2DAM", new Classroom(3, 1))),
			new Student("Manuel", "Martin Murillo", new Course("2DAM", new Classroom(3, 1))),
			new Student("Alvaro", "Marmol Romero", new Course("2DAM", new Classroom(3, 1)))
			
		));
	
	private List<Student> listaEstudiantesCurso = new ArrayList<Student>(List.of(
			
			
		));
	
	private List<Profesor> listaProfesores = new ArrayList<Profesor>(List.of(
    		new Profesor("Juan","Pérez","juan.perez@example.com", new ArrayList<RolReaktor>(List.of(RolReaktor.administrador))),
    		new Profesor("Juana","Rodriguez","jrodgar@example.com",new ArrayList<RolReaktor>(List.of(RolReaktor.conserje)))
    		));

	
	private List<Hour> listaHoras = new ArrayList<Hour>(List.of(
			new Hour("Primera", "8:15", "9:15"),
			new Hour("Segunda", "9:15", "10:15"),
			new Hour("Tercera", "10:15", "11:15"),
			new Hour("Recreo", "11:15", "11:45"),
			new Hour("Cuarta", "11:45", "12:45"),
			new Hour("Quinta", "12:45", "13:45"),
			new Hour("Sexta", "13:45", "14:45")
			));
	
	/**
	 * Public constructor
	 */
	public RestHandlerHorarios()
	{
		// Empty constructor
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = "multipart/form-data")
	public ResponseEntity<?> sendXmlToObject(@RequestPart(value = "xmlFile", required = true) final MultipartFile file) 
	{
	    try {
	    	
	    	XmlParser parser = new XmlParser();
	    	parser.parseDataFromXmlFile(file);
	    	
	        return ResponseEntity.ok().build();
	    } catch (HorarioError horarioError) {
	        return ResponseEntity.status(400).body(horarioError);
	    }catch (Exception e) {
		    		return ResponseEntity.status(500).body(e.getMessage());
		}
	
		
    }
	

	@RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvTo(@RequestPart(value = "csvFile", required = true) final MultipartFile csvFile)
	{
		try
		{
			check.checkFile(csvFile);
			List<Profesor> lista = horariosUtils.parseCsvFile(csvFile);
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/sortstudents")
	public ResponseEntity<?> getListStudentsAlphabetically()
	{
		try
		{
			Collections.sort(this.listaEstudiantes);
			
			return ResponseEntity.ok().body(this.listaEstudiantes);
		} 
		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/teachers" ,produces="application/json")
    public ResponseEntity<?> getTeachers() 
    {
        try 
        {
		    return ResponseEntity.ok().body(this.listaProfesores);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
     }
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/hours" ,produces="application/json")
	public ResponseEntity<?> getListHours() 
    {
		try 
        {
		    return ResponseEntity.ok().body(this.listaHoras);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
    }
	
	@RequestMapping(method = RequestMethod.GET ,value = "/get/course/sort/students" ,produces="application/json")
	public ResponseEntity<?> getListAlumCourseFirstSurname(
				@RequestHeader(value = "course", required = true) String course
			) 
    {
		try 
        {
			for(int i = 0; i< listaEstudiantes.size(); i++)
			{
				if(course.equals(listaEstudiantes.get(i).getCourse().getName())) 
				{
					listaEstudiantesCurso.add(listaEstudiantes.get(i));
					Collections.sort(this.listaEstudiantesCurso);
					
				}
			}
			
			
		    return ResponseEntity.ok().body(this.listaEstudiantesCurso);
        } 
        catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
    }
	
	
}
