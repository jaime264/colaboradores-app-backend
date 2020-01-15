package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pe.confianza.colaboradores.gcontenidos.server.bean.CabeceraCTS;
import pe.confianza.colaboradores.gcontenidos.server.bean.DetalleCTS;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCTS;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100",
		"http://localhost:4200", "http://172.20.9.12:7445" })
@PropertySource(ignoreResourceNotFound = true, value = "classpath:gdc.properties")
public class CTSController {


	@Value("${rest.ctscab.url}")
	private String ctscabUrl;
	
	@Value("${rest.ctsdet.url}")
	private String ctsdetUrl;
	
	@RequestMapping("/ctscab")
	public ResponseEntity<?> ctscab(@RequestBody RequestCTS request) throws IOException {
		
		HttpPost post = new HttpPost(ctscabUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(request));
		post.setEntity(params);

		List<CabeceraCTS> listaCabecera = new ArrayList<CabeceraCTS>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				Type listType = new TypeToken<ArrayList<CabeceraCTS>>() { }.getType();
				listaCabecera = gson.fromJson(result, listType);
			}
		}
		return new ResponseEntity<List<CabeceraCTS>>(listaCabecera, HttpStatus.OK);
	}
	

	@RequestMapping("/ctsdet")
	public ResponseEntity<?> ctsdet(@RequestBody RequestCTS request) throws IOException {
		
		HttpPost post = new HttpPost(ctsdetUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(request));
		post.setEntity(params);
		
		DetalleCTS detalleCTSOut = new DetalleCTS();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				detalleCTSOut = gson.fromJson(result, DetalleCTS.class);
			}
		}
		return new ResponseEntity<DetalleCTS>(detalleCTSOut, HttpStatus.OK);
	}
}
