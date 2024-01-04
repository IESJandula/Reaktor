package es.reaktor.reaktorclient.scheduled_task;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.reaktor.models.Action;
import es.reaktor.models.ComputerError;
import es.reaktor.models.Status;
import es.reaktor.reaktorclient.utils.HttpCommunicationSender;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import lombok.extern.slf4j.Slf4j;

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
    
    private void actionsCfgWifiFile(List<Status> statusList, String serialNumber, Action actionsToDo)
    {
       
        if (actionsToDo.getConfiguracionWifi()!=null && !actionsToDo.getConfiguracionWifi().isBlank() && !actionsToDo.getConfiguracionWifi().isEmpty())
        {
            try
            {
                
                File cfgFile = new File(actionsToDo.getConfiguracionWifi());
                if(cfgFile.exists() && cfgFile.isFile()) 
                {
                  
                    log.info(" ADD CFG WIFI -- > cmd.exe /c "+cfgFile.getAbsolutePath());
                    Runtime.getRuntime().exec
                    (
                    "cmd.exe /c netsh wlan add profile filename="+cfgFile.getAbsolutePath()+""
                    );

                
                    Status status = new Status("ADD CFG WIFI exec " + serialNumber, true);
                    statusList.add(status);
                }
                else 
                {
                    Status status = new Status("ADD CFG WIFI Error " + serialNumber, false);
                    statusList.add(status);
                }
            }
            catch (Exception exception)
            {
                // --- ERROR ON ACTION ---
                Status status = new Status("ADD CFG WIFI Error " + serialNumber, false);
                statusList.add(status);
            }
        }
    }
}

