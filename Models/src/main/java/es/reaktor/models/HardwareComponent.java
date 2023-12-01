package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareComponent {

	/**
     * - ATTRIBUTES -
     * This attributes have the component
     */
	private String component;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the cuantity
     */
	private int cuantity;
}
