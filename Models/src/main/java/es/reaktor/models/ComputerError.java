package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputerError {

	/**
     * - ATTRIBUTES -
     * This attributes have the code
     */
	private int code;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the text
     */
	private String text;
}
