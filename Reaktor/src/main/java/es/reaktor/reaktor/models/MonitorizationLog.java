package es.reaktor.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Miguel
 */

@Data
@AllArgsConstructor
public class MonitorizationLog
{
	/**The Last Reset*/
	private String lastRemoteReset;

	/**The Last Commands*/
	private CommandLine lastCommandsLine;

	/**The Last Web URI*/
	private String lastRemoteWebUri;

	/**The Last Software Install*/
	private Software lastInstall;

	/**The Last Software Uninstall*/
	private Software lastUninstall;

	/**The empty constructor of the MonitorizationLog Class*/
	public MonitorizationLog()
	{
	}
}