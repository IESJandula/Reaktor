package es.iesjandula.horarios;

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
@EntityScan(basePackages = "es.reaktor.horarios")
public class HorariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HorariosApplication.class, args);
	}

}
