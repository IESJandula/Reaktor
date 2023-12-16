package es.reaktor.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentHardDisk extends HardwareComponent
{

	private String diskType;
	
	private int capacity;

	
}