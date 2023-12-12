package es.reaktor.reaktorclient.scheduled_task;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientRequest
{
	public void headerRequest() throws Exception
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String classroom = "2º DAM";
		String trolley = "Carrito";
		String professor = "Paco Benitez";
		String url = "http://localhost:8084/computers/admin/software";
		
		HttpPost request = new HttpPost(url);
		CloseableHttpResponse response = httpClient.execute(request);
		request.addHeader("classroom","2º DAM");
		request.addHeader("trolley","Carrito");
		request.addHeader("professor","Paco Benitez");	
	}
}
