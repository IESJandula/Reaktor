package es.reaktor.models;

import lombok.Data;

@Data
public class SoftwareInstance 
{
	/**
	 * Attribute
	 */
	private String application;

	public SoftwareInstance(String application) {
		super();
		this.application = application;
	}
	
	
	
}
