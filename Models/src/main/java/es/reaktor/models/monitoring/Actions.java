package es.reaktor.models.monitoring;

import java.util.List;
import es.reaktor.models.Peripheral;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author David Martinez
 *
 */
/**
 * @author David Martinez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actions
{
	/** Attribute shutdown*/
	private boolean shutdown;
	
	/** Attribute restart*/
	private boolean restart;
	
	/** Attribute commands*/
	private List<String> commands;
	
	/** Attribute blockDispositives*/
	private List<Peripheral> blockDispositives;
	
	/** Attribute openWebs*/
	private List<String> openWebs;
	
	// private List<String> installApps;
	
	// private List<String> unistallApps;
	
	// private File configurationWifi;
	
	/** Attribute updateAndaluciaId*/
	private String updateAndaluciaId;
	
	/** Attribute updateSerialNumber*/
	private String updateSerialNumber;
	
	/** Attribute updateComputerNumber*/
	private String updateComputerNumber;
}
