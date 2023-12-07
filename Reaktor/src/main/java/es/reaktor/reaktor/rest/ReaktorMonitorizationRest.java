package es.reaktor.reaktor.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.models.Computer;
import es.reaktor.models.ComputerError;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorMonitorizationRest 
{
	 @RequestMapping(method = RequestMethod.GET, value = " /computers/get/status")
	    public ResponseEntity<Computer> getCommandLine(
	             @RequestHeader (required = false) String serialNumber
	             )
	    {
	        try 
	        {
	        	Computer computer = new Computer();
	            controlComputer(computer);
	            return ResponseEntity.ok(computer);
	        }
	        catch (ComputerError computerError) 
	        {
	            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage()) ;
	        }
	        catch (Exception e )
	        {

	            return ResponseEntity.status(500).body(e.getMessage()) ;
	        } 
	    }
	 
	 public void controlComputer(Computer computer)
	 {
		 
	 }
	

}
