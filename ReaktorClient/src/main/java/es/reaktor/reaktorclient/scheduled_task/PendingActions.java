package es.reaktor.reaktorclient.scheduled_task;

import es.reaktor.models.Action;
import es.reaktor.models.ComputerError;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class PendingActions
{

	@Value("${reaktor.server.url}")
	private String reaktorServerUrl;

	@Scheduled(fixedDelayString = "${reaktor.pendingActions}", initialDelay = 2000)
	public void computerOnReport()
	{
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try
		{
			httpClient = HttpClients.createDefault();

			String url = reaktorServerUrl + "/computers/get/status";

			HttpGet request = new HttpGet(url);

			request.addHeader("serialNumber", "002");

			response = httpClient.execute(request);
			Action action = new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), Action.class);
			if (action.getActionName() != null)
			{
				switch (action.getActionName())
				{
				case "uninstall" -> uninstallApp(action.getInfo());
				case "openWeb" -> openWeb(action.getInfo());
				default -> System.out.println("Esto esta sin implementar makina");
				}
			}

		} catch (ClientProtocolException e)
		{
			log.warn(e.getMessage());
			e.printStackTrace();
		} catch (IOException e)
		{
			log.warn(e.getMessage());
			e.printStackTrace();
		} catch (ComputerError e)
		{
			log.warn(e.getMessage());
			e.printStackTrace();
		} finally
		{
			this.close(httpClient, response);
		}
	}
	
	private void close(CloseableHttpClient httpClient, CloseableHttpResponse response)
	{
		try
		{
			response.close();
		} catch (IOException e)
		{
			log.warn(e.getMessage());
			log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, e);
			e.printStackTrace();
		}
		try
		{
			httpClient.close();
		} catch (IOException e)
		{
			log.warn(e.getMessage());
			log.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, e);
			e.printStackTrace();
		}
	}
	
	private void openWeb(String url) throws ComputerError {
		
		try
		{
			Process proceso = new ProcessBuilder("cmd.exe", "/c", "start " + url).start();
			
			proceso.waitFor();
			
		} catch (IOException e)
		{
			log.warn(e.getMessage());
			e.printStackTrace();
			throw new ComputerError(1, url, e);
		} catch (InterruptedException e)
		{
			log.warn(e.getMessage());
			e.printStackTrace();
			throw new ComputerError(2, url, e);
		}
		
	}
	
	private void configWifi(String nombreRed) throws ComputerError
	{
		try {
			Process processBuilder = new ProcessBuilder("netsh", "wlan", "connect", "name=" + nombreRed).start();
			processBuilder.waitFor();
		} catch (IOException e) {
			String error="El comando introducido tiene problemas de sintaxis :"+e.getMessage();
			log.warn(error);
			e.printStackTrace();
			throw new ComputerError(1,error, e);
		} catch (InterruptedException e) {
			String error="Ha ocurrido un error al ejecutar el comando en este pc : "+e.getMessage();
			log.warn(e.getMessage());
			e.printStackTrace();
			throw new ComputerError(1, error, e);
		}

	}
	
	private void uninstallApp(String appName)
	{

		Scanner scanner = null;
		try
		{
			log.info("inicio busqueda chrome");
			Process proceso = new ProcessBuilder("cmd.exe", "/c", "dir /s /b C:\\" + appName + " > resultados.txt")
					.start();
			
			int resultado = proceso.waitFor();
			log.info("fin busqueda chrome");
			if (resultado == 0)
			{
				
				scanner = new Scanner(new File("resultados.txt"));

				String path = scanner.nextLine();
				log.info("inicio borrado resultado");
				proceso = new ProcessBuilder("cmd.exe", "/c", "del resultados.txt")
						.start();
				resultado = proceso.waitFor();
				log.info("inicio desinstalacion");
				proceso = new ProcessBuilder("cmd.exe", "/c", "\"" + path + "\" --uninstall --force-uninstall")
						.start();

				resultado = proceso.waitFor();
				log.info("fin desinstalacion");
			} else
			{
				System.out.println("Error en la desinstalación. Código de salida: " + resultado);
			}
		} catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		} finally
		{
			if (scanner != null)
			{
				scanner.close();
			}
		}
	}
}