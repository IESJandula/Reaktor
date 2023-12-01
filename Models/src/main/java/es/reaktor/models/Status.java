package es.reaktor.models;

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
     * This attributes have theerror
     */
	private ComputerError error;
}
