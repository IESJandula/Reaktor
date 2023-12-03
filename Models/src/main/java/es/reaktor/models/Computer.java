package es.reaktor.models;

import java.util.List;

/**
 * @author Juan Sutil Mesa
 */
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
	
	
	/**
	 * 
	 */
	public Computer()
	{
		
	}


	/**
	 * @param serialNumber
	 * @param andaluciaId
	 * @param computerNumber
	 * @param operativeSystem
	 * @param professor
	 * @param location
	 * @param hardwareList
	 * @param softwareList
	 * @param commandLine
	 * @param monitorizationLog
	 */
	public Computer(String serialNumber, String andaluciaId, String computerNumber, String operativeSystem,
			String professor, Location location, List<HardwareComponent> hardwareList, List<Software> softwareList,
			CommandLine commandLine, MonitorizationLog monitorizationLog)
	{
		this.serialNumber = serialNumber;
		this.andaluciaId = andaluciaId;
		this.computerNumber = computerNumber;
		this.operativeSystem = operativeSystem;
		this.professor = professor;
		this.location = location;
		this.hardwareList = hardwareList;
		this.softwareList = softwareList;
		this.commandLine = commandLine;
		this.monitorizationLog = monitorizationLog;
	}


	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber()
	{
		return serialNumber;
	}


	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}


	/**
	 * @return the andaluciaId
	 */
	public String getAndaluciaId()
	{
		return andaluciaId;
	}


	/**
	 * @param andaluciaId the andaluciaId to set
	 */
	public void setAndaluciaId(String andaluciaId)
	{
		this.andaluciaId = andaluciaId;
	}


	/**
	 * @return the computerNumber
	 */
	public String getComputerNumber()
	{
		return computerNumber;
	}


	/**
	 * @param computerNumber the computerNumber to set
	 */
	public void setComputerNumber(String computerNumber)
	{
		this.computerNumber = computerNumber;
	}


	/**
	 * @return the operativeSystem
	 */
	public String getOperativeSystem()
	{
		return operativeSystem;
	}


	/**
	 * @param operativeSystem the operativeSystem to set
	 */
	public void setOperativeSystem(String operativeSystem)
	{
		this.operativeSystem = operativeSystem;
	}


	/**
	 * @return the professor
	 */
	public String getProfessor()
	{
		return professor;
	}


	/**
	 * @param professor the professor to set
	 */
	public void setProfessor(String professor)
	{
		this.professor = professor;
	}


	/**
	 * @return the location
	 */
	public Location getLocation()
	{
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location)
	{
		this.location = location;
	}


	/**
	 * @return the hardwareList
	 */
	public List<HardwareComponent> getHardwareList()
	{
		return hardwareList;
	}


	/**
	 * @param hardwareList the hardwareList to set
	 */
	public void setHardwareList(List<HardwareComponent> hardwareList)
	{
		this.hardwareList = hardwareList;
	}


	/**
	 * @return the softwareList
	 */
	public List<Software> getSoftwareList()
	{
		return softwareList;
	}


	/**
	 * @param softwareList the softwareList to set
	 */
	public void setSoftwareList(List<Software> softwareList)
	{
		this.softwareList = softwareList;
	}


	/**
	 * @return the commandLine
	 */
	public CommandLine getCommandLine()
	{
		return commandLine;
	}


	/**
	 * @param commandLine the commandLine to set
	 */
	public void setCommandLine(CommandLine commandLine)
	{
		this.commandLine = commandLine;
	}


	/**
	 * @return the monitorizationLog
	 */
	public MonitorizationLog getMonitorizationLog()
	{
		return monitorizationLog;
	}


	/**
	 * @param monitorizationLog the monitorizationLog to set
	 */
	public void setMonitorizationLog(MonitorizationLog monitorizationLog)
	{
		this.monitorizationLog = monitorizationLog;
	}
	
	
	
	
}
