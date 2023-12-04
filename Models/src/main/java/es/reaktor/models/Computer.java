package es.reaktor.models;

import java.util.List;

import lombok.Data;

/**
 * @author Juan Sutil Mesa
 */
@Data
public class Computer
{

	private String serialNumber;
	private String andaluciaId;
	private String computerNumber;
	private String operativeSystem;
	private String professor;
	private Location location;
	private List<HardwareComponent> hardwareList;
	private List<Software> softwareList;
	private CommandLine commandLine;
	private MonitorizationLog monitorizationLog;
	
}
