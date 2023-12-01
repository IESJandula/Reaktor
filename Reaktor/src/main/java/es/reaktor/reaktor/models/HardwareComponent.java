package es.reaktor.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Pablo Ruiz Canovas
 */
@Data
@AllArgsConstructor
public class HardwareComponent 
{
	/**Computer component type, Name, Internal, External... */
	private String component;
	/**Component cuantity */
	private Integer cuantity;
	/**
	 * Default constructor to create a empty object
	 */
	public HardwareComponent ()
	{
		//Public constructor
	}
}
