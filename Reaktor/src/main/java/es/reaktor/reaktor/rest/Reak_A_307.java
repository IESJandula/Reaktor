package es.reaktor.reaktor.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileCacheImageInputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.exceptions.ComputerError;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Javier Martínez Megías
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/computers")
@Slf4j
public class Reak_A_307
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

	/**
	 * Method sendInformation to send information of commands to computers
	 * 
	 * @param serialNumber the serial number of computer
	 * @param classroom    the classroom
	 * @param trolley      the trolley
	 * @param plant        the plant
	 * @param File the execFile
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/file", consumes = "multipart/form-data")
	public ResponseEntity<?> postComputerCommandLine(
			@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) Integer plant,
			@RequestBody(required = true) File execFile)
	{
		try
		{
			List<Computer> fileComputers = new ArrayList<Computer>();
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				// --- CHECKING IF ANY PARAMETER IS BLANK OR EMPTY ---
				if (this.checkBlanksOrEmptys(serialNumber, classroom, trolley))
				{
					String error = "Any Paramater Is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}

				if (serialNumber != null)
				{
					// ALL FILE ON SPECIFIC COMPUTER BY serialNumber
					this.fileToComputerBySerialNumber(serialNumber, fileComputers);
					
				}
				if (trolley != null)
				{
					// ALL FILE ON SPECIFIC COMPUTER BY trolley
					this.fileToComputerByTrolley(trolley, fileComputers);
					
				}
				if (classroom != null)
				{
					// ALL FILE ON SPECIFIC COMPUTER BY classroom
					this.fileToComputerByClassroom(classroom, fileComputers);
					
				}
				if (plant != null)
				{
					// ALL FILE ON SPECIFIC COMPUTER BY plant
					this.fileToComputerByPlant(plant, fileComputers);
					
				}
				
				// --- RETURN OK RESPONSE ---
				return ResponseEntity.ok(this.computerListToMap( fileComputers));
			}
			else
			{
				// COMMANDS RUN ON ALL COMPUTERS
				this.fileToAllComputers(fileComputers);
				return ResponseEntity.ok(this.computerListToMap(fileComputers));
			}
		}
		catch (Exception exception)
		{
			log.error(exception.getMessage(),exception);
			ComputerError computerError = new ComputerError(500, exception.getMessage(), exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
		
	}
	
	private boolean checkBlanksOrEmptys(String serialNumber, String classroom, String trolley)
	{
		if (serialNumber != null)
		{
			if (serialNumber.isBlank() || serialNumber.isEmpty())
			{
				// --- IF IS PARAMETER IS BLANK OR EMPTY ---
				String error = "Serial Number Is Empty or Blank";
				log.error(error);
				return true;
			}
		}
		if (trolley != null)
		{
			if (trolley.isBlank() || trolley.isEmpty())
			{
				// --- IF IS PARAMETER IS BLANK OR EMPTY ---
				String error = "Trolley Is Empty or Blank";
				log.error(error);
				return true;
			}
		}
		if (classroom != null)
		{
			if (classroom.isBlank() || classroom.isEmpty())
			{
				// --- IF IS PARAMETER IS BLANK OR EMPTY ---
				String error = "Classroom Is Empty or Blank";
				log.error(error);
				return true;
			}
		}
		return false;
	}
	
	private void fileToAllComputers(List<Computer> fileComputers)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			fileComputers.add(computer);
		}
	}
	private void fileToComputerByPlant(Integer plant, List<Computer> fileComputers)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getLocation().getPlant() == plant)
			{								
				fileComputers.add(computer);
			}
		}
	}
	private void fileToComputerByClassroom(String classroom, List<Computer> fileComputers)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getLocation().getClassroom().equalsIgnoreCase(classroom))
			{
				fileComputers.add(computer);
			}
		}
	}
	private void fileToComputerByTrolley(String trolley, List<Computer> fileComputers)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getLocation().getTrolley().equalsIgnoreCase(trolley))
			{
				fileComputers.add(computer);
			}
		}
	}
	private void fileToComputerBySerialNumber(String serialNumber, List<Computer> fileComputers)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				fileComputers.add(computer);
			}
		}
	}
	
	private Map<String, List<Computer>> computerListToMap(List<Computer> fileComputers)
	{
		Map<String, List<Computer>> computerListMap = new HashMap<>();
		computerListMap.put("computers", fileComputers);
		return computerListMap;
	}
}
