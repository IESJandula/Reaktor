package es.reaktor.reaktor.rest;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.Computer;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alex Cazalla
 *
 */
@RestController
@RequestMapping( value = "Reaktor",produces = "application/json")
public class RestHandlerMonitorizacion 
{
	/**Class logger */
	private Logger log = LogManager.getLogger();
	
	/** */
	/**
	 * Default constructor
	 */
	public RestHandlerMonitorizacion()
	{
		//Public constructor
	}
	/**
	 * *****************************************************************************
	 * *********************************REAK102M************************************
	 * *****************************************************************************
	 */
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
			this.checkSerialNumber(serialNumber);
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
	public ResponseEntity<?> sendScreenshot(@RequestPart(value = "screenshot", required = true)final File screenshot)
	{
		try
		{
			this.checkParam(screenshot);
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
			//TODO: Mandar la informacion al ordenador afectado obtenido de otro endpoint
			return ResponseEntity.ok().body("OK");
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
			@RequestHeader( value = "app",required = true) final String app
			) 
	{
		try
		{
			//Se comprueba que el ordenador exista
			if(serialNumber.isEmpty())
			{
				log.error("Pc not found");
				return ResponseEntity.status(404).body("PC not found, the serial number doesn't exists");
			}
			//Se comprueba que la app sea msi o exe
			else if(!app.endsWith(".msi") && !app.endsWith(".exe"))
			{
				log.error("App not found");
				return ResponseEntity.status(404).body("The app to install doesn't exists");
			}
			//Si es msi ejecuta un comando de instalacion silenciosa
			else if(app.endsWith(".msi"))
			{
				Runtime rt = Runtime.getRuntime();
				Process pr = rt.exec("cmd.exe msiexec /i "+app+" /qb! /l*v install.log");
				return ResponseEntity.ok().body("App installed succesfully");
			}
			//En caso contrario ejecutaria el exe
			else
			{
				Runtime rt = Runtime.getRuntime();
				Process pr = rt.exec("cmd.exe "+app+" /S");
				return ResponseEntity.ok().body("App installed succesfully");
			}
		}
		catch(IOException ex)
		{
			log.error("The system couldn't find the app",ex);
			return ResponseEntity.status(404).body("The app "+app+" couldn't be installed because doesn't exists or is deprecated");
		}
		catch(Exception ex)
		{
			log.error("Server error",ex);
			return ResponseEntity.status(404).body("Server error");
		}
	}
	
	private void checkParam(String serialNumber) throws ComputerError
	{
		if(serialNumber.isEmpty())
		{
			throw new ComputerError(1,"Serial number can't be null");
		}
	}
	
	
	
	
	
	
	
	
}
