package es.reaktor.reaktor.rest;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.Peripheral;
import es.reaktor.reaktor.models.Software;

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
	
	/************************************************************************************************************************************************/
	/*******************************************************************REAK 100A *******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/commandLine") 
	public ResponseEntity postComputerCommandLine(
			@RequestHeader(value = "serialNumber", required = false)final String serialNumber,
			@RequestHeader(value = "classroom", required = false)final String classroom,
			@RequestHeader(value = "trolley", required = false)final String trolley,
			@RequestHeader(value = "plant", required = false)final Integer plant,
			@RequestHeader(value = "commandLineInstance", required = false)final CommandLine commandLine
			) throws ComputerError
	{	
		try
		{
			String [] commands = new String [0];
			this.checkCommands(serialNumber, classroom, trolley, plant, commandLine);
			if(!serialNumber.isEmpty())
			{
				commands = Arrays.copyOf(commands, commands.length+1);
				commands[commands.length-1] = serialNumber;
			}
			if(!classroom.isEmpty())
			{
				commands = Arrays.copyOf(commands, commands.length+1);
				commands[commands.length-1] = classroom;
			}
			if(!trolley.isEmpty())
			{
				commands = Arrays.copyOf(commands, commands.length+1);
				commands[commands.length-1] = trolley;
			}
			if(plant!=null)
			{
				commands = Arrays.copyOf(commands, commands.length+1);
				commands[commands.length-1] = String.valueOf(plant);
			}
			commandLine.setCommands(commands);
			//TODO: Mandarlo a la base de datos
			return ResponseEntity.ok().body("OK");
		}
		catch(ComputerError ex)
		{
			log.error("Administration error",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Internal server error",ex);
			return ResponseEntity.status(500).body("Server error");
		}
	}
	
	private void checkCommands(String serialNumber,String classroom,String trolley, Integer plant, CommandLine commandLine) throws ComputerError
	{
		if(commandLine==null)
		{
			throw new ComputerError(1, "Command line can't be null");
		}
		else if(serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant==null)
		{
			throw new ComputerError(2, "All params can't be null");
		}
	}
	
	/************************************************************************************************************************************************/
	/*******************************************************************REAK 101A *******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerShutdown(@RequestHeader(value = "serialNumber", required = false)final String serialNumber,
												 @RequestHeader(value = "classroom", required = false)final String classroom,
												 @RequestHeader(value = "trolley", required = false)final String trolley,
												 @RequestHeader(value = "plant", required = false)final Integer plant)
	{
		try
		{
			this.checkParamsComputerShutdown(serialNumber, classroom, trolley, plant);
			return ResponseEntity.ok().body("OK") ;
		}
		catch (ComputerError exception)
		{
			String message = "Administration error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		}
		catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	public void checkParamsComputerShutdown(String serialNumber, String classroom, String trolley, Integer plant) throws ComputerError
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

	/************************************************************************************************************************************************/
	/*******************************************************************REAK 102A *******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerRestart(@RequestHeader(value = "serialNumber", required = false)final String serialNumber,
												 @RequestHeader(value = "classroom", required = false)final String classroom,
												 @RequestHeader(value = "trolley", required = false)final String trolley,
												 @RequestHeader(value = "plant", required = false)final Integer plant)
	{
		try
		{
			this.checkParamsPutComputerRestart(serialNumber, classroom, trolley, plant);
			return ResponseEntity.ok().body("OK") ;
		}
		catch (ComputerError exception)
		{
			String message = "Administration error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		}
		catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	public void checkParamsPutComputerRestart(String serialNumber, String classroom, String trolley, Integer plant) throws ComputerError
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

	/************************************************************************************************************************************************/
	/*******************************************************************REAK 103A *******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/by_body/", consumes = {"application/json"})
	public ResponseEntity<?> addByBody(
			@RequestHeader(value="classroom", required = false) final String classroom,
			@RequestHeader(value="trolley", required = false) final String trolley,
			@RequestBody(required=true) Peripheral[] peripherals)
	//TUDU...
	{
		try
		{	
			this.checkParamsAddByBody(classroom, trolley, peripherals);
			return ResponseEntity.ok().body("All OK");
		}
		catch (ComputerError ce)
		{
			return ResponseEntity.status(400).body(ce.getBodyMessageException()) ;
		}
		catch(Exception e) 
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	private void checkParamsAddByBody(String classroom,String trolley, Peripheral[] peripheral) throws ComputerError
    {
        if(classroom.isEmpty() && trolley.isEmpty())
        {
            throw new ComputerError(2, "All params can't be null");
        }
        else if(peripheral==null) 
        {
        	throw new ComputerError(5, "The Pheriferal can't be null");
        }
    }

	/************************************************************************************************************************************************/
	/*******************************************************************REAK 104A *******************************************************************/
	/************************************************************************************************************************************************/


	/************************************************************************************************************************************************/
	/*******************************************************************REAK 105A *******************************************************************/
	/************************************************************************************************************************************************/


	/************************************************************************************************************************************************/
	/*******************************************************************REAK 106A *******************************************************************/
	/************************************************************************************************************************************************/
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
			this.checkParamsPostComputerExeFile(serialNumber, classroom, trolley, plant, execFile);
			//TODO: Cuando se implante la bbdd llamar al ordenador ye incluirlo en softaware
			return ResponseEntity.ok().body("Computer update success");
		}
		catch(ComputerError ex)
		{
			log.error("Computer update failed",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(500).body("Server error");
		}
	}
	
	private void checkParamsPostComputerExeFile(String serialNumber,String classroom,String trolley,Integer plant,File execFile) throws ComputerError
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

	/************************************************************************************************************************************************/
	/*******************************************************************REAK 107A *******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> sendSoftware(@RequestHeader(value = "classroom", required = false)final String classroom,
												 @RequestHeader(value = "trolley", required = false)final String trolley,
												 @RequestHeader(value = "professor", required = false)final String professor,
												 @RequestBody(required = true)final Software[] softwareInstance)
	{
		try
		{
			this.checkParamsSendSoftware(classroom, trolley, professor, softwareInstance);
			return ResponseEntity.ok().body("OK") ;
		}
		catch (ComputerError exception)
		{
			String message = "Software error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		}
		catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	public void checkParamsSendSoftware(String classroom, String trolley, String professor, Software[] softwareInstance) throws ComputerError
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

	/************************************************************************************************************************************************/
	/*******************************************************************REAK 108A *******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> uninstallSoftware(@RequestHeader(value = "classroom", required = false)final String classroom,
												 @RequestHeader(value = "trolley", required = false)final String trolley,
												 @RequestHeader(value = "professor", required = false)final String professor,
												 @RequestBody(required = true)final Software[] softwareInstance)
	{
		try
		{
			this.checkParamsUninstallSoftware(classroom, trolley, professor, softwareInstance);
			return ResponseEntity.ok().body("OK") ;
		}
		catch (ComputerError exception)
		{
			String message = "Software error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		}
		catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	public void checkParamsUninstallSoftware(String classroom, String trolley, String professor, Software[] softwareInstance) throws ComputerError
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

	/************************************************************************************************************************************************/
	/*******************************************************************REAK 109A *******************************************************************/
	/************************************************************************************************************************************************/
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
			this.checkParamsUpdatecomputer(serialNumber, andaluciaId, computerNumber, computer);
			//TODO: Obtener datos del ordenador desde la bbdd editarlo y subirlo
			return ResponseEntity.ok().body("OK");
		}
		catch(ComputerError ex)
		{
			log.error("Administration error",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(500).body("Server error");
		}
		
		
	}
	
	private void checkParamsUpdatecomputer(String serialNumber,String andaluciaId, String computerNumber, Computer computer) throws ComputerError
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