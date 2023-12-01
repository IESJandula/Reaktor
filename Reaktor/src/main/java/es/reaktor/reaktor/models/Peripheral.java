package es.reaktor.reaktor.models;
import lombok.Data;
/**
 * @author Pablo Ruiz Canovas
 */
@Data
public class Peripheral extends HardwareComponent 
{
	/**Computer peripheral component status */
	private Boolean isOpen;
	
	/**
	 * Default constructor to create a empty object
	 */
	public Peripheral()
	{
		//Public constructor
	}
	/**
	 * Complete constructor that creates a peripheral component
	 * @param component
	 * @param cuantity
	 * @param isOpen
	 */
	public Peripheral(String component,Integer cuantity,Boolean isOpen)
	{
		super(component,cuantity);
		this.isOpen = isOpen;
	}
}
