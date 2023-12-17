package es.reaktor.reaktorclient.scheduled_task;

import es.reaktor.models.Action;
import es.reaktor.reaktorclient.utils.HttpCommunicationSender;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import es.reaktor.reaktorclient.windows.WindowsMotherboard;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Manuel Martín Murillo
 *
 */
@Component
@Slf4j
public class ComputerMonitorization
{
	final static Logger logger = LogManager.getLogger();
    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    public void computerOnReport()
	{
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try
		{
			httpClient = HttpClients.createDefault();

			String url = reaktorServerUrl + "/computers/get/status";

			HttpGet request = new HttpGet(url);

			request.addHeader("serialNumber", "003");

			response = httpClient.execute(request);
			Action action = new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), Action.class);
			if (action.getActionName() != null)
			{
				switch (action.getActionName())
				{
				case "configuracion" -> ConfiguracionWifi();
				default -> System.out.println("No existe esta accion");
				}
			}

		} 
		catch (ClientProtocolException e)
		{
			log.warn(e.getMessage());
			log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, e);
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			log.warn(e.getMessage());
			log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, e);
			e.printStackTrace();
		} 
		finally
		{
			try 
			{
				response.close();
			} 
			catch (IOException ioException) 
			{
				ioException.printStackTrace();
			}
			try 
			{
				httpClient.close();
			} 
			catch (IOException ioException) 
			{
				ioException.printStackTrace();
			}
		}
	}
    
    public void ConfiguracionWifi() 
    {    	
		try
		{
			log.info("iniciamos la cmd");
			Process proceso = new ProcessBuilder("cmd.exe","netsh wlan connect andared").start();		
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally
		{

		}	
    }
}

