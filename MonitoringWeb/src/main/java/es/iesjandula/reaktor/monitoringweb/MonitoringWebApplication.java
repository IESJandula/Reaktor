package es.iesjandula.reaktor.monitoringweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class MonitoringWebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitoringWebApplication.class, args);
    }
}
