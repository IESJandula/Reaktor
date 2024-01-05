package es.reaktor.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
