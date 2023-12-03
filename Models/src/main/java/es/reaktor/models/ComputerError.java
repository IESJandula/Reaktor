package es.reaktor.models;

import java.util.HashMap;
import java.util.Map;

public class ComputerError extends Exception
{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2430243519965740823L ;
	
	/** Attribute - Code */
	private int code ;
	
	/** Attribute - Message */
	private String message ;

	/**
	 * @param code	  with the code
	 * @param message with the message
	 */
	public ComputerError(int code, String message)
	{
		this.code    = code ;
		this.message = message ;
	}

	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("code", this.code) ;
		mapBodyException.put("message", this.message) ;
		
		return mapBodyException ;
	}

}



