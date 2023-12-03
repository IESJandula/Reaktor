package es.reaktor.models;

public class ComponentHardDisk extends HardwareComponent
{

	private String diskType;
	
	private int capacity;

	public ComponentHardDisk (String component, int cuantity, String diskType, int capacity) {
		super(component, cuantity);
		this.diskType = diskType;
		this.capacity = capacity;
	}

	/**
	 * @return the diskType
	 */
	public String getDiskType()
	{
		return diskType;
	}

	/**
	 * @param diskType the diskType to set
	 */
	public void setDiskType(String diskType)
	{
		this.diskType = diskType;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity()
	{
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}

	
}