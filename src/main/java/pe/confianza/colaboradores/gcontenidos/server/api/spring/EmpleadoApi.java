package pe.confianza.colaboradores.gcontenidos.server.api.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;

@Component
public class EmpleadoApi {
	
	
	@Value("${rest.perfilEmpleado.url}")
	private String urlEmpleado;
	
	public Empleado getPerfil(Empleado empleado) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Empleado> entity = new HttpEntity<Empleado>(empleado, headers);
		ResponseEntity<Empleado> response = rt.exchange(urlEmpleado, HttpMethod.POST, entity, Empleado.class);
		return response.getBody();
		
	}

}
