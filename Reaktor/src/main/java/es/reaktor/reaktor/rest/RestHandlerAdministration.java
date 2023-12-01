package es.reaktor.reaktor.rest;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
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
			return ResponseEntity.status(404).body(ex.getBodyExceptionMessage());
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
	
}
