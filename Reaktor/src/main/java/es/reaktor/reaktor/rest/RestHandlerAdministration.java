package es.reaktor.reaktor.rest;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.reaktor.reaktor.checker.Checkers;
import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.Peripheral;
import es.reaktor.reaktor.models.Software;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alejandro Cazalla
 *
 */
@RequestMapping(value = "/computers")
@RestController
public class RestHandlerAdministration
{
	/** Class logger */
	private static Logger log = LogManager.getLogger();
	private Checkers check = new Checkers();

	/**
	 * Default constructor
	 */
	public RestHandlerAdministration()
	{
		// Public constructor
	}

	/************************************************************************************************************************************************/
	/********************************************************************REAK 100A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/commandLine")
	public ResponseEntity<?> postComputerCommandLine(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant,
			@RequestHeader(value = "commandLineInstance", required = false) final CommandLine commandLine)
			throws ComputerError
	{
		try
		{
			String[] commands = new String[0];
			
			check.checkCommands(serialNumber, classroom, trolley, plant, commandLine);
			if (!serialNumber.isEmpty())
			{
				commands = Arrays.copyOf(commands, commands.length + 1);
				commands[commands.length - 1] = serialNumber;
			}
			if (!classroom.isEmpty())
			{
				commands = Arrays.copyOf(commands, commands.length + 1);
				commands[commands.length - 1] = classroom;
			}
			if (!trolley.isEmpty())
			{
				commands = Arrays.copyOf(commands, commands.length + 1);
				commands[commands.length - 1] = trolley;
			}
			if (plant != null)
			{
				commands = Arrays.copyOf(commands, commands.length + 1);
				commands[commands.length - 1] = String.valueOf(plant);
			}
			commandLine.setCommands(commands);
			// TODO: Mandarlo a la base de datos
			return ResponseEntity.ok().body("OK");
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

	/************************************************************************************************************************************************/
	/********************************************************************REAK 101A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerShutdown(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant)
	{
		try
		{
			check.checkParamsComputerShutdown(serialNumber, classroom, trolley, plant);
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
	/********************************************************************REAK 102A*******************************************************************/
	/************************************************************************************************************************************************/
	@RequestMapping(method = RequestMethod.POST, value = "/admin/restart", consumes = "application/json")
	public ResponseEntity<?> putComputerRestart(
			@RequestHeader(value = "serialNumber", required = false) final String serialNumber,
			@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestHeader(value = "plant", required = false) final Integer plant)
	{
		try
		{
			check.checkParamsPutComputerRestart(serialNumber, classroom, trolley, plant);
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
	@RequestMapping(method = RequestMethod.POST, value = "/by_body/", consumes = { "application/json" })
	public ResponseEntity<?> addByBody(@RequestHeader(value = "classroom", required = false) final String classroom,
			@RequestHeader(value = "trolley", required = false) final String trolley,
			@RequestBody(required = true) Peripheral[] peripherals)
	{
		try
		{
			check.checkParamsAddByBody(classroom, trolley, peripherals);
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
	@RequestMapping(method = RequestMethod.POST, value = "/admin/screenshotpost")
	public ResponseEntity<?> sendScreenshotPost(@RequestHeader(value = "classroom") final String classroom,
			@RequestHeader(value = "trolley") final String trolley)
	{
		try
		{
			check.checkParamsSendScreenshotOrder(classroom, trolley);
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
			check.checkParamsGetScreenshots(classroom, trolley);
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
			check.checkParamsPostComputerExeFile(serialNumber, classroom, trolley, plant, execFile);
			// TODO: Cuando se implante la bbdd llamar al ordenador ye incluirlo en
			// softaware
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
			check.checkParamsSendSoftware(classroom, trolley, professor, softwareInstance);
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
			check.checkParamsUninstallSoftware(classroom, trolley, professor, softwareInstance);
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
			check.checkParamsUpdatecomputer(serialNumber, andaluciaId, computerNumber, computer);
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
