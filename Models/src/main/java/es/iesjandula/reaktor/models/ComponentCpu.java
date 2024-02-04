package es.iesjandula.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentCpu extends HardwareComponent{

	/**
     * - ATTRIBUTES -
     * This attributes have the cores
     */
	private int cores;
}
