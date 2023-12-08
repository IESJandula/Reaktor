package es.reaktor.reaktorclient.scheduled_task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.reaktor.models.CommandLine;
import es.reaktor.models.Computer;
import es.reaktor.models.HardwareComponent;
import es.reaktor.models.Location;
import es.reaktor.models.MonitorizationLog;
import es.reaktor.models.Software;
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
	 * Method computerOnReport SCHELUDED TASK
	 * 
	 * @throws ReaktorClientException
	 */
	@Scheduled(fixedDelayString = "5000", initialDelay = 2000)
	public void computerOnReport() throws ReaktorClientException
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

	}
}
