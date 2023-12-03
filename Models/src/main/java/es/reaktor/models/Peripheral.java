package es.reaktor.models;

import lombok.Data;

@Data
public class Peripheral extends HardwareComponent 
{
	HardwareComponent hardwareComponent;
	private boolean isOpen;
	
}
