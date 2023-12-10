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
import es.reaktor.reaktor.models.Computer;

@RequestMapping(value = "/computers", produces = {"application/json"})
@RestController
public class RestHandlerMonitoring 
{
	
	/**Class logger */
	Logger log = LogManager.getLogger();
	/**
	 * Default constructor
	 */
	public RestHandlerMonitoring()
	{
		//Public constructor
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/send/fullInfo", consumes = "application/json" )
	public ResponseEntity<?> sendFullComputer (
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "andaluciaId", required = false) final String andaluciaId,
			@RequestHeader(value = "computerNumber", required = false) final String computerNumber,
			@RequestBody(required = true) final Computer computerInstance
			)
	{
		try
		{
			this.checkParams(serialNumber, andaluciaId, computerNumber, computerInstance);
			//TODO: Mandar la informacion al ordenador afectado obtenido de otro endpoint
			return ResponseEntity.ok().body("OK");
		}
		catch(ComputerError ex)
		{
			log.error("Administration error",ex);
			return ResponseEntity.status(404).body(ex.getBodyExceptionMessage());
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(404).body("Server error");
		}
	}
	
	private void checkParams(String serialNumber,String andaluciaId,String computerNumber,Computer computerInstance) throws ComputerError
	{
		if(serialNumber.isEmpty() && andaluciaId.isEmpty() && computerNumber.isEmpty())
		{
			throw new ComputerError(1, "All params can't be null");
		}
		else if(computerInstance == null)
		{
			throw new ComputerError(3, "Computer to send info can't be null");
		}
	}
	
	
}
