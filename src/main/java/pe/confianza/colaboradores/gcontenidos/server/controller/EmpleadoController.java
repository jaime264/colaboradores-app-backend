package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.InstruccionAcademica;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTerminosCondiciones;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class EmpleadoController {

	private static Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

	@Autowired
	private AuditoriaService auditoriaService;

	@Value("${rest.perfilEmpleado.url}")
	private String perfilEmpleadoUrl;

	@Value("${rest.instruccion.academica.url}")
	private String instruccionAcademicaUrl;

	@Autowired
	private EmpleadoService empleadoService;

	@RequestMapping("/empleado")
	public ResponseEntity<?> unidadoperativabydivision(@RequestBody RequestEmpleado empleado) throws IOException {
		logger.info("[BEGIN] unidadoperativabydivision");

		HttpPost post = new HttpPost(perfilEmpleadoUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(empleado));
		post.setEntity(params);
		logger.info("Empleado: " + empleado.toString() + " API: " + perfilEmpleadoUrl);
		RequestEmpleado empleadoOut = new RequestEmpleado();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				empleadoOut = gson.fromJson(result, RequestEmpleado.class);
			} else {
				logger.info("Error al obtener datos de Perfil: null");
				String jsonData = gson.toJson(empleado);
				auditoriaService.createAuditoria("002", "010", 99, "Error al obtener datos de Perfil: null",
						BsonDocument.parse(jsonData));
			}
		} catch (Exception e) {
			logger.error("Error al obtener datos de Perfil ", e);
			String jsonData = gson.toJson(empleado);
			auditoriaService.createAuditoria("002", "010", 99, "Error al obtener datos de Perfil: " + e.getMessage(),
					BsonDocument.parse(jsonData));
		}
		logger.info("[END] unidadoperativabydivision");
		return new ResponseEntity<RequestEmpleado>(empleadoOut, HttpStatus.OK);
	}

	@RequestMapping("/empleado/instruccionacademica")
	public ResponseEntity<?> instruccionAcademica(@RequestBody RequestEmpleado empleado) throws IOException {

		HttpPost post = new HttpPost(instruccionAcademicaUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(empleado));
		post.setEntity(params);
		logger.info("Empleado: " + empleado.toString() + " API: " + instruccionAcademicaUrl);
		List<InstruccionAcademica> instruccionAcademicaOut = new ArrayList<InstruccionAcademica>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				Type listType = new TypeToken<ArrayList<InstruccionAcademica>>() {
				}.getType();
				instruccionAcademicaOut = gson.fromJson(result, listType);
			} else {
				logger.info("Error al obtener datos de Instrucción Académica: null");
				String jsonData = gson.toJson(empleado);
				auditoriaService.createAuditoria("002", "011", 99,
						"Error al obtener datos de Instrucción Académica: null", BsonDocument.parse(jsonData));
			}
		} catch (Exception e) {
			logger.error("Error al obtener datos de Instruccion Academica", e);
			String jsonData = gson.toJson(empleado);
			auditoriaService.createAuditoria("002", "011", 99,
					"Error al obtener datos de Instruccion Academica: " + e.getMessage(), BsonDocument.parse(jsonData));
		}
		return new ResponseEntity<List<InstruccionAcademica>>(instruccionAcademicaOut, HttpStatus.OK);
	}

	@PostMapping("/empleado/getEmpleadofechaNacimiento")
	public ResponseEntity<?> getEmpleadoFechaNacimiento() throws IOException {

		List<Empleado> listEmpleado = empleadoService.findfechaNacimientoDeHoy();

		logger.info("Lista empleado: " + listEmpleado.toString());
		return new ResponseEntity<List<Empleado>>(listEmpleado, HttpStatus.OK);
	}
	
	@PostMapping("/empleado/get/codigo")
	public ResponseEntity<?> getEmpleadoByCodigo(Long codigo) throws IOException {

		Empleado empleado = empleadoService.buscarPorCodigo(codigo);

		logger.info("empleado: " + empleado.toString());
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@PostMapping("/empleado/get/usuario")
	public ResponseEntity<?> getEmpleadoByUsuarioBt(String usuariobt) throws IOException {

		Empleado empleado = empleadoService.buscarPorUsuarioBT(usuariobt);

		logger.info("empleado: " + empleado.toString());
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Empleado acepta los terminos y conficiones", value = "url proxy /empleadoaceptartc")
	@PostMapping("empelado/aceptatc")
	public ResponseEntity<ResponseStatus> aceptarTerminosCondiciones(@RequestBody RequestAuditoria request) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseTerminosCondiciones body = empleadoService.aceptarTerminosCondiciones(request.getUsuarioOperacion());
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(body);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(notes = "Empleado consulta los terminos y conficiones", value = "url proxy /empleadoconsultatc")
	@PostMapping("empelado/consultatc")
	public ResponseEntity<ResponseStatus> consultarTerminosCondiciones(@RequestBody RequestAuditoria request) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseTerminosCondiciones body = empleadoService.consultarTerminosCondiciones(request.getUsuarioOperacion());
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(body);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
