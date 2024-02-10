package es.acceso.administracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EntityScan(basePackages = "es.acceso.administracion")
public class AdiministracionApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(AdiministracionApplication.class, args);
	}

	@Transactional(readOnly = false)
	public void run(String... args) throws Exception
	{
		
	}

}
