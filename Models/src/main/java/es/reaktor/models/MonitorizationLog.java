package es.reaktor.models;

import lombok.Data;

/**
 * @author Juan Sutil Mesa
 */
@Data
public class MonitorizationLog
{
	
	private String lastRemoteReset;
	
	private CommandLine lastCommandLine;
	
	private String lastRemoteWebUri;
	
	private Software lastInstall;
	
	private Software lastUnistall;
	
}
