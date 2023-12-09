package es.reaktor.reaktor.rest;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;

@RequestMapping(value = "/computers", produces = {"application/json"})
@RestController

/**
 * @author Alejandro Cazalla Pérez
 */
public class RestHandlerMonitoring
{
	/**
	 * Public constructor
	 */
	public RestHandlerMonitoring()
	{
		// Empty constructor
	}
	
	final static Logger logger = LogManager.getLogger();
	
	@RequestMapping(method = RequestMethod.POST, value = "/send/screenshots", consumes = "multipart/form-data")
	public ResponseEntity<?> sendScreenshot(@RequestPart(value = "screenshot", required = true)final File screenshot)
	{
		try
		{
			this.checkParam(screenshot);
			return ResponseEntity.ok().body("Getting success") ;
		}
		catch (ComputerError exception)
		{
			String message = "Computer error getting";
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
	
	public void checkParam(File screenshot) throws ComputerError
	{
		if(screenshot == null)
		{
			throw new ComputerError(1, "Error with the screenshot");
		}
	}
}
