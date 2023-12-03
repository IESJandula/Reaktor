package es.reaktor.models;

public class Peripheral extends HardwareComponent
{
	/** Attribute is open*/
	private Boolean isOpen;

	public Peripheral (String component, int cuantity, boolean isOpen) {
		super(component, cuantity);
		this.isOpen = isOpen;
	}

	/**
	 * @return the isOpen
	 */
	public Boolean getIsOpen()
	{
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setIsOpen(Boolean isOpen)
	{
		this.isOpen = isOpen;
	}
}
