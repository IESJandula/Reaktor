package es.iesjandula.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Peripheral extends HardwareComponent
{
	/**
	 * - ATTRIBUTES - This attributes say if a peripheral is open
	 */
	private boolean isOpen;
	
	/**
	 * Constructor for create new Peripheral
	 * @param component
	 * @param cuantity
	 * @param isOpen
	 */
	public Peripheral(String component, int cuantity, boolean isOpen)
	{
		super(component, cuantity);
		this.isOpen = isOpen;
	}

	/**
	 * Method equals custom configuration
	 * @author David Martinez
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof HardwareComponent) 
		{
			if(super.getComponent().equals(((HardwareComponent)obj).getComponent())) 
			{
				return true;
			}
		}
		return false;
	}


}
