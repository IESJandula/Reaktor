package es.monitoringserver.monitoringclient.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import es.monitoringserver.monitoringclient.utils.exceptions.ConstantsErrors;
import es.monitoringserver.monitoringclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static es.monitoringserver.monitoringclient.utils.Json.mapper;

@Service
@Slf4j
public class JsonUtils
{

    /**
     * Parse an object to JSON in Pretty format
     *
     * @param objectToJson object to be parsed
     * @return String with JSON in Pretty format
     */
    public String writeObjectToJsonAsStringPretty(Object objectToJson) throws ReaktorClientException
    {
        String resultJsonAsString = "";
        try
        {
            // Write Result Pretty Format
            resultJsonAsString = mapper().writerWithDefaultPrettyPrinter().writeValueAsString(objectToJson);
        } catch (JsonProcessingException jsonProcessingException)
        {
            log.error(ConstantsErrors.ERROR_PARSING_OBJECT_TO_JSON, jsonProcessingException);
            throw new ReaktorClientException(ConstantsErrors.ERROR_PARSING_OBJECT_TO_JSON, "Error a la Hora de transformar el objeto a Json", jsonProcessingException);
        }

        return resultJsonAsString;
    }

    public <T> List<T> fromJsonToList(String json, Class<T> clazz) throws Exception {
        List<T> list;
        if (json.trim().equals("[]")) {
            list = Collections.emptyList();
        } else {
            list = Json.mapper().readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        }
        return list;
    }

}
