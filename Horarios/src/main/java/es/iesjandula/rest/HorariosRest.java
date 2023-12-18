package es.iesjandula.rest;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iesjandula.Horarios.exceptions.HorarioError;


public class HorariosRest {
	@RequestMapping(method = RequestMethod.POST, value = "/computers/admin/file", consumes = "multipart/form-data")
	public ResponseEntity<?> sendXmlToObject(
	        @RequestBody(required = true) File file
	) {
	    try {

	        return ResponseEntity.ok().build();
	    } catch (HorarioError horarioError) {
	        return ResponseEntity.status(400).body(horarioError);
	    }catch (Exception e) {
		    		return ResponseEntity.status(500).body(e.getMessage());
		}
	
		
    }
}
