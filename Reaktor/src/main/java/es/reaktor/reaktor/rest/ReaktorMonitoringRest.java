package es.reaktor.reaktor.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.reaktor.exceptions.ComputerError;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;
import es.reaktor.models.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Javier Martínez Megías
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
	/** Attribute shutDownComputerList */
	private List<Computer> shutDownComputerList = new ArrayList<Computer>(List.of(new Computer("sn123556", "and123556",
			"cn1234556", "windows", "paco", new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(),
			new ArrayList<Software>(), new CommandLine(), new MonitorizationLog())));
	/** Attribute restartDownComputerList */
	private List<Computer> restartDownComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(), new MonitorizationLog())));

	/** Attribute execCommandsComputerList */
	private List<Computer> execCommandsComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("mkdir .\\carpeta1David")), new MonitorizationLog())));

	/** Attribute blockDispositiveComputerList */
	private List<Computer> blockDispositiveComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(), new MonitorizationLog())));

	/** Attribute openWebComputerList */
	private List<Computer> openWebComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome www.iesjandula.es")), new MonitorizationLog())));

	/** Attribute installAppComputerList */
	private List<Computer> installAppComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute unistallAppComputerList */
	private List<Computer> unistallAppComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute configurationFileComputerList */
	private List<Computer> configurationFileComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute updateAndaluciaComputerList */
	private List<Computer> updateAndaluciaComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute updateSerialNumberComputerList */
	private List<Computer> updateSerialNumberComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute updateComputerNumberComputerList */
	private List<Computer> updateComputerNumberComputerList = new ArrayList<Computer>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<HardwareComponent>(), new ArrayList<Software>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/**
	 * Method getCommandLine get the full computer status
	 * 
	 * @param serialNumber the serial number of the computer
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/status")
	public ResponseEntity<?> getCommandLine(@RequestHeader(required = true) String serialNumber)
	{
		try
		{
			if (serialNumber != null && isUsable(serialNumber))
			{
				Computer computer = chekIfSerialNumberExist(serialNumber);
				if (computer == null)
				{
					String error = "Compuer not found";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}
				return ResponseEntity.ok(computer);
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
	
	/**
	 * Getting the files , the computer send the serialNumber to identify
	 * @param serialNumber, the serial number of the computer
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/file")
	public ResponseEntity<?> getAnyFile(@RequestHeader(required = true) String serialNumber)
	{
		try
		{
			if (serialNumber != null && isUsable(serialNumber))
			{
				// Check if the serial number Exist
				Computer computer = chekIfSerialNumberExist(serialNumber);
				if (computer == null)
				{
					String error = "Compuer not found";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}
				return ResponseEntity.ok(computer);
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
	
	/**
	 * Getting the screenshot order , the compuer send the serialNumber to identify
	 * @param serialNumber, the serial number of the computer
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/screenshot")
	public ResponseEntity<?> getScreenshotOrder(@RequestHeader(required = true) String serialNumber)
	{
		try
		{
			if (serialNumber != null && isUsable(serialNumber))
			{
				// Check if the serial number Exist and return a computer
				Computer computer = chekIfSerialNumberExist(serialNumber);
				if (computer == null)
				{
					String error = "Compuer not found";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}
				return ResponseEntity.ok(computer);
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
	
	/**
	 * Getting the files , the computer send the serialNumber to identify
	 * @param serialNumber, the serial number of the computer
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/screenshot", consumes = "multipart/form-data")
	public ResponseEntity<?> sendScreenshot(@RequestBody(required = true) MultipartFile screenshot)
	{
		try
		{
			if (screenshot != null)
			{
				// Check if the serial number Exist
				return ResponseEntity.ok("OK");	
			}
			else
			{
				String error = "The parameter is null";
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
	/**
	 * this Method check if the serialNumber is blank or empty
	 * 
	 * @param serialNumber, the serial Number of the computer
	 * @return boolean
	 */
	private boolean isUsable(String serialNumber)
	{
		boolean usable = false;
		if (!serialNumber.isBlank() || !serialNumber.isEmpty())
		{
			usable = true;
		}
		return usable;
	}

	/**
	 * this method check if a computer with this serialNumber exist
	 * 
	 * @param serialNumber, the serial Number of the computer
	 * @return Computer
	 */
	private Computer chekIfSerialNumberExist(String serialNumber)
	{
		Computer computer = null;
		for (Computer x : this.computerList)
		{
			if (x.getSerialNumber().equals(serialNumber))
			{
				computer = x;
			}
		}
		return computer;
	}

	/**
	 * Method sendFullComputer that method is used for send periodically computer
	 * Instance
	 * 
	 * @param serialNumber     the serial number
	 * @param andaluciaId      the andalucia id
	 * @param computerNumber   the computer number
	 * @param computerInstance the computer object instance
	 * @return ResponseEntity response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/fullInfo", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> sendFullComputer(@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String andaluciaId, 
			@RequestHeader(required = false) String computerNumber,
			@RequestBody(required = true) Computer computerInstance)
	{
		try
		{
			// --- ONLY ONE PARAMETER BECAUSE WHY ONLY SEND THE STATUS OF ONE COMPUTER AT
			// THE SAME TIME (FOR SCHELUDED TASK ON CLIENT) ---
			if (serialNumber != null || andaluciaId != null || computerNumber != null)
			{
				if (serialNumber != null)
				{
					if (this.checkIsBlankEmpty(serialNumber))
					{
						// --- BY SERIAL NUMBER ---
						String error = "serialNumber is Empty or Blank";
						ComputerError computerError = new ComputerError(404, error, null);
						return ResponseEntity.status(404).body(computerError.toMap());
					}
					this.sendFullBySerialNumber(serialNumber, computerInstance);
				}
				else if (andaluciaId != null)
				{
					// --- BY ANDALUCIA ID ---
					if (this.checkIsBlankEmpty(andaluciaId))
					{
						String error = "andaluciaId is Empty or Blank";
						ComputerError computerError = new ComputerError(404, error, null);
						return ResponseEntity.status(404).body(computerError.toMap());
					}
					this.sendFullByAndaluciaId(andaluciaId, computerInstance);
				}
				else if (computerNumber != null)
				{
					// --- BY COMPUTER NUMBER ---
					if (this.checkIsBlankEmpty(computerNumber))
					{
						String error = "computerNumber is Empty or Blank";
						ComputerError computerError = new ComputerError(404, error, null);
						return ResponseEntity.status(404).body(computerError.toMap());
					}
					this.sendFullByComputerNumber(computerNumber, computerInstance);
				}

				// --- RESPONSE WITH OK , BUT TEMPORALY RESPONSE WITH LIST TO SEE THE CHANGES
				// ---
				return ResponseEntity.ok(this.computerList);

			}
			else
			{
				// --- ON THIS CASE ALL PARAMETERS ARE BLANK OR EMPTY ---
				String error = "All Paramaters Empty or Blank";
				ComputerError computerError = new ComputerError(404, error, null);
				return ResponseEntity.status(404).body(computerError.toMap());
			}
		}
		catch (Exception exception)
		{
			String error = "Server Error";
			ComputerError computerError = new ComputerError(404, error, exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
	}

	/**
	 * Method sendStatusComputer metod to check and send status
	 * 
	 * @param serialNumber the serialNumber
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/status", produces = "application/json")
	public ResponseEntity<?> sendStatusComputer(@RequestHeader(required = true) String serialNumber)
	{
		try
		{
			List<Status> statusList = new ArrayList<Status>();
			// --- SEARCHING THE COMPUTER ---
			if (serialNumber == null || serialNumber.isBlank() || serialNumber.isEmpty())
			{
				String error = "SerialNumber is Null, Empty or Blank";
				ComputerError computerError = new ComputerError(404, error, null);
				return ResponseEntity.status(404).body(computerError.toMap());
			}
			for (Computer computer : computerList)
			{
				// --- GETTING THE COMPUTER BY S/N ---
				if (computer.getSerialNumber().equalsIgnoreCase(serialNumber))
				{
					// --- SCANNING TASKS ---
					// --- SHUTDOWN ---
					for (int i = 0; i < shutDownComputerList.size(); i++)
					{
						Computer cmp = shutDownComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- SHUTDOWN LOGIC-------------------------------
							try
							{
								Runtime rt = Runtime.getRuntime();
								Process pr = rt.exec("cmd.exe /c shutdown -s -t 61");

								Status status = new Status("Shutdown computer " + serialNumber, true, null);
								statusList.add(status);
								shutDownComputerList.remove(cmp);
							}
							catch (Exception exception)
							{
								Status status = new Status("Shutdown computer "  + serialNumber, false,
										new ComputerError(001, "error on Shutdown computer ", null));
								statusList.add(status);
							}
						}
					}
					// --- RESTART ---
					for (int i = 0; i < restartDownComputerList.size(); i++)
					{
						Computer cmp = restartDownComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- RESTART LOGIC -------------------------------
							try
							{
								Runtime rt = Runtime.getRuntime();
								Process pr = rt.exec("cmd.exe /c shutdown -r -t 61");

								Status status = new Status("restart computer " + serialNumber, true, null);
								statusList.add(status);
								restartDownComputerList.remove(cmp);
							}
							catch (Exception exception)
							{
								Status status = new Status("Restart computer "  + serialNumber, false,
										new ComputerError(002, "error on restart computer ", null));
								statusList.add(status);
							}
							
						}
					}
					// --- COMMAND EXECUTE ---
					for (int i = 0; i < execCommandsComputerList.size(); i++)
					{
						Computer cmp = execCommandsComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- COMMAND EXECUTE LOGIC-------------------------------
							try
							{
								for (String command : cmp.getCommandLine().getCommands())
								{
									// --- GETTING COMMAND TO EXEC ---
									log.info("cmd.exe /c "+command);
									Runtime rt = Runtime.getRuntime();
									Process pr = rt.exec("cmd.exe /c "+command);
								}
								Status status = new Status("Execute Commands " + serialNumber, true,null);
								statusList.add(status);
								execCommandsComputerList.remove(cmp);
							}
							catch (Exception exception)
							{
								Status status = new Status("Execute Commands " + serialNumber, false,
										new ComputerError(111, "error on execute command", null));
								statusList.add(status);
							}
						}
					}
					// --- BLOCK DISPOSITIVE ---
					for (int i = 0; i < blockDispositiveComputerList.size(); i++)
					{
						Computer cmp = blockDispositiveComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO BLOCK DISP.LOGIC-------------------------------
							Status status = new Status("Block Dispositive " + serialNumber, true,null);
							statusList.add(status);
						}
					}
					// --- APERTURA REMOTA DE ENLACE WEB ---
					for (int i = 0; i < openWebComputerList.size(); i++)
					{
						// ------------------------- OPEN WEB -------------------------------
						Computer cmp = openWebComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							try
							{
								for (String command : cmp.getCommandLine().getCommands())
								{
									if(command.contains("start chrome")) 
									{
										log.info("cmd.exe /c "+command);
										Runtime rt = Runtime.getRuntime();
										Process pr = rt.exec("cmd.exe /c "+command);
									}
								}
								Status status = new Status("Execute Web Commands " + serialNumber, true,null);
								statusList.add(status);
								openWebComputerList.remove(cmp);
							}
							catch (Exception exception)
							{
								Status status = new Status("Execute Commands " + serialNumber, false,
										new ComputerError(333, "error on execute web command", null));
								statusList.add(status);
							}
						}
					}
					// --- INSTALACION REMOTA DE APLICACIONES ---
					for (int i = 0; i < installAppComputerList.size(); i++)
					{
						Computer cmp = installAppComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO Install AppLOGIC-------------------------------
							Status status = new Status("Install App " + serialNumber, true, null);
							statusList.add(status);
						}
					}
					// --- DESISNSTALACION REMOTA DE APP ---
					for (int i = 0; i < unistallAppComputerList.size(); i++)
					{
						Computer cmp = unistallAppComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO DESISNSTALACION REMOTA DE APP LOGIC------------
							Status status = new Status("Unistall App " + serialNumber,true, null);
							statusList.add(status);
						}
					}
					// --- EJECUCION DE CFG WIFI ---
					for (int i = 0; i < configurationFileComputerList.size(); i++)
					{
						Computer cmp = configurationFileComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO EJECUCION DE CFG WIFILOGIC--------------------
							Status status = new Status("CFG Configuration " + serialNumber, true, null);
							statusList.add(status);
						}
					}
					// --- ACTUALIZACION DE JUNTA ANDALUCIA ---
					for (int i = 0; i < updateAndaluciaComputerList.size(); i++)
					{
						Computer cmp = updateAndaluciaComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO ACTUALIZACION DE JUNTA ANDALUCIALOGIC---------
							Status status = new Status("Block Dispositive " + serialNumber,true, null);
							statusList.add(status);
						}
					}
					// --- ACTUALIZACION DE NUMERO DE SERIE ---
					for (int i = 0; i < updateSerialNumberComputerList.size(); i++)
					{
						Computer cmp = updateSerialNumberComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO ACTUALIZACION DE JUNTA ANDALUCIALOGIC---------
							Status status = new Status("Update Serial Number  " + serialNumber,true, null);
							statusList.add(status);
						}
					}
					// --- ACTUALIZACION DE NUM DE CAJA ---
					for (int i = 0; i < updateComputerNumberComputerList.size(); i++)
					{
						Computer cmp = updateComputerNumberComputerList.get(i);
						if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
						{
							// ------------------------- TO-DO ACTUALIZACION DE NUM DE CAJA ------------------------------
							Status status = new Status("Update Computer Number " + serialNumber, true, null);
							statusList.add(status);
						}
					}
				}
			}
			return ResponseEntity.ok().body(statusList);
		}
		catch (Exception exception)
		{
			String error = "Server Error";
			ComputerError computerError = new ComputerError(404, error, exception);
			return ResponseEntity.status(500).body(computerError.toMap());
		}
	}

	/**
	 * Method sendFullByComputerNumber
	 * 
	 * @param computerNumber
	 * @param computerInstance
	 */
	private void sendFullByComputerNumber(String computerNumber, Computer computerInstance)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer listedComputer = computerList.get(i);
			if (listedComputer.getComputerNumber().equalsIgnoreCase(computerNumber))
			{
				computerList.set(i, computerInstance);
			}
		}
	}

	/**
	 * Method sendFullByAndaluciaId
	 * 
	 * @param andaluciaId
	 * @param computerInstance
	 */
	private void sendFullByAndaluciaId(String andaluciaId, Computer computerInstance)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer listedComputer = computerList.get(i);
			if (listedComputer.getAndaluciaID().equalsIgnoreCase(andaluciaId))
			{
				computerList.set(i, computerInstance);
			}
		}
	}

	/**
	 * Method sendFullBySerialNumber
	 * 
	 * @param serialNumber
	 * @param computerInstance
	 */
	private void sendFullBySerialNumber(String serialNumber, Computer computerInstance)
	{
		for (int i = 0; i < computerList.size(); i++)
		{
			Computer listedComputer = computerList.get(i);
			if (listedComputer.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				computerList.set(i, computerInstance);
			}
		}
	}

	/**
	 * Method checkNullEmpty
	 * 
	 * @param serialNumber
	 * @return
	 */
	private boolean checkIsBlankEmpty(String strigParameter)
	{
		if (strigParameter.isBlank() || strigParameter.isEmpty())
		{
			return true;
		}
		return false;
	}

}
