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
	
	@RequestMapping(method = RequestMethod.GET, value = "/screenshots")
	public ResponseEntity<?> getScreenshots(@RequestHeader(value = "serialNumber", required = false)final String serialNumber)
	{
		try
		{
			this.checkSerialNumber(serialNumber);
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
	
	public void checkSerialNumber(String serialNumber) throws ComputerError
	{
		if(serialNumber.isEmpty())
		{
			throw new ComputerError(1, "Serial Number is null");
		}
	}
}
