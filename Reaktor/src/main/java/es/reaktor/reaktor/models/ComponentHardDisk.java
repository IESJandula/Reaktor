package es.reaktor.reaktor.models;

import lombok.Data;

/**
 * @author Pablo Ruiz Canovas
 */
@Data
public class ComponentHardDisk extends HardwareComponent 
{
	/**Hard disk type HDD,SSD... */
	private String diskType;
	/**Component HardDisk capacity */
	private Integer capacity;
	
	/**
	 * Default constructor to create a empty object
	 */
	public ComponentHardDisk()
	{
		//Public constructor
	}
	/**
	 * Complete constructor to create a hard disk object
	 * @param component
	 * @param cuantity
	 * @param diskType
	 * @param capacity
	 */
	public ComponentHardDisk(String component, Integer cuantity, String diskType, Integer capacity) 
	{
		super(component, cuantity);
		this.diskType = diskType;
		this.capacity = capacity;
	}
	
	
}
