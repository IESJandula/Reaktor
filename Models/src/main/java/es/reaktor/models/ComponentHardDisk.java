package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentHardDisk extends HardwareComponent {

	/**
     * - ATTRIBUTES -
     * This attributes have the diskType
     */
	private String diskType;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the capacity
     */
	private int capacity;
	
}
