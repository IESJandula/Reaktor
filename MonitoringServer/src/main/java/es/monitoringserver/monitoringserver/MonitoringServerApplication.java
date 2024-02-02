package es.monitoringserver.monitoringserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "es.monitoringserver.models")
public class MonitoringServerApplication
{
    public static void main(String[] args)
    {
    	// -- NECESARY FOR ENABLE TAKE SCREENSHOTS FROM CLIENTS (ROBOT AWT) ---
    	System.setProperty("java.awt.headless", "false");
        SpringApplication.run(MonitoringServerApplication.class, args);
    }
}
