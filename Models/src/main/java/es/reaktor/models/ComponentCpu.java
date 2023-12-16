package es.reaktor.models;

import lombok.Data;

@Data
public class ComponentCpu extends HardwareComponent 
{
	private int cores;

	
	/**
	 * 
	 */
	public ComponentCpu() 
	{
		super();
	}



	/**
	 * 
	 * @param cores
	 */
	public ComponentCpu(int cores) 
	{
		super();
		this.cores = cores;
	}


	/**
	 * 
	 * @return cores
	 */
	public int getCores() 
	{
		return cores;
	}
	

	/**
	 * 
	 * @param cores
	 */
	public void setCores(int cores) 
	{
		this.cores = cores;
	}
	
	
	
}
