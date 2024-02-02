package es.monitoringserver.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author David Martinez
 *
 */
public class ComputerError extends Exception
{
	/** Attribute serialVersionUID*/
	private static final long serialVersionUID = -628975777962427933L;

	/**
	 * - ATTRIBUTES - This attributes have the code
	 */
	private int code;

	/**
	 * - ATTRIBUTES - This attributes have the text
	 */
	private String text;

	
	/**
	 * Constructor for create new ComputerError
	 * @param code the code
	 * @param text the text
	 * @param exception for the possible exception
	 */
	public ComputerError(int code, String text,Exception exception) 
	{
		super(text,exception);
		this.code=code;
		this.text=text;
	}
	
	/**
	 * Method toMap
	 * @return Map<String,String> mapException
	 */
	public Map<String,String> toMap()
	{
		Map<String,String> mapException = new HashMap<String,String>();
		
		mapException.put("code", String.valueOf(this.code));
		mapException.put("text", this.text);
		
		return mapException;
	}

}
