package es.iesjandula.reaktor.monitoring_client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.iesjandula.reaktor.monitoring_client.models.Reaktor;
import es.iesjandula.reaktor.monitoring_client.utils.ActionsArguments;
import es.iesjandula.reaktor.monitoring_client.utils.Constants;
import es.iesjandula.reaktor.monitoring_client.utils.HttpCommunicationSender;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ReaktorClientException;

/**
 * - CLASS -
 * This class is encharged of run the applications Client
 */
@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "es.iesjandula.reaktor.models")
@Slf4j
public class MonitoringClientApplication implements CommandLineRunner
{

    /**
     * - Attribute -
     * this class is used to get the information of the Computer
     */
    @Autowired
    private Reaktor reaktor;


    /**
     * - Attribute -
     * This class is used to manage communications with the server.
     */
    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    /**
     * - Attribute -
     * This attrubte Storage the information about of Server URL
     */
    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    /**
     * - Attribute -
     * this class is used to execute the arguments of the application
     */
    @Autowired
    private ActionsArguments actionsArguments;

    /**
     * Method main
     * @param args the arguments
     * @throws InterruptedException
     */
    public static void main(String[] args)
    {
    	// -- NECESARY FOR ENABLE TAKE SCREENSHOTS FROM CLIENTS (ROBOT AWT) ---
    	System.setProperty("java.awt.headless", "false");

        // como actualmente no soporta linux, introduzco esta linea para que no se ejecute en linux
        if (!System.getProperty("os.name").toLowerCase().contains(Constants.OS_WINDOWS.toLowerCase()))
        {
            System.err.println("This application at the moment does not support Linux");
            System.exit(0);
        }
   
        // Start the application 
        SpringApplication.run(MonitoringClientApplication.class, args);
    }

    /**
     * Method run Start the application
     * @param args the arguments
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception
    {
    	// -- NECESARY FOR ENABLE TAKE SCREENSHOTS FROM CLIENTS (ROBOT AWT) ---
    	System.setProperty("java.awt.headless", "false");
        try
        {
            // We carry out the actions with the arguments that are passed on to us
            this.actionsArguments.writeConfiguration(args);
            
            // Send the update information to Server REAKTOR
            log.info("Sending information to server REAKTOR");
            this.httpCommunicationSender.sendPost(this.httpCommunicationSender.createHttpPostReaktor(this.reaktorServerUrl+"/reaktor", this.reaktor));
        }
        catch (ReaktorClientException reaktorClientException)
        {
            log.error("Error in the application", reaktorClientException);
        }
    }
}
