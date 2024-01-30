package es.monitoringserver.timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author David Martinez
 *
 */
@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "es.monitoringserver.timetable")
public class TimetableApplication
{
	/**
	 * Method main to run spring app
	 * @param args main arguments
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(TimetableApplication.class, args);
	}

}
