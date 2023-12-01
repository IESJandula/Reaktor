package es.reaktor.reaktor.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Miguel
 */

@Data
@AllArgsConstructor
public class Computer
{
	/**Pc Serial Number */
	private String serialNumber;
	
	/**Pc Andalucia Id*/
	private String andaluciaID;
	
	/**PC Computer Number*/
	private String computerNumber;
	
	/**PC Operative System*/
    private String operativeSystem;
    
    /**The name of the Professor*/
    private String professor;
    
    /**The Location of the Computer*/
    private Location location;
    
    /**The list of the HardwareComponent*/
    private List<HardwareComponent> hardwareList;
    
    /**The list of the Software*/
    private List<Software> softwareList;
    
    /**The CommandLine*/
    private CommandLine commandLine;
    
    /**The log of the MonitorizationLog*/
    private MonitorizationLog monitorizationLog;
   
	/**The empty constructor of the Computer Class*/
	public Computer()
	{
	}
}
