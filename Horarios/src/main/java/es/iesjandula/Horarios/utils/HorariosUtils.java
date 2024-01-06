package es.iesjandula.Horarios.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.RolReaktor;
import es.iesjandula.Horarios.models.xml.InfoCentro;
import jakarta.servlet.http.HttpSession;

/**
 * @author Alejandro Cazalla Pérez
 */
public class HorariosUtils
{
	final static Logger logger = LogManager.getLogger();

	/**
	 * Public constructor
	 */
	public HorariosUtils()
	{
		// Empty constructor
	}

	public List<Profesor> parseCsvFile(MultipartFile file, HttpSession session) throws HorarioError
	{
		List<Profesor> profesores = new ArrayList<Profesor>(((InfoCentro) session.getAttribute("info")).getDatos().getProfesores().values());
		
		try 
		{
			Scanner sc = new Scanner(file.getResource().getContentAsString(Charset.defaultCharset()));

			sc.nextLine();
			while (sc.hasNextLine())
			{
				String[] parts = sc.nextLine().split(";");

				String nombre = parts[1] +  ", " +parts[0];
				
				Profesor profesor = this.getIdProfesor(profesores, nombre);
				
				if(profesor != null) {
					
					String cuentaDeCorreo = parts[2];
					List<RolReaktor> listaDeRoles = getRoles(parts[3]);
					
					profesor.setCuentaDeCorreo(cuentaDeCorreo);
					profesor.setListaRoles(listaDeRoles);
				}

			}
			sc.close();
		}
		catch(IOException e) 
		{
			String message = "Corrupted file";
			logger.error(message, e);
			throw new HorarioError(1, message, e);
		}
		
		return profesores;
	}

	private Profesor getIdProfesor(List<Profesor> profesores, String nombre) {
		
		int cont = 0;
		Profesor profesor = null;
		while (cont < profesores.size() && profesor == null)
		{
			
			if (profesores.get(cont).getNombre().equals(nombre))
			{
				profesor = profesores.get(cont);
			}
			
		}
		
		return profesor;
	}
	
	private List<RolReaktor> getRoles(String part)
	{
		List<RolReaktor> lista = new ArrayList<RolReaktor>();

		String[] roles = part.split(",");

		for (String string : roles)
		{
			switch (string)
			{
			case "docente":
				lista.add(RolReaktor.docente);
				break;
			case "administrador":
				lista.add(RolReaktor.administrador);
				break;
			case "conserje":
				lista.add(RolReaktor.conserje);
				break;
			default:
				break;
			}
		}

		return lista;
	}
}
