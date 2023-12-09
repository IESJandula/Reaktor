package es.reaktor.reaktor.rest;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.HorarioError;

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
	
	@RequestMapping(method = RequestMethod.POST, value = "/get/status", consumes = "multipart/form-data")
	public ResponseEntity<?> sendFile(@RequestPart(value = "csvFile", required = true)final File csvFile)
	{
		try
		{
			this.checkFile(csvFile);
			return ResponseEntity.ok().body("Getting success") ;
		}
		catch (HorarioError exception)
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
	
	public void checkFile(File File) throws HorarioError
	{
		if(File == null)
		{
			throw new HorarioError(1, "Error with the file");
		}
	}
}
