package es.iesjandula.Horarios.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Cazalla Pérez
 */
public class HorarioError extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2541052055859134058L;

	/** Attribute - Code */
	private int code;

	/** Attribute - Message */
	private String message;

	/**
	 * 
	 * @param code    with the code
	 * @param message with the message
	 */
	public HorarioError(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>();

		mapBodyException.put("code", this.code);
		mapBodyException.put("message", this.message);

		return mapBodyException;
	}
}
