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
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Peripheral;
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
	private List<Computer> computerList = new ArrayList<>(List.of(
			new Computer("sn123", "and123", "cn123", "windows", "paco", new Location("0.5", 0, "trolley1"),
					new ArrayList<>(), new ArrayList<>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn1234", "and1234", "cn12344", "windows", "paco", new Location("0.5", 0, "trolley1"),
					new ArrayList<>(), new ArrayList<>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn123", "and12355", "cn123455", "windows", "paco", new Location("0.7", 0, "trolley2"),
					new ArrayList<>(), new ArrayList<>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn123556", "and123556", "cn1234556", "windows", "paco", new Location("0.7", 0, "trolley2"),
					new ArrayList<>(), new ArrayList<>(), new CommandLine(),
					new MonitorizationLog()),
			new Computer("sn123777", "and123777", "cn1234777", "windows", "paco", new Location("0.9", 0, "trolley3"),
					new ArrayList<>(), new ArrayList<>(), new CommandLine(),
					new MonitorizationLog())

	));
	/** Attribute shutDownComputerList */
	private List<Computer> shutDownComputerList = new ArrayList<>(List.of(new Computer("sn123556", "and123556",
			"cn1234556", "windows", "paco", new Location("0.7", 0, "trolley2"), new ArrayList<>(),
			new ArrayList<>(), new CommandLine(), new MonitorizationLog())));
	/** Attribute restartDownComputerList */
	private List<Computer> restartDownComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(), new MonitorizationLog())));

	/** Attribute execCommandsComputerList */
	private List<Computer> execCommandsComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("mkdir .\\carpeta1David")), new MonitorizationLog())));

	/** Attribute blockDispositiveComputerList */
	private List<Computer> blockDispositiveComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(List.of(new Peripheral("Raton",1,true))), new ArrayList<>(),
					new CommandLine(), new MonitorizationLog())));

	/** Attribute openWebComputerList */
	private List<Computer> openWebComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("start chrome www.iesjandula.es")), new MonitorizationLog())));

	/** Attribute installAppComputerList */
	private List<Computer> installAppComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute unistallAppComputerList */
	private List<Computer> unistallAppComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute configurationFileComputerList */
	private List<Computer> configurationFileComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute updateAndaluciaComputerList */
	private List<Computer> updateAndaluciaComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute updateSerialNumberComputerList */
	private List<Computer> updateSerialNumberComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
					new CommandLine(List.of("start chrome")), new MonitorizationLog())));

	/** Attribute updateComputerNumberComputerList */
	private List<Computer> updateComputerNumberComputerList = new ArrayList<>(
			List.of(new Computer("sn123556", "and123556", "cn1234556", "windows", "paco",
					new Location("0.7", 0, "trolley2"), new ArrayList<>(), new ArrayList<>(),
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
			if ((serialNumber != null) && this.isUsable(serialNumber))
			{
				Computer computer = this.chekIfSerialNumberExist(serialNumber);
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
			if ((serialNumber != null) && this.isUsable(serialNumber))
			{
				// Check if the serial number Exist
				Computer computer = this.chekIfSerialNumberExist(serialNumber);
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
			if ((serialNumber != null) && this.isUsable(serialNumber))
			{
				// Check if the serial number Exist and return a computer
				Computer computer = this.chekIfSerialNumberExist(serialNumber);
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
			if ((serialNumber != null) || (andaluciaId != null) || (computerNumber != null))
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
			List<Status> statusList = new ArrayList<>();
			// --- SEARCHING THE COMPUTER ---
			if ((serialNumber == null) || serialNumber.isBlank() || serialNumber.isEmpty())
			{
				String error = "SerialNumber is Null, Empty or Blank";
				ComputerError computerError = new ComputerError(404, error, null);
				return ResponseEntity.status(404).body(computerError.toMap());
			}
			for (Computer computer : this.computerList)
			{
				// --- GETTING THE COMPUTER BY S/N ---
				if (computer.getSerialNumber().equalsIgnoreCase(serialNumber))
				{
					// --- SCANNING TASKS ---
					// --- SHUTDOWN ---
					this.sendStatusComputerShutdown(serialNumber, statusList);
					// --- RESTART ---
					this.sendStatusComputerRestart(serialNumber, statusList);
					// --- COMMAND EXECUTE ---
					this.sendStatusComputerCommandExecute(serialNumber, statusList);
					// --- BLOCK DISPOSITIVE ---
					this.sendStatusComputerBlockDisp(serialNumber, statusList, computer);
					// --- APERTURA REMOTA DE ENLACE WEB ---
					this.sendStatusComputerOpenWeb(serialNumber, statusList);
					// --- INSTALACION REMOTA DE APLICACIONES ---
					this.sendStatusComputerInstallApp(serialNumber, statusList);
					// --- DESISNSTALACION REMOTA DE APP ---
					this.sendStatusComputerUnistallApp(serialNumber, statusList);
					// --- EJECUCION DE CFG WIFI ---
					this.sendStatusComputerCfgWifi(serialNumber, statusList);
					// --- ACTUALIZACION DE JUNTA ANDALUCIA ---
					this.sendStatusComputerUpdateAndalucia(serialNumber, statusList, computer);
					// --- ACTUALIZACION DE NUMERO DE SERIE ---
					this.sendStatusComputerUpdateSn(serialNumber, statusList, computer);
					// --- ACTUALIZACION DE NUM DE CAJA ---
					this.sendStatusComputerUpdateCn(serialNumber, statusList, computer);
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
	 * Method sendStatusComputerUpdateCn
	 * @param serialNumber
	 * @param statusList
	 * @param computer
	 */
	private void sendStatusComputerUpdateCn(String serialNumber, List<Status> statusList, Computer computer)
	{
		for (int i = 0; i < this.updateComputerNumberComputerList.size(); i++)
		{
			Computer cmp = this.updateComputerNumberComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// -------------------------  ACTUALIZACION DE NUM DE CAJA LOGIC---------
				try
				{
					//  --- UPDATE FULL LIST WITH THE CMP FROM UPDATE TASKS  ---
					this.computerList.remove(computer);
					this.computerList.add(cmp);

					Status status = new Status("Update computer number " + serialNumber, true,null);
					statusList.add(status);
					this.updateComputerNumberComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Update Andalucia Id " + serialNumber, false,
							new ComputerError(121, "error on Update computer number", null));
					statusList.add(status);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerUpdateSn
	 * @param serialNumber
	 * @param statusList
	 * @param computer
	 */
	private void sendStatusComputerUpdateSn(String serialNumber, List<Status> statusList, Computer computer)
	{
		for (int i = 0; i < this.updateSerialNumberComputerList.size(); i++)
		{
			Computer cmp = this.updateSerialNumberComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- ACTUALIZACION DE NUMERO DE SERIE LOGIC---------
				try
				{
					//  --- UPDATE FULL LIST WITH THE CMP FROM UPDATE TASKS  ---
					this.computerList.remove(computer);
					this.computerList.add(cmp);

					Status status = new Status("Update serial number " + serialNumber, true,null);
					statusList.add(status);
					this.updateSerialNumberComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Update serial number " + serialNumber, false,
							new ComputerError(121, "error on Update serial number", null));
					statusList.add(status);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerUpdateAndalucia
	 * @param serialNumber
	 * @param statusList
	 * @param computer
	 */
	private void sendStatusComputerUpdateAndalucia(String serialNumber, List<Status> statusList, Computer computer)
	{
		for (int i = 0; i < this.updateAndaluciaComputerList.size(); i++)
		{
			Computer cmp = this.updateAndaluciaComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- ACTUALIZACION DE JUNTA ANDALUCIA LOGIC---------
				try
				{
					//  --- UPDATE FULL LIST WITH THE CMP FROM UPDATE TASKS  ---
					this.computerList.remove(computer);
					this.computerList.add(cmp);

					Status status = new Status("Update Andalucia Id " + serialNumber, true,null);
					statusList.add(status);
					this.updateAndaluciaComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Update Andalucia Id " + serialNumber, false,
							new ComputerError(121, "error on Update Andalucia Id", null));
					statusList.add(status);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerCfgWifi
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerCfgWifi(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.configurationFileComputerList.size(); i++)
		{
			Computer cmp = this.configurationFileComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- TO-DO EJECUCION DE CFG WIFIL OGIC--------------------
				Status status = new Status("CFG Configuration " + serialNumber, true, null);
				statusList.add(status);
			}
		}
	}

	/**
	 * Method sendStatusComputerUnistallApp
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerUnistallApp(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.unistallAppComputerList.size(); i++)
		{
			Computer cmp = this.unistallAppComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- TO-DO DESISNSTALACION REMOTA DE APP LOGIC------------
				Status status = new Status("Unistall App " + serialNumber,true, null);
				statusList.add(status);
			}
		}
	}

	/**
	 * Method sendStatusComputerInstallApp
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerInstallApp(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.installAppComputerList.size(); i++)
		{
			Computer cmp = this.installAppComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- TO-DO Install AppLOGIC-------------------------------
				Status status = new Status("Install App " + serialNumber, true, null);
				statusList.add(status);
			}
		}
	}

	/**
	 * Method sendStatusComputerOpenWeb
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerOpenWeb(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.openWebComputerList.size(); i++)
		{
			// ------------------------- OPEN WEB -------------------------------
			Computer cmp = this.openWebComputerList.get(i);
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
					this.openWebComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Execute Commands " + serialNumber, false,
							new ComputerError(333, "error on execute web command", null));
					statusList.add(status);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerBlockDisp
	 * @param serialNumber
	 * @param statusList
	 * @param computer
	 */
	private void sendStatusComputerBlockDisp(String serialNumber, List<Status> statusList, Computer computer)
	{
		for (int i = 0; i < this.blockDispositiveComputerList.size(); i++)
		{
			Computer cmp = this.blockDispositiveComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- BLOCK DISP.LOGIC-------------------------------
				try
				{
					// --- GETTING ON FULL LIST ---

					Peripheral peripheral = (Peripheral) cmp.getHardwareList().get(0);
					peripheral.setOpen(false);
					cmp.getHardwareList().set(0, peripheral);

					//  --- UPDATE FULL LIST ---
					this.computerList.remove(computer);
					this.computerList.add(cmp);

					Status status = new Status("Dispotisive blocked " + serialNumber, true,null);
					statusList.add(status);
					this.blockDispositiveComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Dispotisive blocked error " + serialNumber, false,
							new ComputerError(121, "error on block", null));
					statusList.add(status);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerCommandExecute
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerCommandExecute(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.execCommandsComputerList.size(); i++)
		{
			Computer cmp = this.execCommandsComputerList.get(i);
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
					this.execCommandsComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Execute Commands " + serialNumber, false,
							new ComputerError(111, "error on execute command", null));
					statusList.add(status);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerRestart
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerRestart(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.restartDownComputerList.size(); i++)
		{
			Computer cmp = this.restartDownComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- RESTART LOGIC -------------------------------
				try
				{
					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec("cmd.exe /c shutdown -r -t 61");

					Status status = new Status("restart computer " + serialNumber, true, null);
					statusList.add(status);
					this.restartDownComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Restart computer "  + serialNumber, false,
							new ComputerError(002, "error on restart computer ", null));
					statusList.add(status);
				}

			}
		}
	}

	/**
	 * Method sendStatusComputerShutdown
	 * @param serialNumber
	 * @param statusList
	 */
	private void sendStatusComputerShutdown(String serialNumber, List<Status> statusList)
	{
		for (int i = 0; i < this.shutDownComputerList.size(); i++)
		{
			Computer cmp = this.shutDownComputerList.get(i);
			if (cmp.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				// ------------------------- SHUTDOWN LOGIC-------------------------------
				try
				{
					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec("cmd.exe /c shutdown -s -t 61");

					Status status = new Status("Shutdown computer " + serialNumber, true, null);
					statusList.add(status);
					this.shutDownComputerList.remove(cmp);
				}
				catch (Exception exception)
				{
					Status status = new Status("Shutdown computer "  + serialNumber, false,
							new ComputerError(001, "error on Shutdown computer ", null));
					statusList.add(status);
				}
			}
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
		for (int i = 0; i < this.computerList.size(); i++)
		{
			Computer listedComputer = this.computerList.get(i);
			if (listedComputer.getComputerNumber().equalsIgnoreCase(computerNumber))
			{
				this.computerList.set(i, computerInstance);
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
		for (int i = 0; i < this.computerList.size(); i++)
		{
			Computer listedComputer = this.computerList.get(i);
			if (listedComputer.getAndaluciaID().equalsIgnoreCase(andaluciaId))
			{
				this.computerList.set(i, computerInstance);
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
		for (int i = 0; i < this.computerList.size(); i++)
		{
			Computer listedComputer = this.computerList.get(i);
			if (listedComputer.getSerialNumber().equalsIgnoreCase(serialNumber))
			{
				this.computerList.set(i, computerInstance);
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
