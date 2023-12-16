package es.reaktor.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentRam extends HardwareComponent 
{
	private int capacity;
	
}
