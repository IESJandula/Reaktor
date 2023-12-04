package es.reaktor.models;

import lombok.Data;

/**
 * @author Juan Sutil Mesa
 */
@Data
public class Status
{

	private String statusInfo;
	
	private boolean status;
	
	private ComputerError error;
	
}
