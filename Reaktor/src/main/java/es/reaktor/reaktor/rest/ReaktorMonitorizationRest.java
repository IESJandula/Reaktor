package es.reaktor.reaktor.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.reaktor.models.Computer;
import es.reaktor.models.ComputerError;
import es.reaktor.models.Status;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorMonitorizationRest 
{
	@RequestMapping(method = RequestMethod.GET, value = "/computers/get/status")
    public ResponseEntity<Object> getCommandLine(
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
 
 public void controlComputer(Computer computer) throws ComputerError
 {
 
	 if(computer == null) 
	 {
		 throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");     
	 }
 }
 
 @RequestMapping(method = RequestMethod.GET, value = "/computers/get/file", produces="multipart/from-data")
    public ResponseEntity<Object> getAnyFile(
             @RequestHeader (required = false) String serialNumber
             )
    {
        try 
        {
        	String file = "";
            controlFile(file);
            return ResponseEntity.ok(file);
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
 
 public void controlFile(String file) throws ComputerError
 {
 
	 if(file == null) 
	 {
		 throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");     
	 }
 }
 
 @RequestMapping(method = RequestMethod.GET, value = "/computers/get/screenshot")
    public ResponseEntity<?> getScreenshotOrder(
             @RequestHeader (required = false) String serialNumber
             )
    {
        try 
        {
        	
            controlScreenShotOrder(serialNumber);
            return ResponseEntity.ok().build();
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
 
 public void controlScreenShotOrder(String serialNumber) throws ComputerError
 {
 
	 if(serialNumber== null) 
	 {
		 throw new ComputerError(1, "Nose ha enviado ninguna clase o carrito");     
	 }
 }
 
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/computers/send/screenshot", consumes = {"multipart/form-data"})
	    public ResponseEntity<?> sendScreenshotOrder(
	    		@RequestParam(required=true) MultipartFile screenshot	
	             )
	    {
	        try 
	        {
	            controlScreenShot(screenshot);
	            return ResponseEntity.ok().build();
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
	 
	 public void controlScreenShot(MultipartFile screenshot) throws ComputerError
	    {
	        if(screenshot.isEmpty()) 
	        {

	            throw new ComputerError(1, "Nose hay ninguna screenshot");
	        }
	    }
	 
	 
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/computers/send/fullInfo", consumes = {"application/json"})
	    public ResponseEntity<?> sendFullComputer(
	            @RequestHeader(required=false) String serialNumber,
	            @RequestHeader(required=false) String andaluciaId,
	            @RequestHeader(required=false) String computerNumber,
	            @RequestBody Computer computerInstance []
	    )
	    {
	        try
	        {
	            testComputer(computerInstance);
	        }
	        catch (ComputerError computerError)
	        {
	            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage());

	        }
	        try 
	        {
	            //TO DO
	        }
	        catch(Exception e) 
	        {
	            return ResponseEntity.status(500).body(e.getMessage());
	       }
	        return ResponseEntity.ok().build();
	    }
	 
	 
	 public void testComputer(Computer computerInstance[])throws ComputerError
	    {
	        if(computerInstance == null) 
	        {
	            ComputerError computerError = new ComputerError(1,"No hay ningun ordenador");

	            throw computerError;

	        }
	    }
	    
	 @RequestMapping(method = RequestMethod.POST, value = "/computers/send/status", consumes = {"application/json"})
	    public ResponseEntity<?> sendStatusComputer(
	    		@RequestBody List<Status> statuses	
	    )
	    {
	        try
	        {
	            testStatus(statuses);
	        }
	        catch (ComputerError computerError)
	        {
	            return ResponseEntity.status(400).body(computerError.getBodyExceptionMessage());

	        }
	        try 
	        {
	            //TO DO
	        }
	        catch(Exception e) 
	        {
	            return ResponseEntity.status(500).body(e.getMessage());
	       }
	        return ResponseEntity.ok().build();
	    }
	 
	 public void testStatus(List<Status> statuses)throws ComputerError
	    {
	        if(statuses.isEmpty()) 
	        {
	            ComputerError computerError = new ComputerError(1,"No hay ningun estado");

	            throw computerError;

	        }
	    }
	        
}
