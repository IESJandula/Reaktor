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
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.DTO.MalwareDTOWeb;
import es.reaktor.models.DTO.ReaktorDTO;
import es.reaktor.models.DTO.SimpleComputerDTO;
import es.reaktor.models.ComputerError;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.Malware;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Motherboard;
import es.reaktor.models.Reaktor;
import es.reaktor.models.Software;
import es.reaktor.reaktor.reaktor_actions.ReaktorActions;
import es.reaktor.reaktor.reaktor_actions.ReaktorService;
import es.reaktor.reaktor.repository.IMalwareRepository;
import es.reaktor.reaktor.repository.IMotherboardRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

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

	private Map<String, List<Action>> toDo = new HashMap<String, List<Action>>(Map.of("001",
			new ArrayList<Action>(List.of(new Action("shutdown", ""), new Action("reset", ""),
					new Action("uninstall", "chrome.exe"))),
			"002", new ArrayList<Action>(), "003",
			new ArrayList<Action>(List.of(new Action("uninstall", "chrome.exe")))));
	
	private List<Computer> computers = new ArrayList<Computer>(List.of(
			new Computer("001", "A001", "1", "Windows", "Vicente", new Location("0.5", 0, ""), null, null, null,
					null),
			new Computer("002", "A002", "2", "Linux", "Vicente", new Location("0.5", 0, "10"), null, null, null,
					null),
			new Computer("003", "A003", "3", "Windows", "Vicente", new Location("0.5", 0, ""), null, null, null,
					null),
			new Computer("004", "A004", "4", "MacOS", "Vicente", new Location("1.5", 1, "2"), null, null, null,
					null),
			new Computer("005", "A005", "5", "Windows", "Vicente", new Location("0.5", 0, ""), null, null, null,
					null),
			new Computer("006", "A006", "6", "Linux", "Vicente", new Location("2.5", 2, ""), null, null, null,
					null)));

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

	//Function to response with the activity of the client
	@RequestMapping(method = RequestMethod.GET, value = "/computers/get/status")
	public ResponseEntity<?> getCommandLine(@RequestHeader(required = true) String serialNumber)
	{

		try
		{
			Action response = new Action();
			checkSerialNumber(serialNumber);

			if (!toDo.get(serialNumber).isEmpty())
			{
				response = toDo.get(serialNumber).get(0);

				toDo.get(serialNumber).remove(0);
			}

			return ResponseEntity.ok(response);
		} catch (ComputerError computerError)
		{

			log.error("Computer error", computerError);
			return ResponseEntity.status(400).body(computerError.toMap());
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	//Function to set a computer to do thing on the toDo list
		private void setAccion (String serialNumber, String accion, String info) 
		{
			if(!toDo.containsKey(serialNumber))
			{
				toDo.put(serialNumber,new ArrayList<Action>());
			}
				toDo.get(serialNumber).add(new Action(accion,info));
		}
	
	@RequestMapping(method = RequestMethod.POST, value = "/computers/admin/restart")
	public ResponseEntity<?> postComputerRestart(@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) int plant)
	{
		try
		{
			if(serialNumber != null) 
			{
				setAccion(serialNumber,"restart","");
			}else if(classroom != null) 
			{
				for(Computer computer : this.computers) 
				{
					if(computer.getLocation().getClassroom().equals(classroom)) 
					{
						setAccion(computer.getSerialNumber(),"restart","");
					}
				}
			}else if(trolley != null) 
			{
				for(Computer computer : this.computers) 
				{
					if(computer.getLocation().getTrolley().equals(trolley)) 
					{
						setAccion(computer.getSerialNumber(),"restart","");
					}
				}
			}else if(plant != 0) 
			{
				for(Computer computer : this.computers) 
				{
					if(computer.getLocation().getPlant() == plant) 
					{
						setAccion(computer.getSerialNumber(),"restart","");
					}
				}
			}

			return ResponseEntity.ok().build();
		} catch (Exception e)
		{
			log.error("Server error", e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	private void checkSerialNumber(String serialNumber) throws ComputerError
	{

		if (serialNumber.isBlank() || !this.toDo.containsKey(serialNumber))
		{
			throw new ComputerError(2, "invalid SerialNumber", null);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/computers/admin/shutdown")
	public ResponseEntity<?> postComputerShutdown(@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) int plant)
	{
		try
		{
			if(serialNumber != null) 
			{
				setAccion(serialNumber,"shutdown","");
			}else if(classroom != null) 
			{
				for(Computer computer : this.computers) 
				{
					if(computer.getLocation().getClassroom().equals(classroom)) 
					{
						setAccion(computer.getSerialNumber(),"shutdown","");
					}
				}
			}else if(trolley != null) 
			{
				for(Computer computer : this.computers) 
				{
					if(computer.getLocation().getTrolley().equals(trolley)) 
					{
						setAccion(computer.getSerialNumber(),"shutdown","");
					}
				}
			}else if(plant != 0) 
			{
				for(Computer computer : this.computers) 
				{
					if(computer.getLocation().getPlant() == plant) 
					{
						setAccion(computer.getSerialNumber(),"shutdown","");
					}
				}
			}

			return ResponseEntity.ok().build();
		} catch (Exception e)
		{
			log.error("Server error", e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/computers/admin/file")
	public ResponseEntity<?> postComputerExecFile(@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String classroom, 
			@RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) int plant, 
			@RequestBody(required = true) File file)
	{
		try
		{

			return ResponseEntity.ok().build();
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/computers/get/file", produces = "multipart/form-data")
	public ResponseEntity<?> getAnyFile(@RequestHeader(required = false) String serialNumber)
	{
		try
		{

			return ResponseEntity.ok().build();
		} /*
			 * catch (ComputerError computerError){ return
			 * ResponseEntity.status(400).body(computerError); }
			 */catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/computers/get/screenshot")
	public ResponseEntity<?> getScreenshotOrder(@RequestHeader(required = false) String serialNumber)
	{
		try
		{

			return ResponseEntity.ok().build();
		} /*
			 * catch (ComputerError computerError){ return
			 * ResponseEntity.status(400).body(computerError); }
			 */catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/computers/send/screenshot")
	public ResponseEntity<?> sendScreenshot(@RequestHeader(required = false) String screenshot)
	{
		try
		{

			return ResponseEntity.ok().build();
		} /*
			 * catch (ComputerError computerError){ return
			 * ResponseEntity.status(400).body(computerError); }
			 */catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/computers/send/fullInfo")
	public ResponseEntity<?> sendFullComputer(@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String andaluciaId, @RequestHeader(required = false) String computerNumber)
	{
		try
		{

			return ResponseEntity.ok().build();
		} /*
			 * catch (ComputerError computerError){ return
			 * ResponseEntity.status(400).body(computerError); }
			 */catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/computers/send/status", consumes = "application/json")
	public ResponseEntity<?> sendFullComputer()
	{
		try
		{

			return ResponseEntity.ok().build();
		} /*
			 * catch (ComputerError computerError){ return
			 * ResponseEntity.status(400).body(computerError); }
			 */catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/computers/web", consumes = "application/json")
	public ResponseEntity<?> getComputersByAny(@RequestHeader(required = false) String serialNumber,
			@RequestHeader(required = false) String andaluciaId, @RequestHeader(required = false) String computerNumber,
			@RequestHeader(required = false) String classroom, @RequestHeader(required = false) String trolley,
			@RequestHeader(required = false) String plant, @RequestHeader(required = false) String professor)
	{
		try
		{
			
			List<Computer> response = new ArrayList<Computer>();
			if (serialNumber != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						response.add(computer);
					}
				}
			} else if (andaluciaId != null)
			{
				for(Computer computer : computers) {
					if (computer.getAndaluciaId().equals(andaluciaId))
					{
						response.add(computer);
					}
				}
			} else if (computerNumber != null)
			{
				for(Computer computer : computers) {
					if (computer.getComputerNumber().equals(computerNumber))
					{
						response.add(computer);
					}
				}
			} else if (classroom != null)
			{
				for(Computer computer : computers) {
					if (computer.getLocation().getClassroom().equals(classroom))
					{
						response.add(computer);
					}
				}
			} else if (trolley != null)
			{
				for(Computer computer : computers) {
					if (computer.getLocation().getTrolley().equals(trolley))
					{
						response.add(computer);
					}
				}
			} else if (plant != null)
			{
				for(Computer computer : computers) {
					if (computer.getLocation().getPlant() == Integer.valueOf(plant))
					{
						response.add(computer);
					}
				}
			} else if (professor != null)
			{
				for(Computer computer : computers) {
					if (computer.getProfessor().equals(professor))
					{
						response.add(computer);
					}
				}
			}

			return ResponseEntity.ok().body(response);
		}catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/computer/edit", consumes = "application/json")
	public ResponseEntity<?> updateComputer(@RequestHeader(required = true) String serialNumber,
			@RequestHeader(required = false) String andaluciaId, @RequestHeader(required = false) String computerNumber,
			@RequestHeader(required = false) String operativeSystem, @RequestHeader(required = false) String professor,
			@RequestHeader(required = false) Location location, @RequestHeader(required = false) List<HardwareComponent> hardwareList,
			@RequestHeader(required = false) List<Software> softwareList, @RequestHeader(required = false) CommandLine commandLine,
			@RequestHeader(required = false) MonitorizationLog monitorizationLog)
	{
		try
		{

			List<Computer> response = new ArrayList<Computer>();
			
			if (andaluciaId != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setAndaluciaId(andaluciaId);
					}
				}
			} if (computerNumber != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setComputerNumber(computerNumber);
					}
				}
			} if (operativeSystem != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setOperativeSystem(operativeSystem);
					}
				}
			} if (professor != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setProfessor(professor);
					}
				}
			} if (location != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setLocation(location);
					}
				}
			} if (hardwareList != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setHardwareList(hardwareList);
					}
				}
			} if (softwareList != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setSoftwareList(softwareList);
					}
				}
			} if (commandLine != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setCommandLine(commandLine);
					}
				}
			} if (monitorizationLog != null)
			{
				for(Computer computer : computers) {
					if (computer.getSerialNumber().equals(serialNumber))
					{
						computer.setMonitorizationLog(monitorizationLog);
					}
				}
			}
			return ResponseEntity.ok().body(response);
		}catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

}
