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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actions
{
	private boolean shutdown;
	
	private boolean restart;
	
	private List<String> commands;
	
	private List<Peripheral> blockDispositives;
	
	private List<String> openWebs;
	
	// private List<String> installApps;
	
	// private List<String> unistallApps;
	
	// private File configurationWifi;
	
	private String updateAndaluciaId;
	
	private String updateSerialNumber;
	
	private String updateComputerNumber;
}
