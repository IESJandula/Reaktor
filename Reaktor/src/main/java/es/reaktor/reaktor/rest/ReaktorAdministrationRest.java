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
import es.reaktor.models.Computer;
import es.reaktor.models.Location;

/**
 * @author David Martinez
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/computers")
public class ReaktorAdministrationRest
{
	private List<Computer>computerList = new ArrayList<Computer>(List.of(
			//new Computer("sn123","ad123","cn123","windows","Paco",new Location(),new String[0],new String[0],"CommandLine","Monitorization")
			
	));
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/commandLine",consumes = "application/json")
	public ResponseEntity<?> sendInformation(
			@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) Integer plant,
			@RequestBody(required = true) CommandLine commandLine)
	{
		try
		{
			List<String> commandBlock = new ArrayList<String>(Arrays.asList(commandLine.getCommands()));
			
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				if(serialNumber!=null) 
				{
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY serialNumber

					return ResponseEntity.ok("CommandLine send OK serialNumber");
				}
				else if(trolley!=null) 
				{
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY trolley
					return ResponseEntity.ok("CommandLine send OK trolley");
				}
				else if(classroom!=null) 
				{
					//TO-DO ALL COMMANDS ON SPECIFIC COMPUTER BY classroom
					return ResponseEntity.ok("CommandLine send OK classroom");
				}
				else if(plant!=null) 
				{
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
				//TO-DO COMMANDS RUN ON ALL COMPUTERS
				return ResponseEntity.ok("CommandLine send OK ALL COMPUTERS");
			}
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(500).body("ERROR 500");
		}

	}

}