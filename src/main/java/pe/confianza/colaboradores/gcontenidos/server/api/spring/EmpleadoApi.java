package pe.confianza.colaboradores.gcontenidos.server.api.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;

@Component
public class EmpleadoApi {
	
	
	@Value("${rest.perfilEmpleado.url}")
	private String urlEmpleado;
	
	public Empleado getPerfil(Empleado empleado) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Empleado> entity = new HttpEntity<Empleado>(empleado, headers);
		ResponseEntity<Empleado> response = rt.exchange(urlEmpleado, HttpMethod.POST, entity, Empleado.class);
		if(response.getStatusCodeValue() == HttpStatus.OK.value())
			return response.getBody();
		throw new AppException("Error al obtener información del empleado");
	}
	
	

}
