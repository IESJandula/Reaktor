package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan Sutil Mesa
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Location
{
	
	private String classroom;
	
	private int plant;
	
	private String trolley;
	
}
