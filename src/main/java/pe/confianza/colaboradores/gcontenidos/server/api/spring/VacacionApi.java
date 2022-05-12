package pe.confianza.colaboradores.gcontenidos.server.api.spring;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionRes;

@Component
public class VacacionApi {

	public VacacionRes getVacacion(String codigoSpring) {

		RestTemplate rt = new RestTemplate();
		ResponseEntity<VacacionRes> vac = rt.exchange("http://localhost:8081/api/test/vacacion", HttpMethod.POST, null,
				VacacionRes.class);

		VacacionRes response = vac.getBody();

		return response;
	}

}
