package es.reaktor.horarios.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.reaktor.horarios.exceptions.HorariosError;
import es.reaktor.horarios.models.ActitudePoints;
import es.reaktor.horarios.models.Student;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/horarios")
@Slf4j
public class HorariosRest {

    @RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> sendCsvTo(@RequestPart MultipartFile archivo) {
        List<String> roles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> rolesObjeto = parsearLineaCSV(line);
                roles.addAll(rolesObjeto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return roles;
    }

    private List<String> parsearLineaCSV(String linea) {
        List<String> roles = new ArrayList<>();

        try {
            String[] campos = linea.split(",");
            if (campos.length >= 4) {
                List<String> rolesObjeto = List.of(campos[3].trim().split(";"));
                roles.addAll(rolesObjeto);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Datos de CSV incompletos o incorrectos: " + linea);
        }

        return roles;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get/course/sort/students")
    public ResponseEntity<?> getListAlumnoFirstSurname(@RequestHeader(required = true) String course)
    {
    	List<Student> listStudents = new ArrayList<Student>();
    	try
    	{
    		if(true)
    		{
    			/*
            	 * BUSCAR ALUMNOS EN EL XML??
            	*/
        		return ResponseEntity.ok().body(listStudents);
    		}
    		else
    		{
    			String error = "list not found";
    			HorariosError horariosError = new HorariosError(400, error, null);
    			log.error(error);
    			return ResponseEntity.status(400).body(horariosError);
    		}
    		
    	}catch(Exception ex)
    	{
    		// -- CATCH ANY ERROR ---
    					String error = "Server Error";
    					HorariosError horariosError = new HorariosError(500, error, exception);
    					log.error(error, exception);
    					return ResponseEntity.status(500).body(horariosError);
    	}
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get/points")
    public ResponseEntity<?> getListPointsCoexistence()
    {
    	List<ActitudePoints> listActitudePoints = new ArrayList<>();
    	
    	try
    	{
    		ActitudePoints actitudePoints = new ActitudePoints(20, "Realizar todas las tareas");
    		listActitudePoints.add(actitudePoints);
    		if(!listActitudePoints.isEmpty())
    		{
    			
    			return ResponseEntity.ok().body(listActitudePoints);
    		}
    		else
    		{
    			String error = "list not found";
    			HorariosError horariosError = new HorariosError(400, error, null);
    			log.error(error);
    			return ResponseEntity.status(400).body(horariosError);
    		}
    	}catch(Exception exception)
    	{
    		// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
    	}
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get/namelastname/reflexion")
    public ResponseEntity<?> getFirstNameSurname()
    {
    	String[] nombreYApellido = new String[2];
    	nombreYApellido[0] = "Manolo";
    	nombreYApellido[1] = "Fernandez";
    	try
    	{
    		if(nombreYApellido != null)
    		{
    			
    			return ResponseEntity.ok().body(nombreYApellido);
    		}
    		else
    		{
    			String error = "teacher not found";
    			HorariosError horariosError = new HorariosError(400, error, null);
    			log.error(error);
    			return ResponseEntity.status(400).body(horariosError);
    		}
    	}catch(Exception exception)
    	{
    		// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
    	}
    }
    @RequestMapping(method = RequestMethod.GET, value ="/get/location/studentTutor")
    public ResponseEntity<?> getLocationStudentTutor(@RequestHeader(required = true) String name, String lastName)
    {
    	
    	try
    	{
    		if(true)
    		{
    			/*
    			 * rellenar lista
    			 */
    			return ResponseEntity.ok().body();
    		}
    		else
    		{
    			String error = "student not found";
    			HorariosError horariosError = new HorariosError(400, error, null);
    			log.error(error);
    			return ResponseEntity.status(400).body(horariosError);
    		}
    	}catch(Exception exception)
    	{
    		// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
    	}
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get/location/studentTutor/course", produces = "application/json")
    public ResponseEntity<?> getLocationStudentTutorCourse(@RequestHeader(required = true) String course, String name, String lastName)
    {
    	try
    	{
    		if(true)
    		{
    			/*
    			 * rellenar lista
    			 */
    			return ResponseEntity.ok().body();
    		}
    		else
    		{
    			String error = "student not found";
    			HorariosError horariosError = new HorariosError(400, error, null);
    			log.error(error);
    			return ResponseEntity.status(400).body(horariosError);
    		}
    	}catch(Exception exception)
    	{
    		// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
    	}
    }
}
