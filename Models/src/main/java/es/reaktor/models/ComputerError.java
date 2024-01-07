package es.reaktor.models;

import java.util.HashMap;
import java.util.Map;

public class ComputerError extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1926786439465935521L;

	private int code;

	private String text;

	public ComputerError(int code, String text, Exception exception)
	{
		super(text, exception);
		this.code = code;
		this.text = text;
	}

	public Map<String, String> toMap()
	{
		Map<String, String> mapException = new HashMap<String, String>();
		mapException.put("code", String.valueOf(this.code));
		mapException.put("text", this.text);
		return mapException;
	}

}