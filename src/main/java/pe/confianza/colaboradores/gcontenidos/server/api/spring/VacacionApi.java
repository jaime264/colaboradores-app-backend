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

import pe.confianza.colaboradores.gcontenidos.server.api.entity.ProgramacionVacacionesRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionRes;
import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;

@Component
public class VacacionApi {
	
	@Value("${rest.springConect.vacaciones}")
	private String urlConsultaProgramacionVacaciones;

	public VacacionRes getVacacion(String codigoSpring) {

		RestTemplate rt = new RestTemplate();
		ResponseEntity<VacacionRes> vac = rt.exchange("http://localhost:8081/api/test/vacacion", HttpMethod.POST,
				null, VacacionRes.class);

		VacacionRes response = vac.getBody();

		return response;
	}
	
	public List<ProgramacionVacacionesRes> obtenerVacacionesProgramadas(long idEmpleado) {
		Empleado empleado = new Empleado();
		empleado.setIdEmpleado(idEmpleado);
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Empleado> entity = new HttpEntity<Empleado>(empleado, headers);
		ResponseEntity<List<ProgramacionVacacionesRes>> response = rt.exchange(urlConsultaProgramacionVacaciones, HttpMethod.POST, entity, new ParameterizedTypeReference<List<ProgramacionVacacionesRes>>() {});
		if(response.getStatusCodeValue() == HttpStatus.OK.value())
			return response.getBody();
		throw new AppException("Error al obtener programacion de vacaciones");
	}
	
	

}
