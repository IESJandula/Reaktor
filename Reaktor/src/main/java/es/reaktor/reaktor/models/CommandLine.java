package es.reaktor.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Pablo Ruiz Canovas
 */
@Data
@AllArgsConstructor
public class CommandLine
{
	/**List of commands */
	private String [] commands;
	/**
	 * Default constructor to create a empty object
	 */
	public CommandLine()
	{
		//Public constructor
	}
	
}
