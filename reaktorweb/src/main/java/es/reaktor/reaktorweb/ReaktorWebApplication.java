package es.reaktor.reaktorweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ReaktorWebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ReaktorWebApplication.class, args);
    }
}
