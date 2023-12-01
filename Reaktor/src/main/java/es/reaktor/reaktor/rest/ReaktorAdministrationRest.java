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
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;

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
      new Computer("sn123","and123","cn123","windows","paco",new Location("0.5",0,"trolley1"),new ArrayList<HardwareComponent>(),new ArrayList<Software>(),new CommandLine(),new MonitorizationLog()),
      new Computer("sn1234","and1234","cn12344","windows","paco",new Location("0.5",0,"trolley1"),new ArrayList<HardwareComponent>(),new ArrayList<Software>(),new CommandLine(),new MonitorizationLog()),
      new Computer("sn123","and12355","cn123455","windows","paco",new Location("0.7",0,"trolley2"),new ArrayList<HardwareComponent>(),new ArrayList<Software>(),new CommandLine(),new MonitorizationLog()),
      new Computer("sn123556","and123556","cn1234556","windows","paco",new Location("0.7",0,"trolley2"),new ArrayList<HardwareComponent>(),new ArrayList<Software>(),new CommandLine(),new MonitorizationLog()),
      new Computer("sn123777","and123777","cn1234777","windows","paco",new Location("0.9",0,"trolley3"),new ArrayList<HardwareComponent>(),new ArrayList<Software>(),new CommandLine(),new MonitorizationLog())
			
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
			// --- GETTING THE COMMAND BLOCK ----
			List<String> commands = new ArrayList<String>(commandLine.getCommands());
			
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				if(serialNumber!=null) 
				{
					//ALL COMMANDS ON SPECIFIC COMPUTER BY serialNumber
					
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getSerialNumber().equalsIgnoreCase(serialNumber)) 
						{
							computer.setCommandLine(new CommandLine(commands));
							int index = computerList.indexOf(computer);
							computerList.remove(computer);
							computerList.add(index,computer);
						}
					}
					
					return ResponseEntity.ok("CommandLine send OK serialNumber "+computerList);
				}
				else if(trolley!=null) 
				{
					//ALL COMMANDS ON SPECIFIC COMPUTER BY trolley
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getTrolley().equalsIgnoreCase(trolley)) 
						{
							computer.setCommandLine(new CommandLine(commands));
							int index = computerList.indexOf(computer);
							computerList.remove(computer);
							computerList.add(index,computer);
						}
					}
					return ResponseEntity.ok("CommandLine send OK trolley"+computerList);
				}
				else if(classroom!=null) 
				{
					//ALL COMMANDS ON SPECIFIC COMPUTER BY classroom
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getClassroom().equalsIgnoreCase(classroom)) 
						{
							computer.setCommandLine(new CommandLine(commands));
							int index = computerList.indexOf(computer);
							computerList.remove(computer);
							computerList.add(index,computer);
						}
					}
					return ResponseEntity.ok("CommandLine send OK classroom"+computerList);
				}
				else
				{
					// plant!=null  (The last Possible)
					//ALL COMMANDS ON SPECIFIC COMPUTER BY plant
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getPlant()==plant) 
						{
							computer.setCommandLine(new CommandLine(commands));
							int index = computerList.indexOf(computer);
							computerList.remove(computer);
							computerList.add(index,computer);
						}
					}
					return ResponseEntity.ok("CommandLine send OK plant"+computerList);
				}
			}
			else
			{
				//COMMANDS RUN ON ALL COMPUTERS
				for(int i = 0;i<computerList.size();i++) 
				{
					Computer computer = computerList.get(i);
					computer.setCommandLine(new CommandLine(commands));
					int index = computerList.indexOf(computer);
					computerList.remove(computer);
					computerList.add(index,computer);
				}
				return ResponseEntity.ok("CommandLine send OK ALL COMPUTERS"+computerList);
			}
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(500).body("ERROR 500"+exception);
		}
	}
}