package es.reaktor.reaktor.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import es.reaktor.models.CommandLine;

/**
 * @author David Martinez
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/computers")
public class ReaktorAdministrationRest
{
	@RequestMapping(method = RequestMethod.GET, value = "/admin/commandLine",consumes = "application/json")
	public ResponseEntity<?> sendInformation(
			@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) Integer plant,
			@RequestBody(required = true) CommandLine commandLine)
	{
		try
		{
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				if(serialNumber!=null) 
				{
					List<String> commandBlock = new ArrayList<String>(Arrays.asList(commandLine.getCommands()));
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY serialNumber

					return ResponseEntity.ok("CommandLine send OK serialNumber");
				}
				else if(trolley!=null) 
				{
					List<String> commandBlock = new ArrayList<String>(Arrays.asList(commandLine.getCommands()));
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY trolley
					return ResponseEntity.ok("CommandLine send OK trolley");
				}
				else if(classroom!=null) 
				{
					List<String> commandBlock = new ArrayList<String>(Arrays.asList(commandLine.getCommands()));
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY classroom
					return ResponseEntity.ok("CommandLine send OK classroom");
				}
				else if(plant!=null) 
				{
					List<String> commandBlock = new ArrayList<String>(Arrays.asList(commandLine.getCommands()));
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY plant
					return ResponseEntity.ok("CommandLine send OK plant");
				}
				else 
				{
					return ResponseEntity.status(490).body("ERROR 490");
				}
			}
			else
			{
				return ResponseEntity.status(404).body("ERROR 404");
			}
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(500).body("ERROR 500");
		}

	}

}