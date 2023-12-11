package es.reaktor.reaktor.checker;

import java.io.File;

import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.Peripheral;
import es.reaktor.reaktor.models.Software;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alejandro Cazalla
 *
 */
public class Checkers 
{
	
	public void checkCommands(String serialNumber, String classroom, String trolley, Integer plant,CommandLine commandLine) throws ComputerError
	{
		if (commandLine == null)
		{
			throw new ComputerError(1, "Command line can't be null");
		} else if (serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant == null)
		{
			throw new ComputerError(2, "All params can't be null");
		}
	}
	
	public void checkParamsComputerShutdown(String serialNumber, String classroom, String trolley, Integer plant)throws ComputerError
	{
		if (serialNumber.isEmpty())
		{
			throw new ComputerError(1, "Serial Number is null");
		} else if (classroom.isEmpty())
		{
			throw new ComputerError(2, "Classroom is null");
		} else if (trolley.isEmpty())
		{
			throw new ComputerError(3, "Trolley is null");
		} else if (plant == null)
		{
			throw new ComputerError(4, "Plant is null");
		}
	}
	
	public void checkParamsPutComputerRestart(String serialNumber, String classroom, String trolley, Integer plant)throws ComputerError
	{
		if (serialNumber.isEmpty())
		{
			throw new ComputerError(1, "Serial Number is null");
		} else if (classroom.isEmpty())
		{
			throw new ComputerError(2, "Classroom is null");
		} else if (trolley.isEmpty())
		{
			throw new ComputerError(3, "Trolley is null");
		} else if (plant == null)
		{
			throw new ComputerError(4, "Plant is null");
		}
	}
	
	public void checkParamsAddByBody(String classroom, String trolley, Peripheral[] peripheral) throws ComputerError
	{
		if (classroom.isEmpty() && trolley.isEmpty())
		{
			throw new ComputerError(1, "All params can't be null");
		} else if (peripheral == null)
		{
			throw new ComputerError(2, "The Pheriferal can't be null");
		}
	}
	
	public void checkParamsSendScreenshotOrder(String classroom, String trolley) throws ComputerError
	{
		if (classroom == null || classroom.isEmpty() || trolley == null || trolley.isEmpty())
		{
			throw new ComputerError(1, "Classroom and trolley cannot be null or empty");
		}

		if (!classroom.matches("[A-Za-z0-9]+"))
		{
			throw new ComputerError(2, "Classroom must contain only alphanumeric characters");
		}
	}
	
	public void checkParamsGetScreenshots(String classroom,String trolley) throws ComputerError
	{
		if(classroom.isEmpty() && trolley.isEmpty())
		{
			throw new ComputerError(1,"All params can't be null");
		}
	}
	
	public void checkParamsPostComputerExeFile(String serialNumber, String classroom, String trolley, Integer plant,File execFile) throws ComputerError
	{
		if (serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant == null)
		{
			throw new ComputerError(1, "All params can't be null");
		} else if (execFile == null)
		{
			throw new ComputerError(2, "The file can't be null");
		} else if (execFile.getName().endsWith(".exe") || execFile.getName().endsWith(".cfg")|| execFile.getName().endsWith(".exec"))
		{
			throw new ComputerError(4, "The file extensiojn its wrong");
		}
	}
	
	public void checkParamsSendSoftware(String classroom, String trolley, String professor, Software[] softwareInstance)throws ComputerError
	{
		if (classroom.isEmpty())
		{
			throw new ComputerError(1, "Classroom is null");
		} else if (trolley.isEmpty())
		{
			throw new ComputerError(2, "Trolley is null");
		} else if (professor.isEmpty())
		{
			throw new ComputerError(3, "Professor is null");
		} else if (softwareInstance == null)
		{
			throw new ComputerError(4, "SoftwareInstance is null");
		}
	}
	
	public void checkParamsUninstallSoftware(String classroom, String trolley, String professor,Software[] softwareInstance) throws ComputerError
	{
		if (classroom.isEmpty())
		{
			throw new ComputerError(1, "Classroom is null");
		} else if (trolley.isEmpty())
		{
			throw new ComputerError(2, "Trolley is null");
		} else if (professor.isEmpty())
		{
			throw new ComputerError(3, "Professor is null");
		} else if (softwareInstance == null)
		{
			throw new ComputerError(4, "SoftwareInstance is null");
		}
	}
	
	public void checkParamsUpdatecomputer(String serialNumber, String andaluciaId, String computerNumber, Computer computer) throws ComputerError
	{
		if (serialNumber.isEmpty() && andaluciaId.isEmpty() && computerNumber.isEmpty())
		{
			throw new ComputerError(1, "All params can't be null");
		} else if (computer == null)
		{
			throw new ComputerError(2, "Computer can't be null");
		}
	}
}
