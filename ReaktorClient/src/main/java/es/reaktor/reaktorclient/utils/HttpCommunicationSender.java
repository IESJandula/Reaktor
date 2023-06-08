package es.reaktor.reaktorclient.utils;

import es.reaktor.models.Malware;
import es.reaktor.reaktorclient.models.Reaktor;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * - Class -
 * This class is used to send a POST request to a URL
 */
@Service
@Slf4j
public class HttpCommunicationSender
{

    /**
     * - Service -
     * This attribute is used to convert an object to a JSON string
     */
    @Autowired
    private JsonUtils jsonUtils;

    public HttpGet createHttpGetWithOutHeader(String url)
    {
        HttpGet httpGet = new HttpGet(url); // Create a GET request

        return httpGet;
    }

    public List<Malware> sendGetMalware(HttpGet httpGet) throws ReaktorClientException
    {
        // HTTP client
        CloseableHttpResponse response; // HTTP response

        HttpEntity responseEntity; // Response entity

        String responseBody; // Response body

        try (CloseableHttpClient httpClient = HttpClients.createDefault())
        {

            response = httpClient.execute(httpGet); // Send the POST request

            responseEntity = response.getEntity();
            responseBody = EntityUtils.toString(responseEntity); // Get the response body

            return this.jsonUtils.fromJsonToList(responseBody, Malware.class);

        }
        catch (ClientProtocolException clientProtocolException)
        {
            log.error(ConstantsErrors.ERROR_CLIENT_PROTOCOL, clientProtocolException);
            throw new ReaktorClientException(ConstantsErrors.ERROR_CLIENT_PROTOCOL, "426", clientProtocolException);
        }
        catch (IOException ioException)
        {
            log.error(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, ioException);
            throw new ReaktorClientException(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, "500", ioException);
        }
        catch (Exception exception)
        {
            log.error(ConstantsErrors.ERROR_BY_DEFAULT, exception);
            throw new ReaktorClientException(ConstantsErrors.ERROR_BY_DEFAULT, "500", exception);
        }
    }

    /**
     * - Method -
     * This method is used to send a POST request to a URL
     *
     * @throws ReaktorClientException Exception thrown when there is an error sending the POST request to the URL specified
     */
    public String sendPost(HttpPost httpPost) throws ReaktorClientException
    {
        // HTTP client
        CloseableHttpResponse response; // HTTP response

        HttpEntity responseEntity; // Response entity

        String responseBody; // Response body

        try (CloseableHttpClient httpClient = HttpClients.createDefault())
        {

            response = httpClient.execute(httpPost); // Send the POST request

            responseEntity = response.getEntity();
            responseBody = EntityUtils.toString(responseEntity); // Get the response body

            return responseBody;
        }
        catch (ClientProtocolException clientProtocolException)
        {
            log.error(ConstantsErrors.ERROR_CLIENT_PROTOCOL, clientProtocolException);
            throw new ReaktorClientException(ConstantsErrors.ERROR_CLIENT_PROTOCOL, "426", clientProtocolException);
        }
        catch (IOException ioException)
        {
            log.error(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, ioException);
            throw new ReaktorClientException(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, "500", ioException);
        }
        catch (Exception exception)
        {
            log.error(ConstantsErrors.ERROR_BY_DEFAULT, exception);
            throw new ReaktorClientException(ConstantsErrors.ERROR_BY_DEFAULT, "500", exception);
        }
    }

    /**
     * - Method -
     * This method is used to create a POST request for send a Reaktor object to the server
     * @param urlDestino URL to send the POST request
     * @param reaktor Reaktor with the information PC
     * @return HttpPost with the POST request
     * @throws ReaktorClientException
     */
    public HttpPost createHttpPostReaktor(String urlDestino, Reaktor reaktor) throws ReaktorClientException
    {
        HttpPost httpPost = new HttpPost(urlDestino); // Create the HTTP POST request

        StringEntity entity = new StringEntity(this.jsonUtils.writeObjectToJsonAsStringPretty(reaktor), "UTF-8"); // Create the entity of the POST request

        entity.setContentType("application/json"); // Set the content type of the entity
        httpPost.setHeader("Content-type", "application/json"); // Set the content type of the POST request
        httpPost.setEntity(entity); // Set the entity of the POST request

        return httpPost;
    }

    public HttpPost createHttpPostMalwareList(String urlDestino, String serialNumber, List<Malware> malwareList) throws ReaktorClientException
    {
        HttpPost httpPost = new HttpPost(urlDestino); // Create the HTTP POST request

        StringEntity entity = new StringEntity(this.jsonUtils.writeObjectToJsonAsStringPretty(malwareList), "UTF-8"); // Create the entity of the POST request

        entity.setContentType("application/json"); // Set the content type of the entity
        httpPost.setHeader("Content-type", "application/json"); // Set the content type of the POST request
        httpPost.setHeader("serialNumber", serialNumber); // Set the Serial Number of the POST request
        httpPost.setEntity(entity); // Set the entity of the POST request

        return httpPost;
    }

    public HttpPost createHttpPostWithHeader(String urlDestino, String stringParam1, String param1)
    {
        HttpPost httpPost = new HttpPost(urlDestino); // Create the HTTP POST request

        httpPost.setHeader(stringParam1, param1); // Set the entity of the POST request

        return httpPost;
    }

}