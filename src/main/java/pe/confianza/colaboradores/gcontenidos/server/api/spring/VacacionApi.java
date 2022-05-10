package pe.confianza.colaboradores.gcontenidos.server.api.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionRes;
import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;

@Component
public class VacacionApi {

	public VacacionRes getVacacion(String codigoSpring) {

		RestTemplate rt = new RestTemplate();
		ResponseEntity<VacacionRes> vac = rt.exchange("http://localhost:8081/api/test/vacacion", HttpMethod.POST,
				null, VacacionRes.class);

		VacacionRes response = vac.getBody();

		return response;
	}
	
	
	
	

}
