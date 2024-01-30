package es.monitoringserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentRam extends HardwareComponent
{
	/**
     * - ATTRIBUTES -
     * This attributes have the  ram component
     */
	private int capacity;
}
