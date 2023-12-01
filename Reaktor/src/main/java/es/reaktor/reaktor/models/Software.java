package es.reaktor.reaktor.models;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author Pablo Ruiz Canovas
 */
@Data
@AllArgsConstructor
public class Software 
{
	/**Computer application or software */
	private String application;
	/**
	 * Default constructor to create a empty object
	 */
	public Software()
	{
		//Public constructor
	}
	
}
