package es.iesjandula.Horarios.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import es.iesjandula.Horarios.models.Student;

public class ParseAlumnos
{

	private String path;

	/**
	 * @param path
	 */
	public ParseAlumnos(String path)
	{
		this.path = path;
	}

	public List<Student> parse()
	{
		File file = new File("C:\\Users\\Juan\\Downloads\\ALUMNADO.csv");
		
		Scanner scanner = null;

		List<Student> alumnos = new ArrayList<Student>();
		try
		{
			scanner = new Scanner(file);

			scanner.nextLine();

			while (scanner.hasNextLine())
			{
				System.out.println("a");
				String[] parameters = scanner.nextLine().split(";");
				
				alumnos.add(this.createStudent(parameters));
				
			}

		} catch (FileNotFoundException e)
		{
			String error = "error al parsear alumnos";
			
			System.out.println(e);
		}finally {
			if (scanner != null)
			{
				scanner.close();
			}
		}

		return alumnos;
	}

	public Student createStudent(String[] parameters)
	{
		
		String[] nombreCompleto = parameters[0].split(",");
		
		return new Student(nombreCompleto[1].replace(" ", ""), nombreCompleto[1], parameters[3], parameters[4]);
	}

}
