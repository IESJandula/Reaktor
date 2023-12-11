package es.reaktor.reaktorclient.scheduled_task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	@Scheduled(fixedDelayString = "5000", initialDelay = 2000)
	final static Logger logger = LogManager.getLogger();
	public void sendFullComputerTask()
	{
		// THE COMPUTER FAKE FULL INFO STATUS
		Computer computerInfoMob = new Computer("sn1234", "and123", "cn123", "windows", "paco");
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
