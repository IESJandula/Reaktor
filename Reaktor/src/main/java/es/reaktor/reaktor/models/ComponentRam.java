package es.reaktor.reaktor.models;

import lombok.Data;

/**
 * @author Pablo Ruiz Canovas
 */
@Data
public class ComponentRam extends HardwareComponent 
{
	/**Component RAM capacity */
	private Integer capacity;
	/**
	 * Default constructor to create a empty object
	 */
	public ComponentRam()
	{
		//Public constructor
	}
	/**
	 * Complete constructor to create a Component RAM
	 * @param component
	 * @param cuantity
	 * @param capacity
	 */
	public ComponentRam(String component, Integer cuantity, Integer capacity) 
	{
		super(component, cuantity);
		this.capacity = capacity;
	}
}
