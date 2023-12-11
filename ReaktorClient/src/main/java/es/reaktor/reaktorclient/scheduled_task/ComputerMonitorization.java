package es.reaktor.reaktorclient.scheduled_task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.reaktor.models.Computer;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Manuel Martín Murillo
 *
 */
@Slf4j
@Component
public class ComputerMonitorization 
{
	/**
	 * Method sendFullComputerTask scheduled task
	 * 
	 */
	final static Logger logger = LogManager.getLogger();
	
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
			logger.info(responseString);

			Actions actionsToDo = new ObjectMapper().readValue(responseString, Actions.class);

			this.actionsCommands(statusList, serialNumber, actionsToDo);
		
			logger.info(statusList.toString());

		}
	
		catch(ClientProtocolException clientProtocolException)
		{
			String error ="Error de cliente protocal Exception";
			logger.error(error, clientProtocolException);
		}
		catch (IOException ioException) 
		{
			String error ="Error de entrada salida";
			logger.error(error, ioException);
			
		}
		finally 
		{
			try 
			{
				response.close();
				
			} 
			catch (IOException ioException) 
			{
				String error ="Error de entrada salida";
				logger.error(error, ioException);
			}
			try 
			{
				httpClient.close();
			} 
			catch (IOException ioException) 
			{
				
				String error ="Error de entrada salida";
				logger.error(error, ioException);
			}
		}
	}

}
