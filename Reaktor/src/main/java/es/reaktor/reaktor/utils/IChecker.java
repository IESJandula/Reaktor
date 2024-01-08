package es.reaktor.reaktor.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.reaktor.reaktor.constants.Constantes;
import es.reaktor.reaktor.exceptions.ComputerError;
import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.Peripheral;
import es.reaktor.reaktor.models.Software;
import es.reaktor.reaktor.models.Status;

/**
 * 
 * @author Pablo Ruiz, Miguel Rios, Alejandro Cazalla
 *
 */
public interface IChecker 
{
	/**
	 * Metodo que comprueba que todos los parametros esten correctos y que encuentra la informacion solicitada
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @param commandLine
	 * @throws ComputerError
	 */
	public default void checkParams(String serialNumber, String classroom, String trolley, Integer plant,CommandLine commandLine) throws ComputerError
	{
		//Comprobamos que el command line no este vacio
		if (commandLine == null)
		{
			throw new ComputerError(1, "La consola de comandos no puede estar vacia");
		}
		//Se comprueba que todos los atributos no esten vacios
		else if (serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant == null)
		{
			throw new ComputerError(2, "Los parametros no pueden estar vacios");
		}
	}
	/**
	 * Metodo sobrecargado que comprueba que los parametros esten correctos y que encuentra la informacion solicitada
	 * @param serialNumber
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @throws ComputerError
	 * @see Metodo principal {@link #checkParams(String, String, String, Integer, CommandLine)}
	 */
	public default void checkParams(String serialNumber, String classroom, String trolley, Integer plant)throws ComputerError
	{
		//Se comprueba que los atributos no esten vacios
		if (serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant == null)
		{
			throw new ComputerError(2, "Los parametros no pueden estar vacios");
		}
	}
	/**
	 * Metodo sobrecargado que comprueba que los parametros esten correctos y que encuentra la información solicitada
	 * @param classroom
	 * @param trolley
	 * @param peripherals
	 * @throws ComputerError
	 * @see Metodo principal {@link #checkParams(String, String, String, Integer, CommandLine)}
	 */
	public default void checkParams(String classroom,String trolley, Peripheral [] peripherals) throws ComputerError
	{
		//Se comprueba que los atributos no esten vacios
		if(classroom.isEmpty() && trolley.isEmpty())
		{
			throw new ComputerError(2, "Los parametros no pueden estar vacios");
		}
		//Se comprueba que los perifericos no esten vacios
		else if(peripherals.length == 0)
		{
			throw new ComputerError(3, "La lista de perifericos no puede estar vacia");
		}
		//Iteramos los perifericos para comprobar que tienen datos
		int cont = 0;
		while(cont<peripherals.length)
		{
			if(peripherals[cont].getComponent().isEmpty() || peripherals[cont].getCuantity()<1 )
			{
				throw new ComputerError(4,"La lista de perifericos tiene datos vacios o incongruentes");
			}
			cont++;
		}
	}
	/**
	 * Metodo sobrecargado que comprueba que los parametros esten correctos y que encuentra la informacion solicitada
	 * @param classroom
	 * @param trolley
	 * @throws ComputerError
	 * @see Metodo principal {@link #checkParams(String, String, String, Integer, CommandLine)}
	 */
	public default void checkParams(String classroom,String trolley) throws ComputerError
	{
		//Se comprueba que los parametros no esten vacios
		if(classroom.isEmpty() && trolley.isEmpty())
		{
			throw new ComputerError(2,"Los parametros no pueden estar vacios");
		}
	}
	/**
	 * Metodo sobrecargado que comprueba que los parametros esten correctos y que encuentra la informacion solicitada
	 * @param serialNumber
	 * @throws ComputerError
	 * @see Metodo principal {@link #checkParams(String, String, String, Integer, CommandLine)}
	 */
	public default void checkParams(String serialNumber) throws ComputerError
	{
		//Se comprueba que el serial number no este vacio
		if(serialNumber.isEmpty())
		{
			throw new ComputerError(2,"Los parametros no pueden estar vacios");
		}
	}
	
	/**
	 * Metodo sobrecargado que comprueba que los parametros esten correctos y que encuentra la informacion solicitada
	 * @param serialNumber
	 * @param juntaNumber
	 * @param computerNumber
	 * @param cpuNumber
	 * @param ramCap
	 * @param hdCap
	 * @param classroom
	 * @param trolley
	 * @param plant
	 * @param teacher
	 * @throws ComputerError
	 * @see Metodo principal {@link #checkParams(String, String, String, Integer, CommandLine)}
	 */
	public default List<Computer> checkParams(String serialNumber, String juntaNumber, String computerNumber, String classroom, String trolley, Integer plant, String teacher, List<Computer> computers) throws ComputerError
	{
		
		//Se comprueba que los parametros no esten vacios, y si lo estan se devolvera la lista entera de los ordenadores
		if(serialNumber.isEmpty() && juntaNumber.isEmpty() && computerNumber.isEmpty() && classroom.isEmpty())
		{
			computers = Constantes.cargarOrdenadores();
		}
		else  if (!serialNumber.isEmpty()) 
		{
			this.iterateListComputer(serialNumber, "serialNumber", computers);
		}
		else if (!juntaNumber.isEmpty()) 
		{
			this.iterateListComputer(juntaNumber, "andaluciaId", computers);
		}
		else if (!computerNumber.isEmpty()) 
		{
			this.iterateListComputer(computerNumber, "computerNumber", computers);
		}	
		else if (!classroom.isEmpty()) 
		{
			this.iterateListComputer(classroom, "classroom", computers);
		}
		else if (!trolley.isEmpty()) 
		{
			this.iterateListComputer(trolley, "trolley", computers);
		}
		else if (plant != null) 
		{
			this.iterateListComputer(String.valueOf(plant), "plant", computers);
		}
		else if (!teacher.isEmpty()) 
		{
			this.iterateListComputer(teacher, "teacher", computers);
		}
		
		return computers;
	}
	/**
	 * Metodo que comprueba que el fichero pasado por parametro sea una imagen y tenga contenido
	 * @param screenshot
	 * @throws ComputerError
	 */
	public default void checkScreenshot(MultipartFile screenshot) throws ComputerError
	{
		//Se comprueba que el fichero sea una imagen
		String fileName = screenshot.getOriginalFilename();
		if(fileName.endsWith(".png") || fileName.endsWith(".PNG") || fileName.endsWith(".jpg") || fileName.endsWith(".JPG"))
		{
			throw new ComputerError(5,"El fichero no es una imagen png o jpg");
		}
		//Despues se comprueba si tiene datos
		else if(screenshot.isEmpty())
		{
			throw new ComputerError(6,"El fichero esta vacio");
		}
	}
	
	public default void checkParamsSendScreenshotOrder(String classroom, String trolley) throws ComputerError
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
	
	public default void checkParamsGetScreenshots(String classroom,String trolley) throws ComputerError
	{
		if(classroom.isEmpty() && trolley.isEmpty())
		{
			throw new ComputerError(1,"All params can't be null");
		}
	}
	
	public default void checkParamsPostComputerExeFile(String serialNumber, String classroom, String trolley, Integer plant,MultipartFile execFile) throws ComputerError
	{
		if (serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant == null)
		{
			throw new ComputerError(1, "All params can't be null");
		} else if (execFile == null)
		{
			throw new ComputerError(2, "The file can't be null");
		} else if (!execFile.getOriginalFilename().endsWith(".exe") && !execFile.getOriginalFilename().endsWith(".cfg") && !execFile.getOriginalFilename().endsWith(".exec") && execFile.getOriginalFilename().endsWith(".msi"))
		{
			throw new ComputerError(4, "The file extensiojn its wrong");
		}
	}
	
	public default void checkParamsSendSoftware(String classroom, String trolley, String professor, Software[] softwareInstance)throws ComputerError
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
	
	public default void checkParamsUninstallSoftware(String classroom, String trolley, String professor,Software[] softwareInstance) throws ComputerError
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
	
	public default Computer checkParamsUpdatecomputer(String serialNumber, String andaluciaId, String computerNumber, Computer computer,List<Computer>computers) throws ComputerError
	{
		if (serialNumber.isEmpty() && andaluciaId.isEmpty() && computerNumber.isEmpty())
		{
			throw new ComputerError(1, "All params can't be null");
		} 
		else if (computer == null)
		{
			throw new ComputerError(2, "Computer can't be null");
		}
		computer = this.updateComputer(serialNumber, andaluciaId, computerNumber, computer, computers);
		return computer;
		
	}
	
	private Computer updateComputer(String serialNumber, String andaluciaId, String computerNumber, Computer computer,List<Computer>computers) throws ComputerError
	{
		Computer computerReturn = null;
		if(!serialNumber.isEmpty())
		{
			for(Computer a:computers)
			{
				if(serialNumber.equals(a.getSerialNumber()))
				{
					computerReturn = computer;
				}
				if(computerReturn==null)
				{
					throw new ComputerError(3,"There's no computer with this "+serialNumber+" with serial number");
				}
			}
		}
		else if(!andaluciaId.isEmpty())
		{
			for(Computer a:computers)
			{
				if(andaluciaId.equals(a.getAndaluciaID()))
				{
					computerReturn = computer;
				}
			}
			if(computerReturn==null)
			{
				throw new ComputerError(3,"There's no computer with this "+andaluciaId+" with andalucia id");
			}
		}
		else if(!computerNumber.isEmpty())
		{
			for(Computer a:computers)
			{
				if(computerNumber.equals(a.getComputerNumber()))
				{
					computerReturn = computer;
				}
			}
			if(computerReturn==null)
			{
				throw new ComputerError(3,"There's no computer with this "+computerNumber+" with computer number");
			}
		}
		return computerReturn;
		
	}
	
	public default List<Computer> replaceComputer(Computer computer, List<Computer> computers)
	{
		int index = 0;
		while(index<computers.size())
		{
			Computer a = computers.get(index);
			if(a.getSerialNumber().equals(computer.getSerialNumber()) || a.getAndaluciaID().equals(computer.getAndaluciaID()) || a.getComputerNumber().equals(computer.getComputerNumber()))
			{
				computers.remove(index);
				computers.add(computer);
				break;
			}
			index++;
		}
		return computers;
	}
	
	public default void checkComputer(String serialNumber, String andaluciaId, String computerNumber, List<Computer>computers) throws ComputerError 
	{
		if (serialNumber.isEmpty() && andaluciaId.isEmpty() && computerNumber.isEmpty())
		{
			throw new ComputerError(1, "All params can't be null");
		} 
		else  if (!serialNumber.isEmpty()) 
		{
			this.iterateComputer(serialNumber, "serialNumber", computers);
		}
		else if (!andaluciaId.isEmpty()) 
		{
			this.iterateComputer(andaluciaId, "andaluciaId", computers);
		}
		else if (!computerNumber.isEmpty()) 
		{
			this.iterateComputer(computerNumber, "computerNumber", computers);
		}	
	}
	
	private Computer iterateComputer(String data, String dataType, List<Computer>computers) throws ComputerError 
	{
		Computer computerReturn = null;
		if (dataType.equals("serialNumber"))
		{
			for(Computer a:computers)
			{
				
				if (a.getSerialNumber().equals(data))
				{
					computerReturn = a;
				}
			}
		}
		else if (dataType.equals("andaluciaId"))
		{
			for(Computer a:computers)
			{
				
				if (a.getAndaluciaID().equals(data))
				{
					computerReturn = a;
				}
			}
		}
		else if (dataType.equals("computerNumber"))
		{
			for(Computer a:computers)
			{
				
				if (a.getComputerNumber().equals(data))
				{
					computerReturn = a;
				}
			}
		}
		else
		{
			throw new ComputerError(1, "data types are not correct");
		}
		
		if(computerReturn==null)
		{
			throw new ComputerError(2,"There's no computer with this data");
		}
		
		return computerReturn;
		
	}
	
	public default void checkArguments(String classroom, String trolley, Integer plant, List<Computer> computers) throws ComputerError
	{
		if (classroom.isEmpty() && trolley.isEmpty() && plant == null)
		{
			throw new ComputerError(1, "All params can't be null");
		} 
		else  if (!classroom.isEmpty()) 
		{
			this.iterateArguments(classroom, "classroom", computers);
		}
		else if (!trolley.isEmpty()) 
		{
			this.iterateArguments(trolley, "trolley", computers);
		}
		else if (plant != null) 
		{
			this.iterateArguments(String.valueOf(plant), "plant", computers);
		}	
	}
	
	private Computer iterateArguments(String data, String dataType, List<Computer>computers) throws ComputerError 
	{
		Computer computerReturn = null;
		if (dataType.equals("classroom"))
		{
			for(Computer a:computers)
			{
				
				if (a.getLocation().getClassroom().equals(data))
				{
					computerReturn = a;
				}
			}
		}
		else if (dataType.equals("trolley"))
		{
			for(Computer a:computers)
			{
				
				if (a.getLocation().getTrolley().equals(data))
				{
					computerReturn = a;
				}
			}
		}
		else if (dataType.equals("plant"))
		{
			for(Computer a:computers)
			{
				
				if (String.valueOf(a.getLocation().getPlant()).equals(data))
				{
					computerReturn = a;
				}
			}
		}
		else
		{
			throw new ComputerError(1, "data types are not correct");
		}
		
		if(computerReturn==null)
		{
			throw new ComputerError(2,"There's no computer with this data");
		}
		
		return computerReturn;
		
	}
	
	private List<Computer> iterateListComputer(String data, String dataType, List<Computer>computers) throws ComputerError 
	{
		if (dataType.equals("serialNumber"))
		{
			for(Computer a:computers)
			{
				
				if (a.getSerialNumber().equals(data))
				{
					computers.add(a);
				}
			}
		}
		
		else if (dataType.equals("juntaNumber"))
		{
			for(Computer a:computers)
			{
				
				if (a.getAndaluciaID().equals(data))
				{
					computers.add(a);
				}
			}
		}
		else if (dataType.equals("computerNumber"))
		{
			for(Computer a:computers)
			{
				
				if (a.getComputerNumber().equals(data))
				{
					computers.add(a);
				}
			}
		}
		
		else if (dataType.equals("classroom"))
		{
			for(Computer a:computers)
			{
				
				if (a.getLocation().getClassroom().equals(data))
				{
					computers.add(a);
				}
			}
		}
		else if (dataType.equals("trolley"))
		{
			for(Computer a:computers)
			{
				
				if (a.getLocation().getTrolley().equals(data))
				{
					computers.add(a);
				}
			}
		}
		else if (dataType.equals("plant"))
		{
			for(Computer a:computers)
			{
				
				if (String.valueOf(a.getLocation().getPlant()).equals(data))
				{
					computers.add(a);
				}
			}
		}
		else if (dataType.equals("teacher"))
		{
			for(Computer a:computers)
			{
				
				if (a.getProfessor().equals(data))
				{
					computers.add(a);
				}
			}
		}
		else
		{
			throw new ComputerError(1, "data types are not correct");
		}
		
		return computers;
		
	}
	
	public default void writeJson(String datos) throws ComputerError
	{
		File file = new File("src/main/resources/status.json");
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(file);
			fw.write(datos);
			fw.flush();
		}
		catch(IOException ex)
		{
			throw new ComputerError(1,"Error writing the json file");
		}
		finally
		{
			try
			{
				if(fw!=null)
				{
					fw.close();
				}
			}
			catch(IOException ex)
			{
				
			}
		}
	}
	
	public default void deleteJson(String datos) throws ComputerError
	{
		File file = new File("src/main/resources/status.json");
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(file);
			fw.write("");
			fw.flush();
		}
		catch(IOException ex)
		{
			throw new ComputerError(1,"Error writing the json file");
		}
		finally
		{
			try
			{
				if(fw!=null)
				{
					fw.close();
				}
			}
			catch(IOException ex)
			{
				
			}
		}
	}
	
	public default String execute(List<Computer> computers) throws ComputerError
	{
		ObjectMapper mapper = Json.mapper();
		String datos = "";
		try
		{
			List<Status> statuses = mapper.readValue(Paths.get("src/main/resources/status.json").toFile(), new TypeReference<List<Status>> () {});
			for(Status s:statuses)
			{
				datos += this.clavesOrdenador(s, computers);
				datos += this.multiplesOrdenadores(s, computers);
			}
		}
		catch(Exception ex)
		{
			throw new ComputerError(1,"Error parsing the json "+ex);
		}
		return datos;
	}
	
	private String clavesOrdenador(Status s,List<Computer>computers)
	{
		String datos = "";
		if(s.getKey().equals("serialNumber"))
		{
			for(Computer c:computers)
			{
				if(c.getSerialNumber().equals(s.getValue()))
				{
					datos+= "Tarea "+s.getInfo()+" aplicada sobre el ordenador "+c.toString()+"\n";
				}
			}
		}
		if(s.getKey().equals("andaluciaId"))
		{
			for(Computer c:computers)
			{
				if(c.getAndaluciaID().equals(s.getValue()))
				{
					datos+= "Tarea "+s.getInfo()+" aplicada sobre el ordenador "+c.toString()+"\n";
				}
			}
		}
		if(s.getKey().equals("computerNumber"))
		{
			for(Computer c:computers)
			{
				if(c.getComputerNumber().equals(s.getValue()))
				{
					datos+= "Tarea "+s.getInfo()+" aplicada sobre el ordenador "+c.toString()+"\n";
				}
			}
		}
		return datos;
	}
	
	private String multiplesOrdenadores(Status s,List<Computer>computers)
	{
		String datos = "";
		if(s.getKey().equals("classroom"))
		{
			for(Computer c:computers)
			{
				if(c.getLocation().getClassroom().equals(s.getValue()));
				{
					datos+= "Tarea "+s.getInfo()+" aplicada sobre el ordenador "+c.toString()+"\n";
				}
			}
		}
		if(s.getKey().equals("trolley"))
		{
			for(Computer c:computers)
			{
				if(c.getLocation().getTrolley().equals(s.getValue()));
				{
					datos+= "Tarea "+s.getInfo()+" aplicada sobre el ordenador "+c.toString()+"\n";
				}
			}
		}
		if(s.getKey().equals("plant"))
		{
			for(Computer c:computers)
			{
				String valorPlanta = String.valueOf(c.getLocation().getPlant());
				if(valorPlanta.equals(s.getValue()));
				{
					datos+= "Tarea "+s.getInfo()+" aplicada sobre el ordenador "+c.toString()+"\n";
				}
			}
		}
		return datos;
	}
}
