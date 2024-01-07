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
import es.iesjandula.horarios.models.Alumno;
import es.iesjandula.horarios.models.csv.ModelCSV;
import es.iesjandula.horarios.models.xml.Profesor;

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
	public default boolean checkCSVFile(MultipartFile csvFile) throws HorarioError
	{
		boolean profesor = true;
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
					profesor = this.checkCSVFormat(fr, br, file);
					
				}
			}
			catch(IOException ex)
			{
				log.error("Error el fichero "+csvFile.getName()+" esta corrupto o no se puede leer");
				throw new HorarioError(3,"Error, fichero corrupto, ilegible en bytes o vacio");
			}
		}
		return profesor;
	}
	
	/**
	 * Metodo que lee la estructura del csv para comprobar que el fichero esta correcto y se determina si el csv es de alumnos o de profesores
	 * @param fr
	 * @param br
	 * @param file
	 * @return true si el csv es de profesores o false si el csv es de alumnos
	 */
	public default boolean checkCSVFormat(FileReader fr,BufferedReader br,File file) throws HorarioError
	{
		boolean check = false;
		boolean profesor = true;
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			//Leemos solo la primera linea que contiene los campos
			String linea = br.readLine();
			String [] campos = linea.split(",");
			if(campos.length==4)
			{
				//Eliminamos los espacios del separador
				campos[1] = campos[1].substring(0);
				campos[2] = campos[2].substring(0);
				campos[3] = campos[3].substring(0);
				//Comprobamos que los campos sean apellido,nombre,dni/pasaporte y curso en caso de que el csv sea de alumnos
				if(campos[0].equalsIgnoreCase("apellido") && campos[1].equalsIgnoreCase("nombre") && campos[2].equalsIgnoreCase("dni/pasaporte") && campos[3].equalsIgnoreCase("curso"))
				{
					profesor = false;
					check = true;
				}	
			}
			else
			{
				//Eliminamos los espacios del separador
				campos[1] = campos[1].substring(0);
				campos[2] = campos[2].substring(0);
				campos[3] = campos[3].substring(0);
				campos[4] = campos[4].substring(0);
				//Comprobamos que los campos sean nombre,apellidos y roles en caso de que el csv sea de profesores
				if(!campos[0].equalsIgnoreCase("nombre") && !campos[1].equalsIgnoreCase("apellidos") && !campos[2].equalsIgnoreCase("email") && !campos[3].equalsIgnoreCase("telefono") && !campos[3].equalsIgnoreCase("roles"))
				{
					check = false;
				}
				else
				{
					check = true;
				}
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
		//En caso de que los campos no coincidan se manda un error
		if(!check)
		{
			throw new HorarioError(5,"Error la estructura del csv no es correcta, los campos principales han de ser nombre,apellidos y roles");
		}
		//Se devuelve si el csv es de alumnos o profesores
		return profesor;
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
				campos[3] = campos[3].substring(0);
				//Guardamos el valor de los atributos en variables
				String nombre = campos[0];
				String apellidos = campos [1];
				String email = campos [2];
				String telefono = campos[3];
				//Roles y rolesArray se quedan vacias para analizarlas
				String roles = "";
				String [] rolesArray = null;
				//Si la lista mide 4 de longitud guardamos los roles sin mas
				if(campos.length==5)
				{
					roles = campos[4];
					rolesArray = new String [1];
					rolesArray[0] = roles;
				}
				//Si no juntamos todo para que en un string quede asi "rol1 rol2"
				else
				{
					for(int i = 4;i<campos.length;i++)
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
				modelos.add(new ModelCSV(nombre,apellidos,email,telefono,rolesArray));
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
	/**
	 * Metodo que carga los alumnos del csv en una lista y los devuelve
	 * @return lista de alumnos
	 * @throws HorarioError
	 */
	public default List<Alumno> parseAlumnos() throws HorarioError
	{
		List<Alumno> alumnos = new LinkedList<Alumno>();
		File file = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"datosCsv.dat");
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			//Saltamos la primera linea del csv
			br.readLine();
			//Iteramos las lineas hasta el final
			String linea = br.readLine();
			while(linea!=null)
			{
				String [] campos = linea.split(",");
				//Eliminamos los espacios del split
				campos[1] = campos[1].substring(1);
				campos[2] = campos[2].substring(0);
				campos[3] = campos[3].substring(0);	
				//Añadimos los alumnos uno a uno a la lista
				alumnos.add(new Alumno(campos[0],campos[1],campos[2],campos[3]));
				linea = br.readLine();
			}
		}
		catch(IOException ex)
		{
			log.error("Error al leer los alumnos",ex);
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
				log.error("Error al cerrar el fichero",ex);
			}
		}
		return alumnos;
	}
	
	/**
	 * Metodo que recoge los profesores del xml y los parsea a formato csv con un telefono y un email
	 * @param profesores
	 * @throws HorarioError
	 */
	public default void escribirModelos(List<Profesor> profesores) throws HorarioError
	{
		List<ModelCSV> modelos = new LinkedList<>();
		File file = new File("src/main/resources/modelos.csv");
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(file);
			for(Profesor p:profesores)
			{
				String [] campos = p.getNombre().split(",");
				campos[1] = campos[1].substring(1);
				String [] apellidos = campos[0].split(" ");
				apellidos[1] = apellidos[1].substring(0);
				String correo = campos[1].substring(0,1) + apellidos[0].substring(0, 3) + apellidos[1].substring(0, 3) + "@g.educaand.es";
				correo = correo.toLowerCase();
				int numTele = (int)(Math.random()*999999999+100000000);
				String telefono = String.valueOf(numTele);
				numTele = (int)(Math.random()*3+1);
				String [] rol = this.seleccionarRol(numTele);
				ModelCSV modelo = new ModelCSV(campos[1],campos[0],correo,telefono,rol);
				modelos.add(modelo);
			}
			fw.write("Nombre,Apellidos,Email,Telefono,Roles\n");
			for(ModelCSV m:modelos)
			{
				String [] roles = m.getRoles();
				String campoRol = "";
				if(roles.length==1)
				{
					campoRol = "\""+roles[0] +"\"";
				}
				else if(roles.length==2)
				{
					campoRol = "\""+roles[0] +","+roles[1]+"\"";
				}
				else
				{
					campoRol = "\""+roles[0] +","+roles[1]+","+roles[2]+"\"";
				}
				fw.write(m.getNombre()+","+m.getApellido()+","+m.getEmail()+","+m.getTelefono()+","+campoRol+"\n");
			}
			fw.flush();
			log.info("Fichero escrito");
		}
		catch(IOException ex)
		{
			log.error("Error al escribir los modelos de los profesores");
			throw new HorarioError(0,"Error al parsear los profesores a modelos");
		}
		finally
		{
			try
			{
				if(fw != null)
				{
					fw.close();
				}
			}
			catch(IOException ex)
			{
				log.error("Error al cerrar e fichero");
			}
		}
		
	}
	
	private String [] seleccionarRol(int numTele)
	{
		String [] roles = null;
		if(numTele==1)
		{
			roles = new String[1];
			roles[0] = "Docente";
		}
		else if(numTele==2)
		{
			roles = new String[2];
			roles[0] = "Docente";
			roles[1] = "Administrador";
		}
		else
		{
			roles = new String[3];
			roles[0] = "Docente";
			roles[1] = "Administrador";
			roles[2] = "Conserje";
		}
		return roles;
	}
}
