package es.iesjandula.horarios.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 *
 */
@RestController
@RequestMapping( value = "/horarios",produces = "application/json")
public class RestHandlerHorarios 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();

	/**
	 * Constructor por defecto
	 */
	public RestHandlerHorarios() 
	{
		//public constructor
	}
	
	
}
