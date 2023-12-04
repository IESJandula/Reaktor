package es.reaktor.reaktor.rest;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exception.ComputerError;

/**
 * 
 * @author Pablo Ruiz
 *
 */
@RequestMapping(value = "/computers")
@RestController
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/file")
	public ResponseEntity postComputerExeFile(
			@RequestHeader(value = "serialNumber",required = false) final String serialNumber,
			@RequestHeader(value = "classroom",required = false) final String classroom,
			@RequestHeader(value = "trolley",required = false) final String trolley,
			@RequestHeader(value = "plant",required = false) final Integer plant,
			@RequestHeader(value = "execFile",required = false) final File execFile
			)
	{
		try
		{
			this.checkParams(serialNumber, classroom, trolley, plant, execFile);
			//TODO: Cuando se implante la bbdd llamar al ordenador ye incluirlo en softaware
			return ResponseEntity.ok().body("Computer update success");
		}
		catch(ComputerError ex)
		{
			log.error("Computer update failed",ex);
			return ResponseEntity.status(404).body(ex.getBodyExceptionMessage());
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(500).body("Server error");
		}
	}
	
	private void checkParams(String serialNumber,String classroom,String trolley,Integer plant,File execFile) throws ComputerError
	{
		if(serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant==null)
		{
			throw new ComputerError(1,"All params can't be null");
		}
		else if(execFile==null)
		{
			throw new ComputerError(3,"The file can't be null");
		}
		else if(execFile.getName().endsWith(".exe") || execFile.getName().endsWith(".cfg") || execFile.getName().endsWith(".exec"))
		{
			throw new ComputerError(4,"The file extensiojn its wrong");
		}
	}
}
