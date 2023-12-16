package es.reaktor.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Juan Sutil Mesa
 */
@Data
@NoArgsConstructor
public class Status
{

	private String statusInfo;
	
	private boolean status;
	
	private ComputerError error;
	
}
