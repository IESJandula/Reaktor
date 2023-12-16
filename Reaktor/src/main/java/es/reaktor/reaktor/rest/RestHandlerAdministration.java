package es.reaktor.reaktor.rest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.Peripheral;
import es.reaktor.reaktor.models.Software;
import es.reaktor.reaktor.models.Status;
import es.reaktor.reaktor.utils.IChecker;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alejandro Cazalla
 *
 */
@RequestMapping(value = "/computers")
@RestController
public class RestHandlerAdministration implements IChecker
{
	/** Class logger */
	private static Logger log = LogManager.getLogger();
	/**Mapa de status para identificar procesos a ejecutar */
	private Map<String,Status> pcStatus;
	/**
	 * Default constructor
	 */
	public RestHandlerAdministration()
	{
		// Public constructor
		this.pcStatus = new HashMap<String,Status>();
	}

	/**
	 * Endpoint que manda comandos cmd para ejecutarlos en un respectivo ordenador 
	 * @param serialNumber numero de serie del ordenador
	 * @param classroom clase a la que pertenece el ordenador
	 * @param trolley
	 * @param plant
	 * @param commandLine
	 * @return OK si la configuracion es correcta, 404 si es incorrecta o 500 si hubo un error de servidor
	 * @throws ComputerError
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/commandLine")
	public ResponseEntity<?> postComputerCommandLine(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant,
			@RequestHeader(value = "commandLineInstance", required = false) final CommandLine commandLine)
	{
		try
		{
			//Objeto status para mandarlo a ejecutar en cliente
			Status status = null;
			//Comprobamos que los atributos no esten vacios
			this.checkParams(serialNumber, classroom, trolley, plant, commandLine);
			if(!serialNumber.isEmpty())
			{
				status = new Status(String.valueOf(commandLine.getCommands()),false);
				//Añadimos como clave el pc y como valor un objeto status con command line
				this.pcStatus.put(serialNumber,status);
			}
			if(!classroom.isEmpty())
			{
				status = new Status(String.valueOf(commandLine.getCommands()),false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con command line
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status(String.valueOf(commandLine.getCommands()),false);
				//Añadimos como clave el pc y como valor un objeto status con command line
				this.pcStatus.put(trolley,status);
			}
			if(plant != null)
			{
				status = new Status(String.valueOf(commandLine.getCommands()),false);
				//Añadimos como clave el pc y como valor un objeto status con command line
				this.pcStatus.put(String.valueOf(plant),status);
			}
			return ResponseEntity.ok().body("Comandos mandados a ejecutar");
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
	}

	/**
	 * Metodo que manda una peticion a un pc para apagarse
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/shutdown", consumes = "application/json")
	public ResponseEntity<?> putComputerShutdown(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant)
	{
		try
		{
			Status status = null;
			//Se comprueba que los parametros no esten vacios
			this.checkParams(serialNumber, classroom, trolley, plant);
			if(!serialNumber.isEmpty())
			{
				status = new Status("apagado",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "apagado"
				this.pcStatus.put(serialNumber,status);
			}
			if(!classroom.isEmpty())
			{
				status = new Status("apagado",false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con el estado "apagado"
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status("apagado",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "apagado"
				this.pcStatus.put(trolley,status);
			}
			if(plant != null)
			{
				status = new Status("apagado",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "apagado"
				this.pcStatus.put(String.valueOf(plant),status);
			}
			return ResponseEntity.ok().body("OK");
		} 
		catch (ComputerError exception)
		{
			String message = "Administration error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		} 
		catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 102A*******************************************************************/
	/************************************************************************************************************************************************/
	/**
	 * Metodo que manda una petición a pc para reiniciarse
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerRestart(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant)
	{
		try
		{
			Status status = null;
			this.checkParamsPutComputerRestart(serialNumber, classroom, trolley, plant);
			if(!serialNumber.isEmpty())
			{
				status = new Status("reiniciado",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "reiniciado"
				this.pcStatus.put(serialNumber,status);
			}
			if(!classroom.isEmpty())
			{
				status = new Status("reiniciado",false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con el estado "reiniciado"
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status("reiniciado",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "reiniciado"
				this.pcStatus.put(trolley,status);
			}
			if(plant != null)
			{
				status = new Status("reiniciado",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "reiniciado"
				this.pcStatus.put(String.valueOf(plant),status);
			}
			return ResponseEntity.ok().body("OK");
		} catch (ComputerError exception)
		{
			String message = "Administration error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		} catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 103A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/peripheral", consumes = { "application/json" })
	public ResponseEntity<?> postPeripheral(@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestBody(required = true) Peripheral[] peripherals)
	{
		try
		{
			Status status = null;
			this.checkParamsAddByBody(classroom, trolley, peripherals);
			if(!classroom.isEmpty())
			{
				status = new Status("perifericos",false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con el estado "perifericos"
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status("perifericos",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "perifericos"
				this.pcStatus.put(trolley,status);
			}
			if(peripherals != null)
			{
				status = new Status("perifericos",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "perifericos"
				this.pcStatus.put(String.valueOf(peripherals),status);
			}
			return ResponseEntity.ok().body("All OK");
		} catch (ComputerError ce)
		{
			return ResponseEntity.status(400).body(ce.getBodyMessageException());
		} catch (Exception e)
		{
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	/************************************************************************************************************************************************/
	/***************************************************************REAK 104A************************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/screenshot")
	public ResponseEntity<?> sendScreenshotOrder(@RequestHeader(value = "classroom") final String classroom,
			@RequestHeader(value = "trolley") final String trolley)
	{
		try
		{
			Status status = null;
			this.checkParamsSendScreenshotOrder(classroom, trolley);
			if(!classroom.isEmpty())
			{
				status = new Status("capturas",false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con el estado "capturas"
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status("capturas",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "capturas"
				this.pcStatus.put(trolley,status);
			}
			return ResponseEntity.ok().body("Screenshot send successfully");
		} catch (ComputerError ex)
		{
			log.error("Administration error", ex);
			return ResponseEntity.status(404).body(ex);
		} catch (Exception ex)
		{
			log.error("Server error", ex);
			return ResponseEntity.status(500).body(new ComputerError(500, "Server error"));
		}
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 105A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.GET, value = "/admin/screenshot", produces = "application/zip")
	public ResponseEntity<?> getScreenshots(
			@RequestHeader(value = "classroom") final String classroom,
			@RequestHeader(value = "trolley") final String trolley
			)
	{
		try
		{
			Status status = null;
			this.checkParamsGetScreenshots(classroom, trolley);
			if(!classroom.isEmpty())
			{
				status = new Status("capturas",false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con el estado "capturas"
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status("capturas",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "capturas"
				this.pcStatus.put(trolley,status);
			}
			File zipFile = new File("screenshots.zip");
			//TODO: Cuando se implante la bbdd llamar al ordenador ye incluirlo en softaware
			return ResponseEntity.ok().body(zipFile);
		}
		catch(ComputerError ex)
		{
			log.error("Computer update failed",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(500).body("Server error");
		}
	}
	
	/************************************************************************************************************************************************/
	/********************************************************************REAK 106A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/file")
	public ResponseEntity<?> postComputerExeFile(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant,
			@RequestHeader(value = "execFile", required = false) final File execFile)
	{
		try
		{
			Status status = null;
			this.checkParamsPostComputerExeFile(serialNumber, classroom, trolley, plant, execFile);
			if(!serialNumber.isEmpty())
			{
				status = new Status("ejecutable",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "ejecutable"
				this.pcStatus.put(serialNumber,status);
			}
			if(!classroom.isEmpty())
			{
				status = new Status("ejecutable",false);
				//Añadimos como clave una clase en la que hay ordenadores y como valor un objeto status con el estado "ejecutable"
				this.pcStatus.put(classroom,status);
			}
			if(!trolley.isEmpty())
			{
				status = new Status("ejecutable",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "ejecutable"
				this.pcStatus.put(trolley,status);
			}
			if(plant != null)
			{
				status = new Status("ejecutable",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "ejecutable"
				this.pcStatus.put(String.valueOf(plant),status);
			}
			if(execFile != null)
			{
				status = new Status("ejecutable",false);
				//Añadimos como clave el pc y como valor un objeto status con el estado "ejecutable"
				this.pcStatus.put(String.valueOf(execFile),status);
			}
			return ResponseEntity.ok().body("Computer update success");
		} catch (ComputerError ex)
		{
			log.error("Computer update failed", ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		} catch (Exception ex)
		{
			log.error("Server error", ex);
			return ResponseEntity.status(500).body("Server error");
		}
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 107A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> sendSoftware(@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "professor", required = false) final String professor,
			@RequestBody(required = true) final Software[] softwareInstance)
	{
		try
		{
			this.checkParamsSendSoftware(classroom, trolley, professor, softwareInstance);
			return ResponseEntity.ok().body("OK");
		} catch (ComputerError exception)
		{
			String message = "Software error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		} catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 108A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> uninstallSoftware(
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "professor", required = false) final String professor,
			@RequestBody(required = true) final Software[] softwareInstance)
	{
		try
		{
			this.checkParamsUninstallSoftware(classroom, trolley, professor, softwareInstance);
			return ResponseEntity.ok().body("OK");
		} catch (ComputerError exception)
		{
			String message = "Software error";
			log.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyMessageException());
		} catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 109A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.PUT, value = "/edit")
	public ResponseEntity<?> updateComputer(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "andaluciaId", required = false) final String andaluciaId,
			@RequestHeader(value = "computerNumber", required = false) final String computerNumber,
			@RequestHeader(value = "computerInstance", required = true) final Computer computer)
	{
		try
		{
			this.checkParamsUpdatecomputer(serialNumber, andaluciaId, computerNumber, computer);
			// TODO: Obtener datos del ordenador desde la bbdd editarlo y subirlo
			return ResponseEntity.ok().body("OK");
		} catch (ComputerError ex)
		{
			log.error("Administration error", ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		} catch (Exception ex)
		{
			log.error("Server error", ex);
			return ResponseEntity.status(500).body("Server error");
		}

	}

}
