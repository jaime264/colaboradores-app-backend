package pe.confianza.colaboradores.gcontenidos.server.api.spring;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.ResponseExample;

import java.util.List;

@Component
public class ExampleApi {

	public void test_whenList() {
		RestTemplate rt = new RestTemplate();
		ResponseEntity<List<ResponseExample>> exchange = rt.exchange("https://jsonplaceholder.typicode.com/posts",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseExample>>() {
				});
		List<ResponseExample> body = exchange.getBody();
		ResponseExample response = body.stream().findFirst().orElse(null);

	}

	public void test_whenObject() {
		RestTemplate rt = new RestTemplate();
		ResponseEntity<ResponseExample> exchange = rt.exchange("https://jsonplaceholder.typicode.com/posts/4",
				HttpMethod.GET, null, ResponseExample.class);
		ResponseExample response = exchange.getBody();
	}

}
