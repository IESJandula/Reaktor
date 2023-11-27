package es.reaktor.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Miguel
 */

@Data
@AllArgsConstructor
public class Location
{
	/**The classroom location*/
	private String classroom;
	
	/**The number of the plant*/
    private int plant;
    
    /**The trolley*/
    private String trolley;
	
    /**The empty constructor of the Location Class*/
    public Location()
		{
		}    
}
