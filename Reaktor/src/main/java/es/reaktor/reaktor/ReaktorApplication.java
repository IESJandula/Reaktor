package es.reaktor.reaktor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "es.reaktor.models")
public class ReaktorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ReaktorApplication.class, args);
    }
}
