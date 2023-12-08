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
import lombok.extern.slf4j.Slf4j;
import es.reaktor.exceptions.ComputerError;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;
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
	
	/**
	 * Method getCommandLine get the full computer status
	 * @param serialNumber the serial number of the computer
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/status")
	public ResponseEntity<?> getCommandLine(@RequestHeader(required=true) String serialNumber)
	{
		try
		{
			if(serialNumber != null && isUsable(serialNumber))
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
	 * this Method check if the serialNumber is blank or empty
	 * @param  serialNumber, the serial Number of the computer
	 * @return boolean 
	 */
	private boolean isUsable(String serialNumber)
	{
		boolean usable = false;
		if( !serialNumber.isBlank() || !serialNumber.isEmpty())
		{
			usable = true;
		}
		return usable;
	}
	
	/**
	 * this method check if a computer with this serialNumber exist 
	 * @param serialNumber, the serial Number of the computer
	 * @return Computer
	 */
	private Computer chekIfSerialNumberExist(String serialNumber)
	{
		Computer computer = null;	
		for(Computer x : this.computerList)
		{
			if(x.getSerialNumber().equals(serialNumber) )
			{
				computer = x;
			}			
		}
		return computer;	
	}
	

	/**
	 * Method sendFullComputer that method is used for send periodically computer Instance
	 * @param serialNumber the serial number
	 * @param andaluciaId the andalucia id
	 * @param computerNumber the computer number
	 * @param computerInstance the computer object instance
	 * @return ResponseEntity response
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/fullInfo")
	public ResponseEntity<?> sendFullComputer(
			@RequestHeader(required=false) String serialNumber,
			@RequestHeader(required=false) String andaluciaId,
			@RequestHeader(required=false) String computerNumber,
			@RequestBody(required=true) Computer computerInstance)
	{
		
		// --- ONLY ONE PARAMETER BECAUSE WHY ONLY SEND THE STATUS OF ONE COMPUTER AT THE SAME TIME (FOR SCHELUDED TASK ON CLIENT) ---
		if(serialNumber!= null || andaluciaId!=null || computerNumber!=null) 
		{
			if(serialNumber!=null) 
			{
				if(this.checkIsBlankEmpty(serialNumber)) 
				{
					// --- BY SERIAL NUMBER ---
					String error = "serialNumber is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}
				this.sendFullBySerialNumber(serialNumber, computerInstance);
			}
			else if(andaluciaId!=null) 
			{
				// --- BY ANDALUCIA ID ---
				if(this.checkIsBlankEmpty(andaluciaId)) 
				{
					String error = "andaluciaId is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}
				this.sendFullByAndaluciaId(andaluciaId, computerInstance);
			}
			else if(computerNumber!=null) 
			{
				// --- BY COMPUTER NUMBER ---
				if(this.checkIsBlankEmpty(computerNumber)) 
				{
					String error = "computerNumber is Empty or Blank";
					ComputerError computerError = new ComputerError(404, error, null);
					return ResponseEntity.status(404).body(computerError.toMap());
				}
				this.sendFullByComputerNumber(computerNumber, computerInstance);
			}
			
			// --- RESPONSE WITH OK , BUT TEMPORALY RESPONSE WITH LIST TO SEE THE CHANGES ---
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

	/**
	 * Method sendFullByComputerNumber
	 * @param computerNumber
	 * @param computerInstance
	 */
	private void sendFullByComputerNumber(String computerNumber, Computer computerInstance)
	{
		for(int i = 0 ; i<computerList.size();i++) 
		{
			Computer listedComputer = computerList.get(i);
			if(listedComputer.getComputerNumber().equalsIgnoreCase(computerNumber)) 
			{
				computerList.set(i, computerInstance);
			}
		}
	}

	/**
	 * Method sendFullByAndaluciaId
	 * @param andaluciaId
	 * @param computerInstance
	 */
	private void sendFullByAndaluciaId(String andaluciaId, Computer computerInstance)
	{
		for(int i = 0 ; i<computerList.size();i++) 
		{
			Computer listedComputer = computerList.get(i);
			if(listedComputer.getAndaluciaID().equalsIgnoreCase(andaluciaId)) 
			{
				computerList.set(i, computerInstance);
			}
		}
	}

	/**
	 * Method sendFullBySerialNumber
	 * @param serialNumber
	 * @param computerInstance
	 */
	private void sendFullBySerialNumber(String serialNumber, Computer computerInstance)
	{
		for(int i = 0 ; i<computerList.size();i++) 
		{
			Computer listedComputer = computerList.get(i);
			if(listedComputer.getSerialNumber().equalsIgnoreCase(serialNumber)) 
			{
				computerList.set(i, computerInstance);
			}
		}
	}

	/**
	 * Method checkNullEmpty
	 * @param serialNumber
	 * @return
	 */
	private boolean checkIsBlankEmpty(String strigParameter)
	{
		if(strigParameter.isBlank()||strigParameter.isEmpty()) 
		{
			return true;
		}
		return false;
	}
	
}

