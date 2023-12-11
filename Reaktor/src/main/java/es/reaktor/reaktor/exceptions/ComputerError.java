package es.reaktor.reaktor.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ComputerError extends Exception 
{

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -3347809497121907927L;
	
	/**Error code*/
	private int code;
	
	/**Error message */
	private String message;
	
	/**
	 * Constructor que crea una excepcion para el apartado de control de ordenadores
	 * @param code
	 * @param message
	 */
	public ComputerError(int code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	/**
	 * Metodo que devuelve los errores del cliente en un mapa para json
	 * @return
	 */
	public Map<String,Object> getBodyMessageException()
	{
		Map<String,Object> mapBodyException = new HashMap<String,Object>();
		
		mapBodyException.put("code", code);
		
		mapBodyException.put("message", message);
		
		return mapBodyException;
		
	}

}
