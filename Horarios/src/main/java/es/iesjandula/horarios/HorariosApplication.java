package es.iesjandula.horarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "es.iesjandula.horarios")
public class HorariosApplication {
	/**
	 * Metodo principal de arranque del proyecto
	 * @param args
	 */
	public static void main(String[] args) 
	{
		SpringApplication.run(HorariosApplication.class, args);
	}

}
