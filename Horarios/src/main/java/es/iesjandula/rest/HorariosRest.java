package es.iesjandula.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iesjandula.exceptions.HorarioError;

import es.reaktor.models.Roles;


public class HorariosRest {
	
	private Map<String, List<Roles>> toDoCorreoRoles = new HashMap<String, List<Roles>>(Map.of(
   			"ejemplocorreo@gmail.com", new ArrayList<Roles>( List.of(new Roles("administrador"),new Roles("conserje"))),
   			"ejemplocorreo2@gmail.com", new ArrayList<Roles>(),
			"ejemplocorreo3@gmail.com", new ArrayList<Roles>(List.of(new Roles("docente")))));
	
	
	
	
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
	
	
	
}
