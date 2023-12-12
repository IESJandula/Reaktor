package es.iesjandula.Horarios.rest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.Profesor;
import es.iesjandula.Horarios.models.RolReaktor;


@RequestMapping(value = "/horarios", produces = { "application/json" })
@RestController

/**
 * @author Alejandro Cazalla Pérez
 */
public class RestHandlerHorarios
{
	/**
	 * Public constructor
	 */
	public RestHandlerHorarios()
	{
		// Empty constructor
	}

	final static Logger logger = LogManager.getLogger();

	@RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = "multipart/form-data")
	public ResponseEntity<?> sendCsvTo(@RequestPart(value = "csvFile", required = true) final MultipartFile csvFile)
	{
		try
		{
			this.checkFile(csvFile);
			this.parseFile();
			return ResponseEntity.ok().build();
		} catch (HorarioError exception)
		{
			String message = "Computer error getting";
			logger.error(message, exception);
			return ResponseEntity.status(400).body(exception.getBodyExceptionMessage());
		} catch (Exception exception)
		{
			String message = "Server Error";
			logger.error(message, exception);
			return ResponseEntity.status(500).body(exception.getMessage());
		}
	}

	public void checkFile(MultipartFile file) throws HorarioError
	{
		if (file == null)
		{
			throw new HorarioError(1, "Error with the file");
		}
	}

	public List<Profesor> parseFile() throws HorarioError, IOException
	{
		FileInputStream fileInputStream = null;
		BufferedReader reader = null;
		List<Profesor> profesores = new ArrayList<Profesor>();
		int iteracion = 0;
		try
		{
			fileInputStream = new FileInputStream(".\\src\\main\\resources\\Profesores.csv");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			reader = new BufferedReader(inputStreamReader);

			String line = reader.readLine();

			while ((line != null))
			{
				String[] parts = line.split(";");

				String nombre = parts[1];
				String apellidos = parts[2];
				String cuentaDeCorreo = parts[3];
				List<RolReaktor> listaDeRoles = getRoles(parts[4]);

				profesores.add(new Profesor(nombre, apellidos, cuentaDeCorreo, listaDeRoles));
				iteracion++;

				line = reader.readLine();
			}
		} catch (IOException e)
		{
			String error = "Error en la lectura del fichero";
			logger.error(error, e);
			throw e;
		} catch (Exception e)
		{
			String error = "Error";
			logger.error(error, e);
			throw new HorarioError(1, error);
		} finally
		{
			try
			{
				if (fileInputStream != null)
				{
					fileInputStream.close();
				}
				if (reader != null)
				{
					reader.close();
				}
			} catch (IOException e)
			{
				String error = "Error en la lectura del fichero";
				logger.error(error, e);
				throw e;
			}
		}

		return profesores;
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
