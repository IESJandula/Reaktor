package es.reaktor.reaktor.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.Software;

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
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> uninstallSoftware(@RequestHeader(value = "classroom", required = false)final String classroom,
												 @RequestHeader(value = "trolley", required = false)final String trolley,
												 @RequestHeader(value = "professor", required = false)final String professor,
												 @RequestBody(required = true)final Software[] softwareInstance)
	{
		try
		{
			this.checkParam(classroom, trolley, professor, softwareInstance);
			return ResponseEntity.ok().body("OK") ;
		}
		catch (ComputerError exception)
		{
			String message = "Software error";
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
	
	public void checkParam(String classroom, String trolley, String professor, Software[] softwareInstance) throws ComputerError
	{
		if(classroom.isEmpty())
		{
			throw new ComputerError(1, "Classroom is null");
		}
		else if(trolley.isEmpty())
		{
			throw new ComputerError(2, "Trolley is null");
		}
		else if(professor.isEmpty())
		{
			throw new ComputerError(3, "Professor is null");
		}
		else if(softwareInstance == null)
		{
			throw new ComputerError(4, "SoftwareInstance is null");
		}
		
	}
}
