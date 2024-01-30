package es.reaktor.horarios.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author David Martinez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class HorariosError extends Exception
{
	/** Attribute serialVersionUID*/
	private static final long serialVersionUID = 2937203647694023448L;

	/** Attribute code */
	private int code;

	/** Attribute text */
	private String text;
	
	/** Attribute exception*/
	private Exception exception;

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
		if(exception!=null) 
		{
			ObjectMapper mapper = new ObjectMapper();
			try
			{
				map.put("exception",mapper.writeValueAsString(exception));
			}
			catch (JsonProcessingException exception)
			{
				String error = "Error generating execption mapper";
				log.error(error);
			}
		}
		return map;
	}
}
