package es.iesjandula.Horarios.utils;

import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.Horarios.exceptions.HorarioError;

/**
 * @author Alejandro Cazalla Pérez
 */
public class HorariosCheckers
{
	
	/**
	 * Public constructor
	 */
	public HorariosCheckers() 
	{
		// Empty constructor
	}
	
	public void checkFile(MultipartFile file) throws HorarioError
	{
		if (file == null)
		{
			throw new HorarioError(1, "Error with the file");
		}
	}
}
