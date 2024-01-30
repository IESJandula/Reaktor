package es.monitoringserver.monitoringclient.scheduled_task;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.yaml.snakeyaml.Yaml;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.monitoringserver.exceptions.ComputerError;
import es.monitoringserver.models.CommandLine;
import es.monitoringserver.models.Computer;
import es.monitoringserver.models.HardwareComponent;
import es.monitoringserver.models.Location;
import es.monitoringserver.models.MonitorizationLog;
import es.monitoringserver.models.Peripheral;
import es.monitoringserver.models.Software;
import es.monitoringserver.models.Status;
import es.monitoringserver.models.monitoring.Actions;
import es.monitoringserver.monitoringclient.utils.exceptions.ReaktorClientException;
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
		}
		catch (JsonProcessingException exception)
		{
			String error = "Error Json Processing Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (UnsupportedEncodingException exception)
		{
			String error = "Error Unsupported Encoding Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (ClientProtocolException exception)
		{
			String error = "Error Client Protocol Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (IOException exception)
		{
			String error = "Error In Out Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		finally
		{
			closeHttpClientResponse(httpClient, response);
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

			// DO THE HTTP GET WITH PARAMETERS
			HttpGet request = new HttpGet("http://localhost:8084/computers/get/pendingActions");
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
			// --- INSTALL APPS ---
			this.actionsInstallApps(statusList, serialNumber, actionsToDo);
			// --- UNINSTALL APPS ---
			this.actionsUninstallApps(statusList, serialNumber, actionsToDo);
			// --- ADD CFG WIFI ---
			this.actionsCfgWifiFile(statusList, serialNumber, actionsToDo);

			//---- UPDATE ANDALUCIA - S/N - COMPUTER NUMBER SNAKE YAML START -----
			// -- GETING THE MONITORIZATION YML , WITH THE INFO ---
	        InputStream inputStream = new FileInputStream(new File("./src/main/resources/monitorization.yml"));
			Yaml yaml = new Yaml();
			
			// --- LOADING THE INFO INTO STRING OBJECT MAP ---
	        Map<String, Object> yamlMap = yaml.load(inputStream);
	        
	        // --- GETTING THE INFO INTO STRING STRING MAP (CAST)---
            Map<String, String> computerMonitorizationYml = (Map<String, String>) yamlMap.get("ComputerMonitorization");

            // --- LOG THE INFO FROM THE MAP ---
            log.info("andaluciaId: " + computerMonitorizationYml.get("andaluciaId"));
            log.info("computerNumber: " + computerMonitorizationYml.get("computerNumber"));
            log.info("serialNumber: " + computerMonitorizationYml.get("serialNumber"));
            //---- UPDATE ANDALUCIA - S/N - COMPUTER NUMBER SNAKE YAML END -----
            
            
			// --- UPDATE ACTIONS ---
			this.updateAndaluciaId(statusList, serialNumber, actionsToDo, computerMonitorizationYml);
			this.updateComputerNumber(statusList, serialNumber, actionsToDo, computerMonitorizationYml);
			this.updateSerialNumber(statusList, serialNumber, actionsToDo, computerMonitorizationYml);
			
			// -- SAVING ALL MAP INFO INTO MONITORIZATION.YML ---
			this.savingMonitorizationYmlCfg(computerMonitorizationYml);

			// --- ENDPOINT TO SEND STATUS TO SERVER ---
			log.info(statusList.toString());
	
			// GETTING NEW HTTP CLIENT
			CloseableHttpClient httpClientStatus = HttpClients.createDefault();

			// DO THE HTTP POST WITH PARAMETERS
			HttpPost requestPost = new HttpPost("http://localhost:8084/computers/send/status");
			requestPost.setHeader("Content-type", "application/json");
			
			// -- SETTING THE STATUS LIST ON PARAMETERS FOR POST PETITION ---
			StringEntity statusListEntity = new StringEntity(new ObjectMapper().writeValueAsString(statusList));
			requestPost.setEntity(statusListEntity);
			requestPost.setHeader("serialNumber", serialNumber);
			
			CloseableHttpResponse responseStatus = httpClient.execute(requestPost);
			

		}
		catch (JsonProcessingException exception)
		{
			String error = "Error Json Processing Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (UnsupportedEncodingException exception)
		{
			String error = "Error Unsupported Encoding Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (ClientProtocolException exception)
		{
			String error = "Error Client Protocol Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (IOException exception)
		{
			String error = "Error In Out Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		finally
		{
			this.closeHttpClientResponse(httpClient, response);
		}

	}

	
	/**
	 * Method actionsCfgWifiFile
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsCfgWifiFile(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		// --- IF THE STRING FILE IS NOT NULL OR EMPTY/BLANK ---
		if (actionsToDo.getConfigurationWifi()!=null && !actionsToDo.getConfigurationWifi().isBlank() && !actionsToDo.getConfigurationWifi().isEmpty())
		{
			try
			{
				// --- IF THE FILE EXISTS AND IS A FILE ---
				File cfgFile = new File(actionsToDo.getConfigurationWifi());
				if(cfgFile.exists() && cfgFile.isFile()) 
				{
					// --- RUN COMMAND ---
					log.info(" ADD CFG WIFI -- > cmd.exe /c "+cfgFile.getAbsolutePath());
					Runtime.getRuntime().exec
					(
					"cmd.exe /c netsh wlan add profile filename="+cfgFile.getAbsolutePath()+""
					);
					
					// -- STATUD DONE --
					Status status = new Status("ADD CFG WIFI exec " + serialNumber, true, null);
					statusList.add(status);
				}
				else 
				{
					// --- ERROR ON FILE ---
					Status status = new Status("ADD CFG WIFI Error " + serialNumber, false,
							new ComputerError(666, "error CFG doesnt exist or is not a file", null));
					statusList.add(status);
				}	
			}
			catch (Exception exception)
			{
				// --- ERROR ON ACTION ---
				Status status = new Status("ADD CFG WIFI Error " + serialNumber, false,
						new ComputerError(666, "error on ADD CFG WIFI", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method actionsInstallApps
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsInstallApps(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.getInstallApps() != null && !actionsToDo.getInstallApps().isEmpty())
		{
			try
			{
				for (String app : actionsToDo.getInstallApps())
				{
					log.info(" INSTALL -- > cmd.exe /c " + app);
					Runtime.getRuntime().exec
					(
					"cmd.exe /c winget install "+app+" --silent --accept-package-agreements --accept-source-agreements --force"
					);

				}
				Status status = new Status("Install App exec " + serialNumber, true, null);
				statusList.add(status);
			}
			catch (Exception exception)
			{
				Status status = new Status("Install app Error " + serialNumber, false,
						new ComputerError(666, "error on Install app", null));
				statusList.add(status);
			}
		}
	}
	
	
	/**
	 * Method actionsUninstallApps
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 */
	private void actionsUninstallApps(List<Status> statusList, String serialNumber, Actions actionsToDo)
	{
		if (actionsToDo.getUninstallApps() != null && !actionsToDo.getUninstallApps().isEmpty())
		{
			try
			{
				for (String app : actionsToDo.getUninstallApps())
				{
					log.info(" UNINSTALL -- > cmd.exe /c " + app);
					Runtime.getRuntime().exec
					(
					"cmd.exe /c winget uninstall --id "+app+" --silent --force"
					);

				}
				Status status = new Status("Uninstall App exec " + serialNumber, true, null);
				statusList.add(status);
			}
			catch (Exception exception)
			{
				Status status = new Status("Uninstall app Error " + serialNumber, false,
						new ComputerError(777, "error on Uninstall app", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method closeHttpClientResponse
	 * @param httpClient
	 * @param response
	 * @throws ReaktorClientException
	 */
	private void closeHttpClientResponse(CloseableHttpClient httpClient, CloseableHttpResponse response)
			throws ReaktorClientException
	{
		if (httpClient != null)
		{
			try
			{
				httpClient.close();
			}
			catch (IOException exception)
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
			}
			catch (IOException exception)
			{
				String error = "Error In Out Exception";
				log.error(error, exception);
				throw new ReaktorClientException(exception);
			}
		}
	}

	/**
	 * Method savingMonitorizationYmlCfg
	 * @param computerMonitorizationYml
	 * @throws IOException
	 */
	private void savingMonitorizationYmlCfg(Map<String, String> computerMonitorizationYml) throws IOException
	{
		if(computerMonitorizationYml!=null) 
		{
			// --- OPCION RAW , NUEVO YML CON LA INFO ---
			PrintWriter printWriter = new PrintWriter(new FileWriter("./src/main/resources/monitorization.yml"));
			printWriter.print(
						"ComputerMonitorization:\n"
					+ "  andaluciaId: \""+computerMonitorizationYml.get("andaluciaId")+"\"\n"
					+ "  computerNumber: \""+computerMonitorizationYml.get("computerNumber")+"\"\n"
					+ "  serialNumber: \"sn12345577\"");
			printWriter.flush();
			printWriter.close();
		}
	}

	/**
	 * Method updateSerialNumber
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 * @param computerMonitorizationYml
	 */
	private void updateSerialNumber(List<Status> statusList, String serialNumber, Actions actionsToDo,
			Map<String, String> computerMonitorizationYml)
	{
		if (actionsToDo.getUpdateSerialNumber() != null && !actionsToDo.getUpdateSerialNumber().isEmpty())
		{
			try
			{
				log.info("UPDATE SERIAL NUMBER TO - " + actionsToDo.getUpdateSerialNumber());
				if(computerMonitorizationYml!=null) 
				{
					computerMonitorizationYml.put("serialNumber", actionsToDo.getUpdateSerialNumber());
				}
				Status status = new Status("Update serialNumber" + serialNumber, true, null);
				statusList.add(status);
			}
			catch (Exception exception)
			{
				Status status = new Status("Update serialNumber " + serialNumber, false,
						new ComputerError(024, "error Update serialNumber ", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method updateComputerNumber
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 * @param computerMonitorizationYml
	 */
	private void updateComputerNumber(List<Status> statusList, String serialNumber, Actions actionsToDo,
			Map<String, String> computerMonitorizationYml)
	{
		if (actionsToDo.getUpdateComputerNumber() != null && !actionsToDo.getUpdateComputerNumber().isEmpty())
		{
			try
			{
				log.info("UPDATE COMPUTER NUMBER TO - " + actionsToDo.getUpdateComputerNumber());
				if(computerMonitorizationYml!=null) 
				{
					computerMonitorizationYml.put("computerNumber", actionsToDo.getUpdateComputerNumber());
				}
				Status status = new Status("Update computerNumber" + serialNumber, true, null);
				statusList.add(status);
			}
			catch (Exception exception)
			{
				Status status = new Status("Update computerNumber " + serialNumber, false,
						new ComputerError(023, "error Update computerNumber ", null));
				statusList.add(status);
			}
		}
	}

	/**
	 * Method updateAndaluciaId
	 * @param statusList
	 * @param serialNumber
	 * @param actionsToDo
	 * @param computerMonitorizationYml
	 */
	private void updateAndaluciaId(List<Status> statusList, String serialNumber, Actions actionsToDo,
			Map<String, String> computerMonitorizationYml)
	{
		if (actionsToDo.getUpdateAndaluciaId() != null && !actionsToDo.getUpdateAndaluciaId().isEmpty())
		{
			try
			{
				log.info("UPDATE ANDALUCIA ID TO - " + actionsToDo.getUpdateAndaluciaId());
				if(computerMonitorizationYml!=null) 
				{
					computerMonitorizationYml.put("andaluciaId", actionsToDo.getUpdateAndaluciaId());
				}
				Status status = new Status("Update andaluciaId" + serialNumber, true, null);
				statusList.add(status);
			}
			catch (Exception exception)
			{
				Status status = new Status("Update andaluciaId " + serialNumber, false,
						new ComputerError(022, "error Update andaluciaId ", null));
				statusList.add(status);
			}
			
		}
	}
	
	/**
	 * this method make a screenshot and send it 
	 * @throws ReaktorClientException
	 */
	@Scheduled(fixedDelayString = "6000", initialDelay = 2000)
	public void getAndSendScreenshot() throws ReaktorClientException
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
					BufferedImage image = new Robot()
							.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

					ImageIO.write(image, "PNG", new File("./screen.png")); // your image will be saved at this path

				}
				catch (Exception exception)
				{
					String error = "Error making the screenshot";
					log.error(error, exception);
					throw new ReaktorClientException(exception);
				}
			}
		}
		catch (ClientProtocolException exception)
		{
			String error = "Client protocol error";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (IOException exception)
		{
			String error = "Error In Out Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		finally
		{
			closeHttpClientResponse(httpClient, response);
		}
	 
	}
	
	/**
	 * this method actualice your computer info
	 * @throws ReaktorClientException
	 */
	@Scheduled(fixedDelayString = "6000", initialDelay = 2000)
	public void getAndChangeComputerInfo() throws ReaktorClientException
	{
		/*
		// fake computer info
		Computer thisComputerInfo = new Computer("sn123", "and123", "cn123", "windows", "paco", new Location("0.5", 0, "trolley1"),
				new ArrayList<>(), new ArrayList<>(), new CommandLine(),
				new MonitorizationLog());
		
		String serialNumber = "sn123";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		httpClient = HttpClients.createDefault();

		HttpGet request = new HttpGet("http://localhost:8084/computers/get/status");
		request.setHeader("serialNumber", serialNumber);

		try
		{
			response = httpClient.execute(request);
			String responseString = EntityUtils.toString(response.getEntity());
			log.info("Objeto sel servidor:"+responseString);
			
			ObjectMapper objectMapper = new ObjectMapper();
			Computer serverComputer = objectMapper.readValue(responseString, Computer.class);
		
			if(thisComputerInfo.equals(serverComputer))
			{
				log.info("No computer status updates");
			}
			else
			{
				thisComputerInfo = serverComputer;
				log.info("The computer status was update");
			}
			
			
		}
		catch (ClientProtocolException exception)
		{
			String error = "Client protocol error";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		catch (IOException exception)
		{
			String error = "Error In Out Exception";
			log.error(error, exception);
			throw new ReaktorClientException(exception);
		}
		finally
		{
			closeHttpClientResponse(httpClient, response);
		}
*/
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
			}
			catch (Exception exception)
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
			}
			catch (Exception exception)
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
			}
			catch (Exception exception)
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
			}
			catch (Exception exception)
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

			}
			catch (Exception exception)
			{
				Status status = new Status("Shutdown computer " + serialNumber, false,
						new ComputerError(001, "error on Shutdown computer ", null));
				statusList.add(status);
			}
		}
	}
}
