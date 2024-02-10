package es.acceso.adiministracion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "es.acceso.adiministracion")
public class AdiministracionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdiministracionApplication.class, args);
	}

}
