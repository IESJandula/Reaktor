package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	/**
     * - ATTRIBUTES -
     * This attributes have the classroom
     */
	private String classroom;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the plant
     */
	private int plant;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the trolley
     */
	private String trolley;
}
