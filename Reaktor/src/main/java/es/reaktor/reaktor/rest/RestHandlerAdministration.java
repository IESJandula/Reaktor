package es.reaktor.reaktor.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;

@RequestMapping(value = "/computers", produces = {"application/json"})
@RestController

/**
 * @author Alejandro Cazalla Pérez
 */
public class RestHandlerAdministration
{
	/**
	 * Public constructor
	 */
	public RestHandlerAdministration()
	{
		// Empty constructor
	}
	
	final static Logger logger = LogManager.getLogger();
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerShutdown(@RequestHeader(value = "serialNumber", required = false)final String serialNumber,
												 @RequestHeader(value = "classroom", required = false)final String classroom,
												 @RequestHeader(value = "trolley", required = false)final String trolley,
												 @RequestHeader(value = "plant", required = false)final Integer plant)
	{
		try
		{
			this.checkParam(serialNumber, classroom, trolley, plant);
			return ResponseEntity.ok().body("OK") ;
		}
		catch (ComputerError exception)
		{
			String message = "Administration error";
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
	
	public void checkParam(String serialNumber, String classroom, String trolley, Integer plant) throws ComputerError
	{
		if(serialNumber.isEmpty())
		{
			throw new ComputerError(1, "Serial Number is null");
		}
		else if(classroom.isEmpty())
		{
			throw new ComputerError(2, "Classroom is null");
		}
		else if(trolley.isEmpty())
		{
			throw new ComputerError(3, "Trolley is null");
		}
		else if(plant == null)
		{
			throw new ComputerError(4, "Plant is null");
		}
		
	}
}
