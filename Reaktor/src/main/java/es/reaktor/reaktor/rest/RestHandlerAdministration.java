package es.reaktor.reaktor.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exception.ComputerError;
import es.reaktor.reaktor.models.Computer;
/**
 * 
 * @author Pablo Ruiz Canovas
 *
 */
@RestController
@RequestMapping(value = "/computers",produces = {"application/json"})
public class RestHandlerAdministration 
{
	/**Class logger */
	private static Logger log = LogManager.getLogger();
	/**
	 * Default constructor
	 */
	public RestHandlerAdministration()
	{
		//Public constructor
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/edit")
	public ResponseEntity updateComputer (
			@RequestHeader(value = "serialNumber",required = false) final String serialNumber,
			@RequestHeader(value = "andaluciaId",required = false) final String andaluciaId,
			@RequestHeader(value = "computerNumber",required = false) final String computerNumber,
			@RequestHeader(value = "computerInstance",required = true) final Computer computer
			)
	{
		try
		{
			this.checkParams(serialNumber, andaluciaId, computerNumber, computer);
			//TODO: Obtener datos del ordenador desde la bbdd editarlo y subirlo
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
			return ResponseEntity.status(500).body("Server error");
		}
		
		
	}
	
	private void checkParams(String serialNumber,String andaluciaId, String computerNumber, Computer computer) throws ComputerError
	{
		if(serialNumber.isEmpty() && andaluciaId.isEmpty() && computerNumber.isEmpty())
		{
			throw new ComputerError(1,"All params can't be null");
		}
		else if(computer==null)
		{
			throw new ComputerError(6,"Computer can't be null");
		}
		
	}
}
