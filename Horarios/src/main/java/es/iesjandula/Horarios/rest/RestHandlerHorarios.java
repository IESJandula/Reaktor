package es.iesjandula.Horarios.rest;

import java.io.IOException;
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
			horariosUtils.parseFile();
			return ResponseEntity.ok().build();
		}
		catch (HorarioError exception)
		{
			String message = "Request not found";
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
			Student student1 = new Student("Alejandro", "Cazalla Perez", new Course("2DAM", new Classroom(3, 1)));
			Student student2 = new Student("Juan", "Sutil Mesa", new Course("2DAM", new Classroom(3, 1)));
			Student student3 = new Student("Manuel", "Martin Murillo", new Course("2DAM", new Classroom(3, 1)));
			Student student4 = new Student("Alvaro", "Marmol Romero", new Course("2DAM", new Classroom(3, 1)));
			
			List<Student> listaEstudiantes = new ArrayList<Student>();
			listaEstudiantes.add(student1);
			listaEstudiantes.add(student2);
			listaEstudiantes.add(student3);
			listaEstudiantes.add(student4);
			
			Collections.sort(listaEstudiantes);
			
			return ResponseEntity.ok().body(listaEstudiantes);
		} 
		catch (IndexOutOfBoundsException exception)
		{
			String message = "List not found";
			logger.error(message, exception);
			return ResponseEntity.status(400).body(exception.getMessage());
		} 
		catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
}
