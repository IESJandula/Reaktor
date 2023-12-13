package es.iesjandula.horarios.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.utils.ParserXml;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 *
 */
@RestController
@RequestMapping( value = "/horarios",produces = "application/json")
public class RestHandlerHorarios implements ParserXml 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();

	/**
	 * Constructor por defecto
	 */
	public RestHandlerHorarios() 
	{	
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = "multipart/form-data")
    public ResponseEntity<?> parseXML(
            @RequestPart MultipartFile xml)
    {
		try
		{
			if(chekIfEmptyAndXml(xml))
			{
				return ResponseEntity.ok(this.parserFileToObject(xml).getDatos());
	    	}
			else
			{
				throw new HorarioError(1,"El fichero tiene que ser xml");
			}
		}
		catch (HorarioError exception)
		{
			String error = "Is not a xml or is empty";
			HorarioError horarioError = new HorarioError(404, error);
			return ResponseEntity.status(404).body(horarioError.getBodyMessageException());
		}
        catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }

	/**
	 * @param xml
	 * @return
	 */
	private boolean chekIfEmptyAndXml(MultipartFile xml)
	{
		if(!xml.isEmpty())
		{
			
				return true;
			
		}
		return false;
	}
}
