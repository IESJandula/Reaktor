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
	/**
     * - ATTRIBUTES -
     * This attributes have the serial number
     */
	private String serialNumber;
	/**
     * - ATTRIBUTES -
     * This attributes have the andalucia id
     */
	private String andaluciaID;
	/**
     * - ATTRIBUTES -
     * This attributes have the computer number
     */
	private String computerNumber;
	/**
     * - ATTRIBUTES -
     * This attributes have the operative system
     */
	private String operativeSystem;
	/**
     * - ATTRIBUTES -
     * This attributes have the professor
     */
	private String professor;
	/**
     * - ATTRIBUTES -
     * This attributes have the location
     */
	private Location location;
	/**
     * - ATTRIBUTES -
     * This attributes have a array of hardware components
     */
	private List<HardwareComponent> hardwareList;
	/**
     * - ATTRIBUTES -
     * This attributes have a array of softwares
     */
	private List<Software> softwareList;
	/**
     * - ATTRIBUTES -
     * This attributes have the command lines
     */
	private CommandLine commandLine;
	/**
     * - ATTRIBUTES -
     * This attributes have the monitorization log
     */
	private MonitorizationLog motorizationLog;
	
}
