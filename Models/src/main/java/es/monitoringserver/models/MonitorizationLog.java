package es.monitoringserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorizationLog {
	
	/**
     * - ATTRIBUTES -
     * This attributes have the lastRemoteReset
     */
	private String lastRemoteReset;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the lastCommandLine
     */
	private CommandLine lastCommandLine;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the lastRemoteWebUri
     */
	private String lastRemoteWebUri;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the lastInstall
     */
	private Software lastInstall;
	
	/**
     * - ATTRIBUTES -
     * This attributes have the lastUnistall
     */
	private Software lastUnistall;

}
