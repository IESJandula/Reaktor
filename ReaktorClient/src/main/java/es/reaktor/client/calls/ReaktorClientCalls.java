package es.reaktor.client.calls;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ReaktorClientCalls 
{
	final static Logger logger = LogManager.getLogger();
	public static void main(String[] args) 
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String serialNumber = "12";
		String url = "https://localhost:8084/computers/get/status";
		HttpGet request = new HttpGet(url);
		request.addHeader("serialNumber", serialNumber);

		CloseableHttpResponse response = null;
		try 
		{
			response = httpClient.execute(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			System.out.println("Respuesta a: " + responseBody);

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
