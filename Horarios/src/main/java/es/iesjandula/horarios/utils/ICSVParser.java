package es.iesjandula.horarios.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.csv.ModelCSV;

/**
 * 
 * @author Pablo Ruiz Canovas
 *
 */
public interface ICSVParser 
{
	/**Interface logger */
	Logger log = LogManager.getLogger();
	/**
	 * Metodo que comprueba que el fichero sea csv, que no este corrupto y que el formato del csv sea correcto
	 * @param csvFile
	 * @throws HorarioError
	 */
	public default void checkCSVFile(MultipartFile csvFile) throws HorarioError
	{
		//Se obtiene el nombre del fichero
		String fileName = csvFile.getOriginalFilename();
		//Creamos el fichero en el que se guardara el contenido
		File file = null;
		if(!fileName.endsWith(".csv") && !fileName.endsWith(".CSV"))
		{
			throw new HorarioError(2,"El fichero tiene que ser csv");
		}
		else
		{
			//Leemos el contenido en un string
			try
			{
				String content = new String(csvFile.getBytes());
				//Comprobamos que el fichero no este vacio
				if(content.isEmpty())
				{
					throw new HorarioError(3,"Error, fichero corrupto, ilegible en bytes o vacio");
				}
				//Transferimos el contenido del multipart a un fichero csv creado en sesion
				else
				{
					//Escribimos el contenido en otro fichero
					FileWriter fw = null;
					file = this.writeContent(content, fw);
					FileReader fr = null;
					BufferedReader br = null;
					//Comprobamos la estructura en otro metodo
					this.checkCSVFormat(fr, br, file);
					
				}
			}
			catch(IOException ex)
			{
				log.error("Error el fichero "+csvFile.getName()+" esta corrupto o no se puede leer");
				throw new HorarioError(3,"Error, fichero corrupto, ilegible en bytes o vacio");
			}
		}
	}
	
	/**
	 * Metodo que lee la estructura del csv para comprobar que el fichero esta correcto
	 * @param fr
	 * @param br
	 * @param file
	 * @return true si la estructura es correcta, false si es incorrecta
	 */
	private boolean checkCSVFormat(FileReader fr,BufferedReader br,File file) throws HorarioError
	{
		boolean check = false;
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			//Leemos solo la primera linea que contiene los campos
			String linea = br.readLine();
			String [] campos = linea.split(",");
			//Eliminamos los espacios del separador
			campos[1] = campos[1].substring(0);
			campos[2] = campos[2].substring(0);
			campos[3] = campos[3].substring(0);
			//Comprobamos que los campos sean nombre,apellidos y roles
			if(!campos[0].equalsIgnoreCase("nombre") && !campos[1].equalsIgnoreCase("apellidos") && !campos[2].equalsIgnoreCase("email") && !campos[3].equalsIgnoreCase("roles"))
			{
				check = false;
			}
			else
			{
				check = true;
			}	
			if(br!=null)
			{
				br.close();
			}
			if(fr!=null)
			{
				fr.close();
			}
		}
		catch(IOException ex)
		{
			log.error("Error al leer el fichero",ex);
			throw new HorarioError(3,"Error, fichero corrupto, ilegible en bytes o vacio");
		}
		finally
		{
			try
			{
				if(br!=null)
				{
					br.close();
				}
				if(fr!=null)
				{
					fr.close();
				}
			}
			catch(IOException ex)
			{
				log.error("Error al cerrar los flujos de entrada",ex);
			}
		}
		//Mandamos el resultado final
		if(!check)
		{
			throw new HorarioError(5,"Error la estructura del csv no es correcta, los campos principales han de ser nombre,apellidos y roles");
		}
		return check;
	}
	/**
	 * Metodo que escribe el contenido leido en bytes en un fichero aparte
	 * @param content contenido en bytes
	 * @param fw FileWriter para escribir el contenido
	 * @return el fichero creado para posteriormente leerlo
	 * @throws HorarioError
	 */
	private File writeContent(String content,FileWriter fw)throws HorarioError
	{
		File file = null;
		try
		{
			//Instanciamos el file writer y escribimos el contenido en otro fichero
			fw = new FileWriter("src"+File.separator+"main"+File.separator+"resources"+File.separator+"datosCsv.dat");
			fw.write(content);
			fw.flush();
			fw.close();
			file = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"datosCsv.dat");
		}
		catch(IOException ex)
		{
			log.error("Error al escribir los datos en el fichero");
			throw new HorarioError(3,"Error, fichero corrupto, ilegible en bytes o vacio");
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
				log.error("Error al cerrar el flujo de datos");
			}
		}
		return file;
	}
	/**
	 * Metodo que parsea el csv y guarda la informacion en una lista de objetos
	 * @return una lista de modelos del csv
	 */
	public default List<ModelCSV> parseCSV() throws HorarioError
	{
		List<ModelCSV> modelos = new LinkedList<ModelCSV>();
		FileReader fr = null;
		BufferedReader br = null;
		File file = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"datosCsv.dat");
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			//Saltamos la primera linea de campos
			br.readLine();
			//Leemos linea a linea y parseamos los datos
			String linea = br.readLine();
			while(linea!=null)
			{
				String [] campos = linea.split(",");
				//Se eliminan los espacios sobrantes del separador
				campos[1] = campos[1].substring(0);
				campos[2] = campos[2].substring(0);
				//Guardamos el valor de los atributos en variables
				String nombre = campos[0];
				String apellidos = campos [1];
				String email = campos [2];
				//Roles y rolesArray se quedan vacias para analizarlas
				String roles = "";
				String [] rolesArray = null;
				//Si la lista mide 4 de longitud guardamos los roles sin mas
				if(campos.length==4)
				{
					roles = campos[3];
					rolesArray = new String [1];
					rolesArray[0] = roles;
				}
				//Si no juntamos todo para que en un string quede asi "rol1 rol2"
				else
				{
					for(int i = 3;i<campos.length;i++)
					{
						if(campos.length-1 == i)
						{
							roles+=campos[i];
						}
						else
						{
							roles+=campos[i]+=" ";
						}
					}
					//Eliminamos las primeras y ultimas comillas y guardamos el valor
					roles = roles.substring(1);
					roles = roles.substring(0, roles.length()-1);
					rolesArray = roles.split(" ");
				}
				modelos.add(new ModelCSV(nombre,apellidos,email,rolesArray));
				linea = br.readLine();
			}
		}
		catch(IOException ex)
		{
			log.error("Error al leer el fichero",ex);
			throw new HorarioError(3,"Error, fichero corrupto, ilegible en bytes o vacio");
		}
		finally
		{
			try
			{
				if(br!=null)
				{
					br.close();
				}
				if(fr!=null)
				{
					fr.close();
				}
			}
			catch(IOException ex)
			{
				log.error("Error al cerrar los flujos de entrada",ex);
			}
		}
		return modelos;
		
	}
}
