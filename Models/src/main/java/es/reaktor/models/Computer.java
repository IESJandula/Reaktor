package es.reaktor.models;

import lombok.Data;

@Data
public class Computer 
{
	private String serialNumber;
    private String andaluciaId;
    private String computerNumber;
    private String operativeSystem;
    private String professor;
    private Location location;
    private HardwareComponent hardwareList;
    private Software softwareList;
    private CommandLine commandLine;
    private  MonitorizationLog monitorizationLog;
}
