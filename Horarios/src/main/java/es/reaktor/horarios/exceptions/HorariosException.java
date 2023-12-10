package es.reaktor.horarios.exceptions;

import java.util.HashMap;
import java.util.Map;

import es.reaktor.horarios.models.Actividad;
import es.reaktor.horarios.models.GruposActividad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author David Martinez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorariosException extends Exception
{
	/** Attribute code */
	private int code;

	/** Attribute text */
	private String text;

	/**
	 * Constructor for create new HorariosException
	 * 
	 * @param code
	 * @param text
	 * @param exception
	 */
	public HorariosException(int code, String text, Exception exception)
	{
		super(text, exception);
		this.code = code;
		this.text = text;
	}

	/**
	 * Method toMap exception to map
	 * 
	 * @return Map<String,String> map
	 */
	public Map<String, String> toMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(code + "", code + "");
		map.put(text, text);
		return map;
	}
}
