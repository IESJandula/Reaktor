package es.monitoringserver.monitoringclient.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.monitoringserver.models.Configuration;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
@Slf4j
public class ReadFiles
{

    /**
     * Method readFileConfiguration
     * @return
     */
    public String readFileConfiguration()
    {
        File file               = new File(Constants.PATH_CONFIG_FILE);
        String contentJson      = "";

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                contentJson += scanner.nextLine();
            }
        } catch (FileNotFoundException fileNotFoundException)
        {
            log.error("Error al leer el fichero");
        }
        return contentJson;
    }

    /**
     * Method readConfiguration
     * @return
     */
    public Configuration readConfiguration()
    {
        ObjectMapper mapper = Json.mapper();
        Configuration configuration = null;
        try
        {
            configuration = mapper.readValue(this.readFileConfiguration(), new TypeReference<>()
            {
            });
        } catch (JsonProcessingException jsonProcessingException)
        {
            log.error("Error processing arguments to json document");
            configuration = new Configuration("unknow","unknow","unknow","unknow","unknow","unknow",false);
        }

        return configuration;
    }

}
