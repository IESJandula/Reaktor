package es.iesjandula.reaktor.models;

import es.iesjandula.reaktor.exceptions.ComputerError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {

	/**
     * - ATTRIBUTES -
     * This attributes have the statusInfo
     */
	private String statusInfo;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the status
     */
	private Boolean status;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the error
     */
	private ComputerError error;
}
