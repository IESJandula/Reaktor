package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Peripheral extends HardwareComponent
{
	/**
     * - ATTRIBUTES -
     * This attributes say if a peripheral is open
     */
	private boolean isOpen;
}
