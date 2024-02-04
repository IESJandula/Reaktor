package es.iesjandula.reaktor.monitoring_client.utils;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;

import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ConstantsErrors;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Slf4j
public class WriteFiles
{

    /**
     * Method escribirResultadoJson
     * @param objectToJson
     * @throws ReaktorClientException
     */
    public void escribirResultadoJson(Object objectToJson) throws ReaktorClientException
    {
        try
        {
            ObjectWriter writer = Json.mapper().writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(Constants.PATH_CONFIG_FILE), objectToJson);
        }
        catch (IOException streamWriteException)
        {
        	
            throw new ReaktorClientException(ConstantsErrors.ERROR_WRITE_FILE, "500", streamWriteException);
        }
    }

    /**
     * Method crearScriptMalware
     * @param script
     * @throws ReaktorClientException
     */
    public void crearScriptMalware(String script) throws ReaktorClientException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PATH_MALWARE_FILE)))
        {
            File file = new File(Constants.PATH_MALWARE_FILE);
            if (!file.exists())
            {
                file.createNewFile();
            }
            writer.write(script);
        }
        catch (IOException streamWriteException)
        {
        	log.error(ConstantsErrors.ERROR_WRITE_FILE);
            throw new ReaktorClientException(ConstantsErrors.ERROR_WRITE_FILE, "500", streamWriteException);
        }
    }

    /**
     * Method deleteScriptMalware
     */
    public void deleteScriptMalware()
    {
        File file = new File(Constants.PATH_MALWARE_FILE);
        if (file.exists())
        {
            file.delete();
        }
    }

}
