package pe.confianza.colaboradores.gcontenidos.server.api.spring;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmpleadoRes;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;

@Component
public class EmpleadoApi {
	
	private static Logger logger = LoggerFactory.getLogger(EmpleadoApi.class);
	
	@Value("${rest.springConnect.perfilEmpleado}")
	private String urlEmpleado;
	
	public EmpleadoRes getPerfil(String usuarioBT) {
		logger.info("[BEGIN] getPerfil {}", new Object[] {usuarioBT});
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("usuarioBT", usuarioBT.trim());
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(requestBody, headers);
		ResponseEntity<EmpleadoRes> response = rt.exchange(urlEmpleado, HttpMethod.POST, entity, EmpleadoRes.class);
		if(response.getStatusCodeValue() == HttpStatus.OK.value())
			return response.getBody();
		throw new AppException("Error al obtener información del empleado");
	}

}
