package es.reaktor.reaktor.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import es.reaktor.exceptions.ComputerError;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;
import lombok.extern.slf4j.Slf4j;

/**
 * @author David Martinez
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/computers")
@Slf4j
public class ReaktorAdministrationRest
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
	 * @param commandLine  the commnadLine Object
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/commandLine", consumes = "application/json")
	public ResponseEntity<?> postComputerCommandLine(
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

			// --- IF ANY OF THE PARAMETERS IS NOT NULL ---
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				String methodsUsed = "";

				// --- CHECKING IF ANY PARAMETER IS BLANK OR EMPTY ---
				if (this.checkBlanksOrEmptys(serialNumber, classroom, trolley))
				{
					String error = "Any Paramater Is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}

				if (serialNumber != null)
				{
					// ALL COMMANDS ON SPECIFIC COMPUTER BY serialNumber
					this.commandsToComputerBySerialNumber(serialNumber, commands);
					methodsUsed += "serialNumber,";
				}
				if (trolley != null)
				{
					// ALL COMMANDS ON SPECIFIC COMPUTER BY trolley
					this.commandsToComputerByTrolley(trolley, commands);
					methodsUsed += "trolley,";
				}
				if (classroom != null)
				{
					// ALL COMMANDS ON SPECIFIC COMPUTER BY classroom
					this.commandsToComputerByClassroom(classroom, commands);
					methodsUsed += "classroom,";
				}
				if (plant != null)
				{
					// ALL COMMANDS ON SPECIFIC COMPUTER BY plant
					this.commandsToComputerByPlant(plant, commands);
					methodsUsed += "plant,";
				}
				log.info("Parameters Used: " + methodsUsed);
				// --- RETURN OK RESPONSE ---
				return ResponseEntity.ok(this.computerListToMap());
			}
			else
			{
				// COMMANDS RUN ON ALL COMPUTERS
				this.commandsToAllComputers(commands);
				log.info("By all Computers");
				return ResponseEntity.ok(this.computerListToMap());
			}
		}
		catch (Exception exception)
		{
			log.error(exception.getMessage());
			ComputerError computerError = new ComputerError(500, exception.getMessage(), exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
	}
	
	
	/**
	 * Method shutdownComputers
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/shutdown")
	public ResponseEntity<?> putComputerShutdown(
			@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) Integer plant)
	{
		try
		{
			Set<Computer> shutdownComputerListDistint = new HashSet<Computer>();
			
			// --- IF ANY OF THE PARAMETERS IS NOT NULL ---
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				String methodsUsed = "";

				// --- CHECKING IF ANY PARAMETER IS BLANK OR EMPTY ---
				if (this.checkBlanksOrEmptys(serialNumber, classroom, trolley))
				{
					String error = "Any Paramater Is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}

				if (serialNumber != null)
				{
					// SHUTDOWN SPECIFIC COMPUTER BY serialNumber
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							shutdownComputerListDistint.add(computer);
						}
					}
					methodsUsed += "serialNumber,";
				}
				if (trolley != null)
				{
					// SHUTDOWN SPECIFIC COMPUTER BY trolley
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getTrolley().equalsIgnoreCase(trolley))
						{
							shutdownComputerListDistint.add(computer);
						}
					}
					methodsUsed += "trolley,";
				}
				if (classroom != null)
				{
					// SHUTDOWN SPECIFIC COMPUTER BY classroom
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getClassroom().equalsIgnoreCase(classroom))
						{
							shutdownComputerListDistint.add(computer);
						}
					}
					methodsUsed += "classroom,";
				}
				if (plant != null)
				{
					// SHUTDOWN SPECIFIC COMPUTER BY plant
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getPlant()==plant)
						{
							shutdownComputerListDistint.add(computer);
						}
					}
					methodsUsed += "plant,";
				}
				log.info("Parameters Used: " + methodsUsed);
				// --- RETURN OK RESPONSE ---
				return ResponseEntity.ok(this.shutdownComputerListDistintToMap(shutdownComputerListDistint));
			}
			else
			{
				// SHUTDOWN ALL COMPUTERS
				for(int i = 0;i<computerList.size();i++) 
				{
					Computer computer = computerList.get(i);
					shutdownComputerListDistint.add(computer);
				}
				log.info("By all Computers");
				return ResponseEntity.ok(this.shutdownComputerListDistintToMap(shutdownComputerListDistint));
			}
		}
		catch (Exception exception)
		{
			log.error(exception.getMessage());
			ComputerError computerError = new ComputerError(500, exception.getMessage(), exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
	}
	
	/**
	 * Method shutdownComputers
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart")
	public ResponseEntity<?> putComputerRestart(
			@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) Integer plant)
	{
		try
		{
			Set<Computer> restartComputerListDistint = new HashSet<Computer>();
			
			// --- IF ANY OF THE PARAMETERS IS NOT NULL ---
			if (serialNumber != null || classroom != null || trolley != null || plant != null)
			{
				String methodsUsed = "";

				// --- CHECKING IF ANY PARAMETER IS BLANK OR EMPTY ---
				if (this.checkBlanksOrEmptys(serialNumber, classroom, trolley))
				{
					String error = "Any Paramater Is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}

				if (serialNumber != null)
				{
					// RESTART SPECIFIC COMPUTER BY serialNumber
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							restartComputerListDistint.add(computer);
						}
					}
					methodsUsed += "serialNumber,";
				}
				if (trolley != null)
				{
					// RESTART SPECIFIC COMPUTER BY trolley
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getTrolley().equalsIgnoreCase(trolley))
						{
							restartComputerListDistint.add(computer);
						}
					}
					methodsUsed += "trolley,";
				}
				if (classroom != null)
				{
					// RESTART SPECIFIC COMPUTER BY classroom
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getClassroom().equalsIgnoreCase(classroom))
						{
							restartComputerListDistint.add(computer);
						}
					}
					methodsUsed += "classroom,";
				}
				if (plant != null)
				{
					// RESTART SPECIFIC COMPUTER BY plant
					for(int i = 0;i<computerList.size();i++) 
					{
						Computer computer = computerList.get(i);
						if(computer.getLocation().getPlant()==plant)
						{
							restartComputerListDistint.add(computer);
						}
					}
					methodsUsed += "plant,";
				}
				log.info("Parameters Used: " + methodsUsed);
				// --- RETURN OK RESPONSE --- 
				return ResponseEntity.ok(this.shutdownComputerListDistintToMap(restartComputerListDistint));
			}
			else
			{
				// RESTART ALL COMPUTERS
				for(int i = 0;i<computerList.size();i++) 
				{
					Computer computer = computerList.get(i);
					restartComputerListDistint.add(computer);
				}
				log.info("By all Computers");
				return ResponseEntity.ok(this.restartComputerListDistintToMap(restartComputerListDistint));
			}
		}
		catch (Exception exception)
		{
			log.error(exception.getMessage());
			ComputerError computerError = new ComputerError(500, exception.getMessage(), exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
	}

	/**
	 * Method checkBlanksOrEmptys
	 * 
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @return boolean
	 */
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

	/**
	 * Method commandsToAllComputers send commands to all computers
	 * 
	 * @param commands
	 */
	private void commandsToAllComputers(List<String> commands)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			computer.setCommandLine(new CommandLine(commands));
			computerList.remove(computer);
			computerList.add(i, computer);
		}
	}

	/**
	 * Method commandsToComputerByPlant send commands to computers by plant
	 * 
	 * @param plant
	 * @param commands
	 */
	private void commandsToComputerByPlant(Integer plant, List<String> commands)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getLocation().getPlant() == plant)
			{
				computer.setCommandLine(new CommandLine(commands));
				computerList.remove(computer);
				computerList.add(i, computer);
			}
		}
	}

	/**
	 * Method commandsToComputerByClassroom send commands to computers by classroom
	 * 
	 * @param classroom
	 * @param commands
	 */
	private void commandsToComputerByClassroom(String classroom, List<String> commands)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getLocation().getClassroom().equalsIgnoreCase(classroom))
			{
				computer.setCommandLine(new CommandLine(commands));
				computerList.remove(computer);
				computerList.add(i, computer);
			}
		}
	}

	/**
	 * Method commandsToComputerByTrolley send commands to computers by trolley
	 * 
	 * @param trolley
	 * @param commands
	 */
	private void commandsToComputerByTrolley(String trolley, List<String> commands)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getLocation().getTrolley().equalsIgnoreCase(trolley))
			{
				computer.setCommandLine(new CommandLine(commands));
				computerList.remove(computer);
				computerList.add(i, computer);
			}
		}
	}

	/**
	 * Method commandsToComputerBySerialNumber send commands to computers by
	 * serialNumber
	 * 
	 * @param serialNumber
	 * @param commands
	 */
	private void commandsToComputerBySerialNumber(String serialNumber, List<String> commands)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			if (computer.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				computer.setCommandLine(new CommandLine(commands));
				computerList.remove(computer);
				computerList.add(i, computer);
			}
		}
	}

	/**
	 * Method computerListToMap , method for debug or testing only
	 * 
	 * @return Map with list of computers
	 */
	private Map<String, List<Computer>> computerListToMap()
	{
		Map<String, List<Computer>> computerListMap = new HashMap<>();
		computerListMap.put("computers", computerList);
		return computerListMap;
	}

	/**
	 * Method shutdownComputerListDistintToMap
	 * @param shutdownComputerList
	 * @return
	 */
	private Map<String, List<Computer>> shutdownComputerListDistintToMap(Collection shutdownComputerList)
	{
		Map<String, List<Computer>> computerListMap = new HashMap<>();
		computerListMap.put("computers", new ArrayList<Computer>(shutdownComputerList));
		return computerListMap;
	}
	
	/**
	 * Method shutdownComputerListDistintToMap
	 * @param shutdownComputerList
	 * @return
	 */
	private Map<String, List<Computer>> restartComputerListDistintToMap(Collection shutdownComputerList)
	{
		Map<String, List<Computer>> computerListMap = new HashMap<>();
		computerListMap.put("computers", new ArrayList<Computer>(shutdownComputerList));
		return computerListMap;
	}
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
	
	
	/**
	 * Method fileToAllComputers send to all classroom computers
	 * 
	 * @param file
	 */
	private void fileToAllComputers(List<Computer> fileComputers)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer computer = computerList.get(i);
			fileComputers.add(computer);
		}
	}
	/**
	 * Method fileToComputerByPlant send file by plant computers
	 * 
	 * @param file
	 */
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
	
	/**
	 * Method fileToComputerByClassroom send file by classroom computers
	 * 
	 * @param file
	 */
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
	
	/**
	 * Method fileToComputerByTrolley send file by trolley computers
	 * 
	 * @param file
	 */
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
	/**
	 * Method fileToComputerBySerialNumber send file by serial number computers
	 * 
	 * @param file
	 */
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
	
	
	/**
	 * 
	 * @param fileComputers change the list to a map
	 * @return
	 */
	private Map<String, List<Computer>> computerListToMap(List<Computer> fileComputers)
	{
		Map<String, List<Computer>> computerListMap = new HashMap<>();
		computerListMap.put("computers", fileComputers);
		return computerListMap;
	}
	/**
	 * Method handleTypeMismatch method for the spring interal input mismatch
	 * parameter
	 * 
	 * @param MethodArgumentTypeMismatchException exception
	 * @return ResponseEntity
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException exception)
	{
		String name = exception.getName();
		String type = exception.getRequiredType().getSimpleName();
		Object value = exception.getValue();
		String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);
		log.error(message);
		ComputerError computerError = new ComputerError(404, message, exception);
		return ResponseEntity.status(404).body(computerError.toMap());
	}
}