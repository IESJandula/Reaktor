package es.reaktor.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author Pablo Ruiz Canovas
 *
 */
@Data
@AllArgsConstructor
public class Status 
{
	/**Specific pc status, reboot, shut down, install, ... */
	private String info;
	
	/**Status is done or not */
	private boolean done;
	
	/**
	 * Default constructor
	 */
	public Status()
	{
		//Public constructor
	}
}
