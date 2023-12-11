package es.reaktor.reaktor.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.models.Action;
import es.reaktor.models.ComputerError;
import es.reaktor.models.Malware;
import es.reaktor.models.Motherboard;
import es.reaktor.models.Reaktor;
import es.reaktor.models.DTO.MalwareDTOWeb;
import es.reaktor.models.DTO.ReaktorDTO;
import es.reaktor.models.DTO.SimpleComputerDTO;
import es.reaktor.reaktor.reaktor_actions.ReaktorActions;
import es.reaktor.reaktor.reaktor_actions.ReaktorService;
import es.reaktor.reaktor.repository.IMalwareRepository;
import es.reaktor.reaktor.repository.IMotherboardRepository;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ReaktorServerRest
{
	@Autowired
	private ReaktorActions reaktorActions;

	@Autowired
	private IMotherboardRepository iMotherboardRepository;

	@Autowired
	private IMalwareRepository iMalwareRepository;

	@Autowired
	private ReaktorService reaktorService;
	
	private Map<String, List<Action>> toDo = new HashMap<String, List<Action>>(Map.of(
   			"001", new ArrayList<Action>( List.of(new Action("shutdown",""),new Action("reset", ""),new Action("uninstall", "chrome.exe"))),
   			"002", new ArrayList<Action>(),
			"003", new ArrayList<Action>(List.of(new Action("uninstall", "chrome.exe")))));

	@RequestMapping(method = RequestMethod.POST, value = "/reaktor")
	public ResponseEntity<?> sendInformation(@RequestBody Reaktor reaktor)
	{
		log.info("Receiving information from reaktor {}", reaktor);
		this.reaktorActions.saveReaktor(reaktor);
		return ResponseEntity.ok("Reaktor Server is running");
	}

	/**
	 * This Method is used to obtain the malware list
	 * 
	 * @return the malware list
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/computer-on")
	public ResponseEntity<?> computerOn(@RequestHeader String motherBoardSerialNumber)
	{
		// Comprobamos que existe la placa base en la base de datos
		if (!this.iMotherboardRepository.existsById(motherBoardSerialNumber))
		{
			return ResponseEntity.ok("Motherboard with serial number " + motherBoardSerialNumber + " does not exist");
		}

		// we get the motherboard
		Motherboard motherboard = this.iMotherboardRepository.findByMotherBoardSerialNumber(motherBoardSerialNumber);

		// we set the computer on
		motherboard.setComputerOn(true);

		// we set the last update computer on
		motherboard.setLastUpdateComputerOn(new Date());

		// we save the motherboard with the new status
		this.iMotherboardRepository.save(motherboard);

		return ResponseEntity.ok("Computer with serial number " + motherBoardSerialNumber + " is on");
	}

	/**
	 * This Method is used to obtain the malware list
	 * 
	 * @return the malware list
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/malware")
	public List<Malware> getMalware()
	{
		return this.iMalwareRepository.findAll();
	}

	/**
	 * This Method is used to obtain the malware list for Web
	 * 
	 * @return the malware list for Web
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/malware-web")
	public List<MalwareDTOWeb> getMalwareWeb()
	{
		return this.reaktorService.getMalwareWeb();
	}

	/**
	 * This Method is used to create a malware
	 * 
	 * @return Ok if the malware is created
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/malware")
	public ResponseEntity<?> createMalware(@RequestBody Malware newMalware)
	{
		log.info("Creating malware {}", newMalware);
		return ResponseEntity.ok().body(this.iMalwareRepository.save(newMalware));
	}

	/**
	 * This Method is used to create a malware
	 * 
	 * @param name malware name
	 * @return Ok if the malware is created
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/malware/{name}")
	public ResponseEntity<?> deleteMalware(@PathVariable String name)
	{
		this.reaktorService.deleteMalware(name);
		return ResponseEntity.ok("Malware has been deleted");
	}

	/**
	 * This Method is used to report a malware for a motherboard
	 * 
	 * @return Ok if the malware is reported
	 */
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "/report-malware")
	public ResponseEntity<?> reportMalware(@RequestHeader String motherBoardSerialNumber,
			@RequestBody List<Malware> malwareList)
	{
		try
		{
			log.info("Reporting malware for motherboard {}", motherBoardSerialNumber);
			this.reaktorActions.insertMalwareMotherboard(motherBoardSerialNumber, malwareList);
		} catch (Exception exception)
		{
			log.warn("Malware not reported", exception);
			return ResponseEntity.ok("Malware not reported");
		}

		return ResponseEntity.ok("Malware reported");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/computer")
	public ResponseEntity<List<SimpleComputerDTO>> getComputer()
	{
		return ResponseEntity.ok(reaktorService.getSimpleComputerDTO());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reaktor/{idComputer}")
	public ResponseEntity<ReaktorDTO> getReaktor(@PathVariable String idComputer)
	{
		return ResponseEntity.ok(reaktorService.getInformationReaktor(idComputer));
	}
   @RequestMapping(method = RequestMethod.GET, value = "/computers/get/status")
   public ResponseEntity<?> getCommandLine(
    		@RequestHeader(required = true) String serialNumber)
    {
	   	
    	try {
    		Action response = new Action();
    		checkSerialNumber(serialNumber);
    		
    		if(!toDo.get(serialNumber).isEmpty()) {
    			response = toDo.get(serialNumber).get(0);
    			
    			toDo.get(serialNumber).remove(0);
    		}
			
			return ResponseEntity.ok(response);
    	}catch (ComputerError computerError){
    		log.error("Computer error", computerError);
    		return ResponseEntity.status(400).body(computerError.toMap());
    	}catch (Exception e) {
    		log.error("Server error", e);
    		return ResponseEntity.status(500).body(e.getMessage());
		}
   }
   
   
   private void checkSerialNumber(String serialNumber) throws ComputerError {
	   
	   if (serialNumber.isBlank() || !this.toDo.containsKey(serialNumber))
	   {
		   throw new ComputerError(2, "invalid SerialNumber", null);
	   }
   }
}
