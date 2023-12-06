package es.reaktor.models;

import lombok.Data;

@Data
public class MonitorizationLog 
{
	String lastRemoteReset;
	CommandLine lastCommandsLine;
    String lastRemoteWebUri;
    Software lastInstall;    
    Software lastUnistall;
        

}
