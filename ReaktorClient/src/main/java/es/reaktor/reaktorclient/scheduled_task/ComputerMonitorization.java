package es.reaktor.reaktorclient.scheduled_task;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.reaktor.exceptions.ComputerError;
import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Peripheral;
import es.reaktor.models.Software;
import es.reaktor.models.Status;
import es.reaktor.models.monitoring.Actions;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author David Martinez
 *
 */
@Slf4j
@Component
public class ComputerMonitorization
{

	/**
	 * Method sendFullComputerTask scheduled task
	 * 
	 * @throws ReaktorClientException
	 */
	@Scheduled(fixedDelayString = "5000", initialDelay = 2000)
	public void sendFullComputerTask() throws ReaktorClientException
	{
		// THE COMPUTER FAKE FULL INFO STATUS
		Computer computerInfoMob = new Computer("sn1234", "and123", "cn123", "windows", "paco",
				new Location("0.5", 0, "trolley1"), new ArrayList<HardwareComponent>(),
				new ArrayList<Software>(List.of(new Software("Virtual Box"), new Software("PokeGame"))),
				new CommandLine(), new MonitorizationLog());

		// Object mapper
		ObjectMapper mapper = new ObjectMapper();

		// --- CLOSEABLE HTTP ---
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try
		{
			// --- GETTING THE COMPUTER AS STRING ---
			String computerString = mapper.writeValueAsString(computerInfoMob);
			// GETTING COMPUTER AS STRING ENTITY
			StringEntity computerStringEntity = new StringEntity(computerString);

			// GETTING HTTP CLIENT
			httpClient = HttpClients.createDefault();

			// DO THE HTTP POST WITH PARAMETERS
			HttpPost request = new HttpPost("http://localhost:8084/computers/send/fullInfo");
			request.setHeader("Content-Type", "application/json");
			request.setHeader("serialNumber", "sn1234");
			request.setEntity(computerStringEntity);

			response = httpClient.execute(request);

			String responseString = EntityUtils.toString(response.getEntity());
			log.info(responseString);
		} catch (JsonProcessingException exception)
		{
			String error = "Error Json Processing Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} catch (UnsupportedEncodingException exception)
		{
			String error = "Error Unsupported Encoding Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} catch (ClientProtocolException exception)
		{
			String error = "Error Client Protocol Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} catch (IOException exception)
		{
			String error = "Error In Out Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} finally
		{
			if (httpClient != null)
			{
				try
				{
					httpClient.close();
				} catch (IOException exception)
				{
					String error = "Error In Out Exception";
					log.error(error, exception);
					throw new ReaktorClientException(exception);
				}
			}
			if (response != null)
			{
				try
				{
					response.close();
				} catch (IOException exception)
				{
					String error = "Error In Out Exception";
					log.error(error, exception);
					throw new ReaktorClientException(exception);
				}
			}
		}
	}

	/**
	 * Method sendStatusComputerTask scheduled task
	 * 
	 * @throws ReaktorClientException
	 */
	@Scheduled(fixedDelayString = "6000", initialDelay = 2000)
	public void sendStatusComputerTask() throws ReaktorClientException
	{
		List<Status> statusList = new ArrayList<>();
		String serialNumber = "sn123556";

		// --- CLOSEABLE HTTP ---
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try
		{
			// GETTING HTTP CLIENT
			httpClient = HttpClients.createDefault();

			// DO THE HTTP POST WITH PARAMETERS
			HttpPost request = new HttpPost("http://localhost:8084/computers/send/status");
			request.setHeader("serialNumber", serialNumber);

			response = httpClient.execute(request);

			String responseString = EntityUtils.toString(response.getEntity());
			log.info(responseString);

			Actions actionsToDo = new ObjectMapper().readValue(responseString, Actions.class);

			// --- SHUTDOWN ---
			this.actionsShutdown(statusList, serialNumber, actionsToDo);
			// --- RESTART ---
			this.actionsRestart(statusList, serialNumber, actionsToDo);
			// --- EXECUTE COMMANDS ---
			this.actionsCommands(statusList, serialNumber, actionsToDo);
			// --- BLOCK DISP ---
			this.actionsBlockDisp(statusList, serialNumber, actionsToDo);
			// --- OPEN WEBS ---
			this.actionsOpenWeb(statusList, serialNumber, actionsToDo);

			// --- UPDATE ACTIONS ---
			if (actionsToDo.getUpdateAndaluciaId() != null && !actionsToDo.getUpdateAndaluciaId().isEmpty())
			{
				log.info("UPDATE ANDALUCIA ID TO - " + actionsToDo.getUpdateAndaluciaId());
			}
			if (actionsToDo.getUpdateComputerNumber() != null && !actionsToDo.getUpdateComputerNumber().isEmpty())
			{
				log.info("UPDATE COMPUTER NUMBER TO - " + actionsToDo.getUpdateComputerNumber());
			}
			if (actionsToDo.getUpdateSerialNumber() != null && !actionsToDo.getUpdateSerialNumber().isEmpty())
			{
				log.info("UPDATE SERIAL NUMBER TO - " + actionsToDo.getUpdateSerialNumber());
			}

			// --- TO-DO NEW ENDPOINT TO SEND STATUS TO SERVER ---
			log.info(statusList.toString());

		} catch (JsonProcessingException exception)
		{
			String error = "Error Json Processing Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} catch (UnsupportedEncodingException exception)
		{
			String error = "Error Unsupported Encoding Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} catch (ClientProtocolException exception)
		{
			String error = "Error Client Protocol Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} catch (IOException exception)
		{
			String error = "Error In Out Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		} finally
		{
			if (httpClient != null)
			{
				try
				{
					httpClient.close();
				} catch (IOException exception)
				{
					String error = "Error In Out Exception";
					log.error(error, exception);
					throw new ReaktorClientException(exception);
				}
			}
			if (response != null)
			{
				try
				{
					response.close();
				} catch (IOException exception)
				{
					String error = "Error In Out Exception";
					log.error(error, exception);
					throw new ReaktorClientException(exception);
				}
			}
		}

	}

	@Scheduled(fixedDelayString = "6000", initialDelay = 2000)
	public void getAndSendScreenshot()
	{
		String serialNumber = "sn123556";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		httpClient = HttpClients.createDefault();

		HttpGet request = new HttpGet("http://localhost:8084/computers/get/screenshot");
		request.setHeader("serialNumber", serialNumber);

		try
		{
			response = httpClient.execute(request);
			String responseString = EntityUtils.toString(response.getEntity());
			log.info(responseString);

			if (responseString.equalsIgnoreCase("OK"))
			{
				try
				{
					try
					{
						System.setProperty("java.awt.headless", "false");
						Robot robot = new Robot();
						Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

						BufferedImage screesnShot = robot.createScreenCapture(rect);

						ImageIO.write(screesnShot, "JPG", new File(".\screenshot.jpg"));
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Capture screen with the input string as file name
	 * 
	 * @param fileName a given file name
	 * @throws Exception if error occurs
	 **/
	public static void captureScreen(String fileName) throws Exception
	{

	}

	/**
	 * Method actionsOpenWeb
	 * 
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsOpenWeb(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.getOpenWebs() != null && !actionsToDo.getOpenWebs().isEmpty())
		{
			try
			{
				for (String commandOpenWeb : actionsToDo.getOpenWebs())
				{
					log.info("cmd.exe /c " + commandOpenWeb);
					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec("cmd.exe /c " + commandOpenWeb);

				}
				Status status = new Status("Execute Web Commands " + serialNumber, true, null);
				statusList.add(status);
			} catch (Exception exception)
			{
				Status status = new Status("Execute Commands " + serialNumber, false,
						new ComputerError(333, "error on execute web command", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method actionsBlockDisp
	 * 
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsBlockDisp(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.getBlockDispositives() != null && !actionsToDo.getBlockDispositives().isEmpty())
		{
			try
			{
				List<Peripheral> blockDispositives = new ArrayList<Peripheral>();
				for (int i = 0; i < actionsToDo.getBlockDispositives().size(); i++)
				{
					Peripheral peri = actionsToDo.getBlockDispositives().get(i);
					peri.setOpen(false);
					blockDispositives.add(peri);
				}
				log.info("DISPOSITIVES TO BLOCK : " + blockDispositives);

				Status status = new Status("Dispotisive blocked " + serialNumber, true, null);
				statusList.add(status);
			} catch (Exception exception)
			{
				Status status = new Status("Dispotisive blocked error " + serialNumber, false,
						new ComputerError(121, "error on block", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method actionsCommands
	 * 
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsCommands(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.getCommands() != null && !actionsToDo.getCommands().isEmpty())
		{
			try
			{
				for (String command : actionsToDo.getCommands())
				{
					// --- GETTING COMMAND TO EXEC ---
					log.info("cmd.exe /c " + command);
					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec("cmd.exe /c " + command);
				}
				Status status = new Status("Execute Commands " + serialNumber, true, null);
				statusList.add(status);
			} catch (Exception exception)
			{
				Status status = new Status("Execute Commands " + serialNumber, false,
						new ComputerError(111, "error on execute command", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method actionsRestart
	 * 
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsRestart(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.isRestart())
		{
			try
			{
				Runtime rt = Runtime.getRuntime();
				Process pr = rt.exec("cmd.exe /c shutdown -r -t 61");

				Status status = new Status("restart computer " + serialNumber, true, null);
				statusList.add(status);
			} catch (Exception exception)
			{
				Status status = new Status("Restart computer " + serialNumber, false,
						new ComputerError(002, "error on restart computer ", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method actionsShutdown
	 * 
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsShutdown(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.isShutdown())
		{
			try
			{
				Runtime rt = Runtime.getRuntime();
				Process pr = rt.exec("cmd.exe /c shutdown -s -t 61");

				Status status = new Status("Shutdown computer " + serialNumber, true, null);
				statusList.add(status);

			} catch (Exception exception)
			{
				Status status = new Status("Shutdown computer " + serialNumber, false,
						new ComputerError(001, "error on Shutdown computer ", null));
				statusList.add(status);
			}
		}
	}
}
