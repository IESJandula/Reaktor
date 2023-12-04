package es.reaktor.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Peripheral extends HardwareComponent
{
	/** Attribute is open*/
	private Boolean isOpen;

}
