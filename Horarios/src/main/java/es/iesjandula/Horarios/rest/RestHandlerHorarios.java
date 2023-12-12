package es.iesjandula.Horarios.rest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.Classroom;
import es.iesjandula.Horarios.models.Course;
import es.iesjandula.Horarios.models.Profesor;
import es.iesjandula.Horarios.models.RolReaktor;
import es.iesjandula.Horarios.models.Student;
import es.iesjandula.Horarios.utils.HorariosCheckers;
import es.iesjandula.Horarios.utils.HorariosUtils;


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
	private List<Profesor> listaProfesores = new ArrayList<Profesor>(List.of(
    		new Profesor("Juan","Pérez","juan.perez@example.com", new ArrayList<RolReaktor>(List.of(RolReaktor.administrador))),
    		new Profesor("Juana","Rodriguez","jrodgar@example.com",new ArrayList<RolReaktor>(List.of(RolReaktor.conserje)))));
	
	/**
	 * Public constructor
	 */
	public RestHandlerHorarios()
	{
		// Empty constructor
	}

	@RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvTo(@RequestPart(value = "csvFile", required = true) final MultipartFile csvFile)
	{
		try
		{
			check.checkFile(csvFile);
			List<Profesor> lista = horariosUtils.parseFile(csvFile);
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
    
	@RequestMapping(method= RequestMethod.GET ,value = "/get/teachers" ,produces="application/json")
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
}
