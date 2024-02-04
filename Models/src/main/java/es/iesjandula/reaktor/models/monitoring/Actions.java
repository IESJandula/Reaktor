package es.iesjandula.reaktor.models.monitoring;

import java.io.File;
import java.util.List;

import es.iesjandula.reaktor.models.Peripheral;
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
	
	/** Attribute installApps*/
	private List<String> installApps;
	
	/** Attribute uninstallApps*/
	private List<String> uninstallApps;
	
	/** Attribute configurationWifi*/
	private String configurationWifi;
	
	/** Attribute updateAndaluciaId*/
	private String updateAndaluciaId;
	
	/** Attribute updateSerialNumber*/
	private String updateSerialNumber;
	
	/** Attribute updateComputerNumber*/
	private String updateComputerNumber;
}
