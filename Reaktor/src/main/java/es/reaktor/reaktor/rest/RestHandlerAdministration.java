package es.reaktor.reaktor.rest;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.reaktor.reaktor.constants.Constantes;
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
	/** Mapa de status para identificar procesos a ejecutar */
	private Map<String, Status> pcStatus;
	/**Lista de ordenadores para casos de prueba */
	private List<Computer> computers;
	

	/**
	 * Default constructor
	 */
	public RestHandlerAdministration()
	{
		// Public constructor
		this.pcStatus = new HashMap<String, Status>();
		this.computers = Constantes.cargarOrdenadores();
	}

	/**
	 * Endpoint que manda comandos cmd para ejecutarlos en un respectivo ordenador
	 * 
	 * @param serialNumber numero de serie del ordenador
	 * @param classroom    clase a la que pertenece el ordenador
	 * @param trolley
	 * @param plant
	 * @param commandLine
	 * @return OK si la configuracion es correcta, 404 si es incorrecta o 500 si
	 *         hubo un error de servidor
	 * @throws ComputerError
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/commandLine",consumes = "application/json")
	public ResponseEntity<?> postComputerCommandLine(
			@RequestHeader(value = "serialNumber", required = false) String serialNumber,
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "plant", required = false) Integer plant,
			@RequestBody(required = true) final CommandLine commandLine)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			serialNumber = serialNumber==null ? "" : serialNumber;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			// Objeto status para mandarlo a ejecutar en cliente
			Status status = null;
			// Comprobamos que los atributos no esten vacios
			this.checkParams(serialNumber, classroom, trolley, plant, commandLine);
			if(!serialNumber.isEmpty()) 
			{
				this.checkComputer(serialNumber, "", "", computers);
			}else
			{
				this.checkArguments(classroom, trolley, plant, computers);
			}
			
			if (!serialNumber.isEmpty())
			{
				
				status = new Status(serialNumber,String.valueOf(commandLine.getCommands()), false);
				// Añadimos como clave el pc y como valor un objeto status con command line
				this.pcStatus.put("serialNumber", status);
				
			}
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,String.valueOf(commandLine.getCommands()), false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con command line
				this.pcStatus.put("classroom", status);
				
			}
			if (!trolley.isEmpty())
			{
				
				status = new Status(trolley,String.valueOf(commandLine.getCommands()), false);
				// Añadimos como clave el pc y como valor un objeto status con command line
				this.pcStatus.put("trolley", status);	
			}
			if (plant != null)
			{
				status = new Status(String.valueOf(plant),String.valueOf(commandLine.getCommands()), false);
				// Añadimos como clave el pc y como valor un objeto status con command line
				this.pcStatus.put("plant", status);
			}
			return ResponseEntity.ok().body("Comandos mandados a ejecutar");
		} catch (ComputerError ex)
		{
			log.error("Administration error", ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		} catch (Exception ex)
		{
			log.error("Internal server error", ex);
			return ResponseEntity.status(500).body("Server error");
		}
	}

	/**
	 * Metodo que manda una peticion a un pc, clase o carrito para apagarse
	 * 
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @return OK si los parametros estan bien formados, 404 si los parametros
	 *         fallan o 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/shutdown", consumes = "application/json")
	public ResponseEntity<?> putComputerShutdown(
			@RequestHeader(value = "serialNumber", required = false) String serialNumber,
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "plant", required = false) Integer plant)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			serialNumber = serialNumber==null ? "" : serialNumber;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			Status status = null;
			// Se comprueba que los parametros no esten vacios
			this.checkParams(serialNumber, classroom, trolley, plant);
			if(!serialNumber.isEmpty()) 
			{
				this.checkComputer(serialNumber, "", "", computers);
			}else
			{
				this.checkArguments(classroom, trolley, plant, computers);
			}
			if (!serialNumber.isEmpty())
			{
				status = new Status(serialNumber,"apagado", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado apagado
				this.pcStatus.put("serialNumber", status);
			}
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,"apagado", false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con el estado apagado
				this.pcStatus.put("classroom", status);
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"apagado", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado apagado
				this.pcStatus.put("trolley", status);
			}
			if (plant != null)
			{
				status = new Status(String.valueOf(plant),"apagado", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado apagado
				this.pcStatus.put("plant", status);
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

	/**
	 * Metodo que manda una petición a pc, clase o carrito para reiniciarse
	 * 
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @return OK si los parametros estan bien formados, 404 si los parametros
	 *         fallan o 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerRestart(
			@RequestHeader(value = "serialNumber", required = false) String serialNumber,
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "plant", required = false) Integer plant)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			serialNumber = serialNumber==null ? "" : serialNumber;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			Status status = null;
			this.checkParams(serialNumber, classroom, trolley, plant);
			if(!serialNumber.isEmpty()) 
			{
				this.checkComputer(serialNumber, "", "", computers);
			}else
			{
				this.checkArguments(classroom, trolley, plant, computers);
			}
			if (!serialNumber.isEmpty())
			{
				status = new Status(serialNumber,"reiniciado", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "reiniciado"
				this.pcStatus.put("serialNumber", status);
			}
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,"reiniciado", false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con el estado "reiniciado"
				this.pcStatus.put("classroom", status);
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"reiniciado", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "reiniciado"
				this.pcStatus.put("trolley", status);
			}
			if (plant != null)
			{
				status = new Status(String.valueOf(plant),"reiniciado", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "reiniciado"
				this.pcStatus.put("plant", status);
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

	/**
	 * Endpoint que bloquea o desbloquea perifericos en una clase o carrito
	 * 
	 * @param classroom
	 * @param trolley
	 * @param peripherals
	 * @return OK si los parametros estan bien formados, 404 si los parametros
	 *         fallan o 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/peripheral", consumes =
	{ "application/json" })
	public ResponseEntity<?> postPeripheral(
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestBody(required = true) Peripheral[] peripherals)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			Status status = null;
			this.checkParams(classroom, trolley, peripherals);
			this.checkArguments(classroom, trolley, null, computers);
			
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,Arrays.toString(peripherals), false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con el estado "perifericos"
				this.pcStatus.put("classroom", status);
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,Arrays.toString(peripherals), false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "perifericos"
				this.pcStatus.put("trolley", status);
			}
			return ResponseEntity.ok().body("All OK");
		} catch (ComputerError ce)
		{
			log.error("Administration error", ce);
			return ResponseEntity.status(400).body(ce.getBodyMessageException());
		} catch (Exception e)
		{
			log.error("Error de servidor", e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	/**
	 * Endpoint que solicita al servidor envio de capturas mediante clases o
	 * carritos
	 * 
	 * @param classroom
	 * @param trolley
	 * @return OK si los parametros estan bien formados, 404 si los parametros
	 *         fallan o 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/screenshot")
	public ResponseEntity<?> sendScreenshotOrder(
			@RequestHeader(value = "classroom",required = false) String classroom,
			@RequestHeader(value = "trolley",required = false)  String trolley)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			Status status = null;
			this.checkParams(classroom, trolley);
			this.checkArguments(classroom, trolley, null, computers);
			
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,"sendCapturas", false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con el estado "capturas"
				this.pcStatus.put("classroom", status);
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"sendCapturas", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "capturas"
				this.pcStatus.put("trolley", status);
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

	/**
	 * Endpoint que se encarga de descargar un fichero zip de capturas de pantalla de una clase o carrito
	 * @param classroom
	 * @param trolley
	 * @return OK si los parametros estan bien compuestos, 404 si fallan o 500 si hubo error de servidor
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin/screenshot", produces = "application/zip")
	public ResponseEntity<?> getScreenshots(@RequestHeader(value = "classroom") String classroom,
			@RequestHeader(value = "trolley") String trolley)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			Status status = null;
			this.checkParamsGetScreenshots(classroom, trolley);
			this.checkArguments(classroom, trolley, null, computers);
			
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,"capturas", false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con el estado "capturas"
				this.pcStatus.put("classroom", status);
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"capturas", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "capturas"
				this.pcStatus.put("trolley", status);
			}
			File zipFile = new File("screenshots.zip");
			// TODO: Cuando se implante la bbdd llamar al ordenador ye incluirlo en
			// softaware
			return ResponseEntity.ok().body(zipFile);
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

	/**
	 * Endpoint que manda un ejecutable a un ordenador, clase, carrito o planta a ejecurtar o instalarse
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @param execFile
	 * @return OK si los parametros estan bien introducidos, 404 si los parametros o el fichero fallan, 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/file")
	public ResponseEntity<?> postComputerExeFile(
			@RequestHeader(value = "serialNumber", required = false) String serialNumber,
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "plant", required = false) Integer plant,
			@RequestPart(value = "execFile", required = false) final MultipartFile execFile)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			serialNumber = serialNumber==null ? "" : serialNumber;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			Status status = null;
			this.checkParamsPostComputerExeFile(serialNumber, classroom, trolley, plant, execFile);
			if(!serialNumber.isEmpty()) 
			{
				this.checkComputer(serialNumber, "", "", computers);
			}else
			{
				this.checkArguments(classroom, trolley, plant, computers);
			}
			
			if (!serialNumber.isEmpty())
			{
				status = new Status(serialNumber,"ejecutable", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "ejecutable"
				this.pcStatus.put("serialNumber", status);
			}
			if (!classroom.isEmpty())
			{
				status = new Status(classroom,"ejecutable", false);
				// Añadimos como clave una clase en la que hay ordenadores y como valor un
				// objeto status con el estado "ejecutable"
				this.pcStatus.put("classroom", status);
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"ejecutable", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "ejecutable"
				this.pcStatus.put("trolley", status);
			}
			if (plant != null)
			{
				status = new Status(String.valueOf(plant),"ejecutable", false);
				// Añadimos como clave el pc y como valor un objeto status con el estado
				// "ejecutable"
				this.pcStatus.put(String.valueOf(plant), status);
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

	/**
	 * Endpoint que manda a ejecutar software a una clase, carrito o profesor
	 * @param classroom
	 * @param trolley
	 * @param professor
	 * @param softwareInstance
	 * @return OK si los parametros estan bien introducidos, 404 si los parametros o el software falla y 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> sendSoftware(@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "professor", required = false) String professor,
			@RequestBody(required = true) final Software[] softwareInstance)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			professor = professor==null ? "" : professor;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			professor = professor==null ? "" : professor;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			this.checkParamsSendSoftware(classroom, trolley, professor, softwareInstance);
			this.checkArguments(classroom, trolley, null, computers);
			
			Status status = null;
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"install" + softwareInstance, false);
				this.pcStatus.put("trolley", status);
				// Añadimos como clave el carrito que contiene ordenadores y como valor un
				// objeto status con el estado
				// "install" + Nombre de la Aplicacion
			}
			if (!professor.isEmpty())
			{
				status = new Status(professor,"install" + softwareInstance, false);
				this.pcStatus.put("professor", status);
				// Añadimos como clave el ordenador del profesor y como valor un objeto status
				// con el estado
				// "install" + Nombre de la Aplicacion
			}
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

	/**
	 * Endpoint que borra software de una clase, carrito o profesor
	 * @param classroom
	 * @param trolley
	 * @param professor
	 * @param softwareInstance
	 * @return OK si los parametros estan bien introducidos, 404 si los parametros o el software falla o 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/software", consumes = "application/json")
	public ResponseEntity<?> uninstallSoftware(
			@RequestHeader(value = "classroom", required = false) String classroom,
			@RequestHeader(value = "trolley", required = false) String trolley,
			@RequestHeader(value = "professor", required = false) String professor,
			@RequestBody(required = true) final Software[] softwareInstance)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			professor = professor==null ? "" : professor;
			classroom = classroom==null ? "" : classroom;
			trolley = trolley==null ? "" : trolley;
			this.checkParamsUninstallSoftware(classroom, trolley, professor, softwareInstance);
			this.checkArguments(classroom, trolley, null, computers);
			
			Status status = null;
			if(!classroom.isEmpty())
			{
				status = new Status(classroom,"uninstall" + softwareInstance, false);
				this.pcStatus.put("classroom", status);
				// Añadimos como clave una clase que contiene ordenadores y como valor un objeto status con el estado
				// "uninstall" + Nombre de la Aplicacion
			}
			if (!trolley.isEmpty())
			{
				status = new Status(trolley,"uninstall" + softwareInstance, false);
				this.pcStatus.put("trolley", status);
				// Añadimos como clave el carrito que contiene ordenadores y como valor un objeto status con el estado
				// "uninstall" + Nombre de la Aplicacion
			}
			if (!professor.isEmpty())
			{
				status = new Status(professor,"uninstall" + softwareInstance, false);
				this.pcStatus.put("professor", status);
				// Añadimos como clave el ordenador del profesor y como valor un objeto status con el estado
				// "uninstall" + Nombre de la Aplicacion
			}
			
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

	/**
	 * Endpoint que permite editar un ordenador buscandolo por sus atributos
	 * @param serialNumber
	 * @param andaluciaId
	 * @param computerNumber
	 * @param computer
	 * @return OK si los parametros estan bien introducidos, 404 si los parametros o el ordenador falla o 500 si hubo un error de servidor
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/edit")
	public ResponseEntity<?> updateComputer(
			@RequestHeader(value = "serialNumber", required = false) String serialNumber,
			@RequestHeader(value = "andaluciaId", required = false) String andaluciaId,
			@RequestHeader(value = "computerNumber", required = false) String computerNumber,
			@RequestBody(required = true) Computer computer)
	{
		try
		{
			//Comprobamos que los valores no sean nulos para evitar punteros vacios
			serialNumber = serialNumber==null ? "" : serialNumber;
			andaluciaId = andaluciaId==null ? "" : andaluciaId;
			computerNumber = computerNumber==null ? "" : computerNumber;
			//Comprobamos que los parametros coincidan
			computer = this.checkParamsUpdatecomputer(serialNumber, andaluciaId, computerNumber, computer,computers);
			this.checkComputer(serialNumber, andaluciaId, computerNumber, computers);
			
			//Reemplazamos el ordenador antiguo por el nuevo
			this.computers = this.replaceComputer(computer, computers);
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
	/**
	 * Endpoint que envia el mapa de status para posteriormente ejecutarse
	 * @return mapa de status
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get-task")
	public ResponseEntity<?> sendStatus()
	{
		try
		{
			return ResponseEntity.ok().body(this.pcStatus);
		}
		catch(Exception ex)
		{
			log.error("Server error");
			return ResponseEntity.status(500).body("Server error");
		}
		finally
		{
			this.pcStatus = new HashMap<String,Status>();
		}
	}

}