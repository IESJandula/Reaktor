package es.reaktor.reaktor.models;
import lombok.Data;
/**
 * @author Pablo Ruiz Canovas
 */
@Data
public class ComponentCpu extends HardwareComponent 
{
	/**Component CPU cores */
	private Integer cores;
	/**
	 * Default constructor to create a empty object
	 */
	public ComponentCpu()
	{
		//Default constructor
	}
	/**
	 * Complete constructor to create a Component Cpu
	 * @param component
	 * @param cuantity
	 * @param cores
	 */
	public ComponentCpu(String component, Integer cuantity, Integer cores) 
	{
		super(component, cuantity);
		this.cores = cores;
	}
	
}
