package es.reaktor.reaktor.rest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.Status;
import es.reaktor.reaktor.utils.IChecker;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alex Cazalla
 *
 */
@RestController
@RequestMapping( value = "/reaktor",produces = "application/json")
public class RestHandlerMonitorizacion implements IChecker 
{
	/**Class logger */
	private Logger log = LogManager.getLogger();
	
	/**String temporal que almacena los status */
	private String statusJson;
	/**Lista de ordenadores para casos de prueba */
	private List<Computer> computers;
	/**
	 * Default constructor
	 */
	public RestHandlerMonitorizacion()
	{
		//Public constructor
		this.statusJson = "";
		this.computers = Constantes.cargarOrdenadores();
	}
	
	/**
	 * Endpoint que recibe una lista de status a ejecutar 
	 * @return lista de status a ejecutar
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/status")
	public ResponseEntity<?> getStatus()
	{
		//Creamos el cliente
		CloseableHttpClient httpClient = null;
		//Creamos la respuesta
		CloseableHttpResponse response = null;
		try
		{
			//Instanciamos cliente y respuesta
			httpClient = HttpClients.createDefault();
			//Definimos la url de la peticion
			String url = "http://localhost:8084/computers/get-task";
			//Definimos el tipo de respuesta
			HttpGet request = new HttpGet(url);
			response = httpClient.execute(request);
			//Obtenemos la respuesta
			this.statusJson = EntityUtils.toString(response.getEntity());
			log.info("Respuesta aceptada "+this.statusJson);
			this.writeJson(this.statusJson);
			log.info("Json escrito");
			return ResponseEntity.ok("Status recibido");
		
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(500).body("Server error");
		}
		//Cerrado de los componentes http
		finally
		{
			try
			{
				if(response!=null)
				{
					response.close();
				}
				if(httpClient!=null)
				{
					httpClient.close();
				}
			}
			catch(IOException ex)
			{ 
				log.error("Error al cerrar las peticiones",ex);
			}
			
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/execute-task")
	public ResponseEntity<?> executeTask()
	{
		try
		{
			File file = new File("src/main/resources/status.json");
			String resultadoTarea = "";
			if(!file.exists())
			{
				throw new ComputerError(1,"Error the json file doesn't exists");
			}
			else
			{
				resultadoTarea = this.execute(computers);
			}
			return ResponseEntity.ok().body(resultadoTarea);
		}
		catch(ComputerError ex)
		{
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(500).body("Error de servidor "+ex);
		}
	}
	
	/**
	 * Metodo que devuelve una captura de un ordenador usando su numero de serie
	 * @param serialNumber
	 * @return Respuesta de 200 si encuentra el pc, respuesta de 404 o 500 si no lo encuentra 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/screenshots")
	public ResponseEntity<?> getScreenshotsOrder(@RequestHeader(value = "serialNumber", required = false)final String serialNumber)
	{
		try
		{
			//Comprobamos que los parametros esten bien formados
			this.checkParams(serialNumber);
			return ResponseEntity.ok().body("Getting success") ;
		}
		catch (ComputerError exception)
		{
			String message = "Computer error getting";
			log.error(message, exception);
			return ResponseEntity.status(404).body(exception.getBodyMessageException());
		}
		catch (Exception exception)
		{
			String message = "Server Error";
			log.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}
	
	public void checkSerialNumber(String serialNumber) throws ComputerError
	{
		if(serialNumber.isEmpty())
		{
			throw new ComputerError(1, "Serial Number is null");
		}
	}
	
	/**
	 * *****************************************************************************
	 * *********************************REAK103M************************************
	 * *****************************************************************************
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/screenshots", consumes = "multipart/form-data")
	public ResponseEntity<?> sendScreenshot(@RequestPart(value = "screenshot", required = true)final MultipartFile screenshot)
	{
		try
		{
			this.checkScreenshot(screenshot);
			return ResponseEntity.ok().body("Getting success") ;
		}
		catch (ComputerError exception)
		{
			String message = "Computer error getting";
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
	public void checkParam(File screenshot) throws ComputerError
	{
		if(screenshot == null)
		{
			throw new ComputerError(1, "Error with the screenshot");
		}
	}
	/**
	 * *****************************************************************************
	 * *********************************REAK104M************************************
	 * *****************************************************************************
	 */
	
	@RequestMapping(method = RequestMethod.POST, value = "/send/fullInfo", consumes = "application/json" )
	public ResponseEntity<?> sendFullComputer (
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "andaluciaId", required = false) final String andaluciaId,
			@RequestHeader(value = "computerNumber", required = false) final String computerNumber,
			@RequestBody(required = true) final Computer computerInstance
			)
	{
		try
		{
			this.checkParams(serialNumber, andaluciaId, computerNumber, computerInstance);
			Computer computer = this.checkParamsUpdatecomputer(serialNumber, andaluciaId, computerNumber, computerInstance, this.computers);
			return ResponseEntity.ok().body(computer);
		}
		catch(ComputerError ex)
		{
			log.error("Administration error",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(404).body("Server error");
		}
	}
	
	private void checkParams(String serialNumber,String andaluciaId,String computerNumber,Computer computerInstance) throws ComputerError
	{
		if(serialNumber.isEmpty() && andaluciaId.isEmpty() && computerNumber.isEmpty())
		{
			throw new ComputerError(1, "All params can't be null");
		}
		else if(computerInstance == null)
		{
			throw new ComputerError(3, "Computer to send info can't be null");
		}
	}
	/**
	 * Metodo que insala una app pasandole la app a instalar y el numero de serie del ordenador al que se le desea instalar esa app
	 * @param serialNumber
	 * @param app
	 * @return 200 si la app se ha instalado, 404 si no encuentra el ordenador o la app o 500 si hay otro error
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/install/app", consumes = "application/json")
	public ResponseEntity<?> installApp(
			@RequestHeader( value = "serialNumber",required = true) final String serialNumber,
			@RequestHeader( value = "app",required = true) final MultipartFile app
			) 
	{
		try
		{
			String nameApp = app.getName();
			//Se comprueba que el ordenador exista
			if(serialNumber.isEmpty())
			{
				log.error("Pc not found");
				return ResponseEntity.status(404).body("PC not found, the serial number doesn't exists");
			}
			//Se comprueba que la app sea msi o exe
			else if(!nameApp.endsWith(".msi") && !nameApp.endsWith(".exe"))
			{
				log.error("App not found");
				return ResponseEntity.status(404).body("The app to install doesn't exists");
			}
			//this.status.add(new Status("install",false));
			
			return ResponseEntity.ok().body("File download succesfully");
			
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(404).body("Server error");
		}
	}
	
	/**
	 * Metodo que instala un .exe o un .msi se ejecuta cuando se comprueba el status de un ordenador
	 * @param serialNumber
	 * @param app
	 */
	private void install(String serialNumber,MultipartFile app) throws IOException
	{
		String fileName = app.getName();
		//Si es msi ejecuta un comando de instalacion silenciosa
		if(fileName.endsWith(".msi"))
		{
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("cmd.exe msiexec /i "+app+" /qb! /l*v install.log");
		}
		//En caso contrario ejecutaria el exe
		else
		{
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("cmd.exe "+app+" /S");
		}
	}
	
}