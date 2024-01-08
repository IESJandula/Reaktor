package es.reaktor.reaktor.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.constants.Constantes;
import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.utils.IChecker;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alejandro Cazalla
 *
 */
@RequestMapping(value = "/computers")
@RestController
public class RestHandlerWeb implements IChecker
{
	/** Class logger */
	private static Logger log = LogManager.getLogger();
	/**Lista de ordenadores para casos de prueba */
	private List<Computer> computers;
	
	/**
	 * Default constructor
	 */
	public RestHandlerWeb()
	{
		this.computers = Constantes.cargarOrdenadores();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/web/filter",consumes = "application/json")
	public ResponseEntity<?> postComputerCommandLine(
			@RequestHeader(value = "serialNumber", required = false) String serialNumber,
			@RequestHeader(value = "juntaNumber", required = false) String juntaNumber,
			@RequestHeader(value = "computerNumber", required = false) String computerNumber,
			@RequestHeader(value = "cpuNumber", required = false) Integer cpuNumber,
			@RequestHeader(value = "ramCap", required = false) Integer ramCap,
			@RequestHeader(value = "hdCap", required = false) Integer hdCap,
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "plant", required = false) Integer plant,
			@RequestHeader(value = "teacher", required = false) String teacher)
	{
		try
		{
			serialNumber = serialNumber==null ? "" : serialNumber;
			juntaNumber = juntaNumber==null ? "" : juntaNumber;
			computerNumber = computerNumber==null ? "" : computerNumber;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			teacher = teacher==null ? "" : teacher;
			this.checkParams(serialNumber, juntaNumber, computerNumber, cpuNumber, ramCap, hdCap, classroom, trolley, plant, teacher, computers);
		} 
		catch (ComputerError ex)
		{
			log.error("Administration error", ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		} 
		catch (Exception ex)
		{
			log.error("Internal server error", ex);
			return ResponseEntity.status(500).body("Server error");
		}
		return ResponseEntity.ok().build();
	}
	
	
}
