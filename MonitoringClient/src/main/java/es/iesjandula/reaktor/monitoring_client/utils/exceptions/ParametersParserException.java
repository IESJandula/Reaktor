package es.iesjandula.reaktor.monitoring_client.utils.exceptions;
/**
 * This class throws a exception for ParameterParser Class
 * @author Pablo Ruiz Canovas
 *
 */
public class ParametersParserException extends Exception{
	/**
	 * Default constructor to define the exception
	 * @param errorMsg
	 * @param exception
	 */
	public ParametersParserException(String errorMsg,Exception exception) 
	{
		super(errorMsg,exception);	
	}
	
	
}
