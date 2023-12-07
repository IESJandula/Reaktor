package es.reaktor.horarios.rest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import es.reaktor.horarios.models.ObjetoCsv;

public class HorariosRest 
{
	public void procesarArchivoCSV(String nombreArchivo) 
	{
        FileReader fileReader = null;
        BufferedReader br = null;

        try 
        {
            fileReader = new FileReader(nombreArchivo);
            br = new BufferedReader(fileReader);

            String line;
            while ((line = br.readLine()) != null) 
            {
                
                ObjetoCsv objeto = parsearLineaCSV(line);
                //Imprimir el objeto de ejemplo
                System.out.println(objeto);
            }
        } catch (IOException e) 
        {
            // Manejar excepciones de lectura de archivos
            e.printStackTrace();
        } finally 
        {
            // Cerrar FileReader y BufferedReader en el bloque finally
            try 
            {
                if (br != null) 
                {
                    br.close();
                }
                if (fileReader != null) 
                {
                    fileReader.close();
                }
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    private ObjetoCsv parsearLineaCSV(String linea) 
    {
       
        String[] campos = linea.split(",");
        ObjetoCsv objeto = null;
        try
        {
        	if (campos.length >= 4) 
            {
                String nombre = campos[0].trim();
                String apellidos = campos[1].trim();
                String correo = campos[2].trim();

                List<String> roles = List.of(campos[3].trim().split(";"));

                objeto = new ObjetoCsv(nombre, apellidos, correo, roles);
            }
        }catch(IllegalArgumentException illegalArgumentException)
        {
        	String error = "Datos de CSV incompletos o incorrectos: " + linea;
        	
        	System.out.println(error);
        }
        return objeto;
    }
}

