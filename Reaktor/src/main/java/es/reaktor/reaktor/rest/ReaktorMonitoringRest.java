package es.reaktor.reaktor.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import es.reaktor.exceptions.ComputerError;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;
/**
 * @author Javier Martínez
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/computers")
@Slf4j
public class ReaktorMonitoringRest
{
	/** Attribute computerList */
	private List<Computer> computerList = new ArrayList<Computer>(List.of(
			new Computer("sn123", "and123", "cn123", "windows", "paco", new Location("0.5", 0, "trolley1"),
					new ArrayList<HardwareComponent>(), new ArrayList<Software>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn1234", "and1234", "cn12344", "windows", "paco", new Location("0.5", 0, "trolley1"),
					new ArrayList<HardwareComponent>(), new ArrayList<Software>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn123", "and12355", "cn123455", "windows", "paco", new Location("0.7", 0, "trolley2"),
					new ArrayList<HardwareComponent>(), new ArrayList<Software>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn123556", "and123556", "cn1234556", "windows", "paco", new Location("0.7", 0, "trolley2"),
					new ArrayList<HardwareComponent>(), new ArrayList<Software>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn123777", "and123777", "cn1234777", "windows", "paco", new Location("0.9", 0, "trolley3"),
					new ArrayList<HardwareComponent>(), new ArrayList<Software>(), new CommandLine(),
					new MonitorizationLog())

	));
	@RequestMapping(method = RequestMethod.GET, value = "/get/status")
	public ResponseEntity<?> getCommandLine(@RequestHeader(required=true) String serialNumber)
	{
		try
		{
			if(serialNumber != null || isUsable(serialNumber))
			{
				return ResponseEntity.ok(chekIfSerialNumberExist(serialNumber));
			}
			else
			{
				String error = "Any Paramater Is Empty or Blank";
				ComputerError computerError = new ComputerError(404, error, null);
				return ResponseEntity.status(404).body(computerError.toMap());
			}
		}	
		catch (Exception exception)
		{
			log.error(exception.getMessage());
			ComputerError computerError = new ComputerError(500, exception.getMessage(), exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
	}

	private boolean isUsable(String serialNumber)
	{
		boolean usable = false;
		if( serialNumber.isBlank() || serialNumber.isEmpty())
		{
			usable = true;
		}
		return usable;
	}
	
	private Computer chekIfSerialNumberExist(String serialNumber)
	{
		Computer computer = null;	
		for(Computer x : this.computerList)
		{
			if(x.getSerialNumber() == serialNumber)
			{
				computer = x;
			}			
		}
		return computer;	
	}
}

