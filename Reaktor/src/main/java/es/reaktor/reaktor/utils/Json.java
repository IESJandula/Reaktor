package es.reaktor.reaktor.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json
{
	/**Attribute - mapper*/
	private static ObjectMapper mapper;
	
	/**
	 * @return a new instance of ObjectMapper
	 */
	public static ObjectMapper mapper()
	{
		if(Json.mapper == null) 
		{
			Json.mapper = Json.createJson();
		}
		
		return Json.mapper;
	}
	
	/**
	 * @return the ObjectMapper
	 */
	private static ObjectMapper createJson() 
	{
		final ObjectMapper mapper = new ObjectMapper();
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		return mapper;
	}
}
