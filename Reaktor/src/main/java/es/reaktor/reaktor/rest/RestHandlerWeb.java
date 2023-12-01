package es.reaktor.reaktor.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import es.reaktor.reaktor.exceptions.ComputerError;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/*
 * @author Miguel Rios
 */

@Controller
@RequestMapping("/web/screenshot")
public class RestHandlerWeb
{
	public ResponseEntity<?> getComputersScreens(
			@RequestHeader (value = "classroom", required = false) String classroom,
			@RequestHeader (value = "trolley", required = false) String trolley,
			@RequestHeader (value = "plant", required = false)Integer plant,
			@RequestHeader (value = "professor", required = false)String professor) throws Exception
	{
		try
		{
			this.checkParams(professor, classroom, trolley, plant);
			
			File zipFile = getZipFile(classroom, trolley, plant, professor);
			

		} catch (ComputerError error)
		{
			return ResponseEntity.status(404).body(error.getBodyExceptionMessage());
		}
		catch (Exception ex) {
			return ResponseEntity.status(500).body("Internal Error");
		}
		return ResponseEntity.ok("Se a completado correctamente");
	}
	
	private void checkParams(String serialNumber,String classroom,String trolley, Integer plant) throws ComputerError
    {
        if(serialNumber.isEmpty() && classroom.isEmpty() && trolley.isEmpty() && plant==null)
        {
            throw new ComputerError(2, "All params can't be null");
        }
    }
	
	private File getZipFile(String classroom, String trolley, int plant, String professor) throws Exception
	{

		File zipFile = File.createTempFile("screenshots", ".zip");
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile)))
		{
			zipOutputStream.putNextEntry(new ZipEntry("screenshot.png"));
			zipOutputStream.write("Contenido de la captura de pantalla".getBytes());
			zipOutputStream.closeEntry();
		}
		return zipFile;
	}
}