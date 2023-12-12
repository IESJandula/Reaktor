package com.microservice.carritos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EntityScan(basePackages = "es.reaktor.models.carritos")
public class MicroserviceCarritosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCarritosApplication.class, args);
	}

	/**
	 * Run encargado de hacer funcionar la base de datos
	 * @param args argumentos
	 * @throws Exception excepcion general
	 */
	@Transactional(readOnly = false)
	public void run(String... args) throws Exception {

	}

}
