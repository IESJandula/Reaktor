package es.iesjandula.horarios.rest;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.csv.ModelCSV;
import es.iesjandula.horarios.utils.ICSVParser;
import es.iesjandula.horarios.utils.IChecker;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 *
 */
@RestController
@RequestMapping( value = "/horarios",produces = "application/json")
public class RestHandlerHorarios implements ICSVParser,IChecker
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	/**Modelos del csv para guardarlos en sesion */
	private List <ModelCSV> modelos;
	/**
	 * Constructor por defecto
	 */
	public RestHandlerHorarios() 
	{
		//public constructor
		this.modelos = new LinkedList<ModelCSV>();
	}
	/**
	 * Endpoint que recibe un fichero csv y lo parsea internamente en sesion
	 * @param csvFile fichero csv que recibe el servidor
	 * @return ok si el fichero cumple las condiciones,404 si el formato o la estructura esta mal o 500 si hubo un error interno
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/send/csv" ,consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvTo(@RequestBody(required = true) final MultipartFile csvFile)
	{
		try
		{
			//Se comprueba que el fichero sea csv y cumpla con la estructura establecida
			this.checkCSVFile(csvFile);
			//Parseamos el fichero y guardamos los datos
			this.modelos = this.parseCSV();
			return ResponseEntity.ok().body("Fichero recibido con exito");
		}
		catch(HorarioError ex)
		{
			log.error("Error a la hora de parsear el fichero",ex);
			return ResponseEntity.status(415).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error interno de servidor",ex);
			return ResponseEntity.status(500).body("Error interno de servidor");
		}
	}
	/**
	 * Endpoint que recibe un email y busca a partir de el la lista de roles de la cuenta asociada
	 * @param email de la cuenta a buscar 
	 * @return ok si encontro los roles, 404 si el email no es valido o 500 si hubo un error interno
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/get/roles")
	public ResponseEntity<?> getRoles(@RequestHeader(value = "email",required = true) final String email)
	{
		try
		{
			//Comprobacion del email y si existe en los modelos
			this.checkParam(email, this.modelos);
			//Obtencion de los roles
			String [] roles = this.searchRol(email, modelos);
			return ResponseEntity.ok().body(roles);
		}
		catch(HorarioError ex)
		{
			log.error("Error en el email o al buscar el modelo",ex);
			return ResponseEntity.status(404).body(ex.getBodyMessageException());
		}
		catch(Exception ex)
		{
			log.error("Error interno de servidor",ex);
			return ResponseEntity.status(404).body("Error de servidor");
		}
	}
		
}