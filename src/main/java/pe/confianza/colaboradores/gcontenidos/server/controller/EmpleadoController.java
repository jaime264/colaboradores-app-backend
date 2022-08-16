package pe.confianza.colaboradores.gcontenidos.server.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.CumpleanosRes;
import pe.confianza.colaboradores.gcontenidos.server.bean.InstruccionAcademica;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTerminosCondiciones;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

import org.springframework.security.access.annotation.Secured;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.web.bind.annotation.CrossOrigin;

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

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@RequestMapping("/empleado")
	public ResponseEntity<?> unidadoperativabydivision(@RequestBody RequestEmpleado empleado) throws IOException {
		logger.info("[BEGIN] unidadoperativabydivision");
		Gson gson = new Gson();
		Map<String, Object> resExcepcion = new HashMap<>();
		RequestEmpleado empleadoOut = new RequestEmpleado();
		
		try {
			//Validamos usuario de la sesión
			if (empleado.getLogAuditoria() != null) {
				if (empleado.getLogAuditoria().getUsuario() != null) {
					SecurityUtils.validateUserSession(empleado.getUsuarioBT());
				} else {
					resExcepcion.put("mensaje", "Usuario No Autorizado");
					resExcepcion.put("error", "Auditoria: Usuario No Autorizado");
					return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
				}
			} else {
				resExcepcion.put("mensaje", "Usuario No Autorizado");
				resExcepcion.put("error", "Es necesario los datos de auditoria");
				return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
			}
			
			HttpPost post = new HttpPost(perfilEmpleadoUrl);
			post.addHeader("content-type", "application/json");

			
			StringEntity params = new StringEntity(gson.toJson(empleado));
			post.setEntity(params);
			logger.info("Empleado: " + empleado.toString() + " API: " + perfilEmpleadoUrl);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				empleadoOut = gson.fromJson(result, RequestEmpleado.class);
			} else {
				logger.error("Error al obtener datos de Perfil: null");
				String jsonData = gson.toJson(empleado);
				auditoriaService.createAuditoria("002", "010", 99, "Error al obtener datos de Perfil: null", BsonDocument.parse(jsonData));
			}


		} catch (Exception e) {
			logger.error("Error al obtener datos de Perfil: " + e.getMessage());
			String jsonData = gson.toJson(empleado);
			auditoriaService.createAuditoria("002", "010", 99, "Error al obtener datos de Perfil: " + e.getMessage(), BsonDocument.parse(jsonData));
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				resExcepcion.put("mensaje", "Usuario No Autorizado");
				resExcepcion.put("error", "Usuario de sesión no concuerda con el de consulta");
				return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
			} else {
				resExcepcion.put("mensaje", "Error al obtener datos de Perfil");
				resExcepcion.put("error", e.getMessage());
				return new ResponseEntity<>(resExcepcion, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		
		
		
		logger.info("[END] unidadoperativabydivision");
		return new ResponseEntity<RequestEmpleado>(empleadoOut, HttpStatus.OK);
	}

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@RequestMapping("/empleado/instruccionacademica")
	public ResponseEntity<?> instruccionAcademica(@RequestBody RequestEmpleado empleado) throws IOException {
		Gson gson = new Gson();
		Map<String, Object> resExcepcion = new HashMap<>();
		List<InstruccionAcademica> instruccionAcademicaOut = new ArrayList<InstruccionAcademica>();
		try {
			//Validamos usuario de la sesión
			if (empleado.getLogAuditoria() != null) {
				if (empleado.getLogAuditoria().getUsuario() != null) {
					SecurityUtils.validateUserSession(empleado.getUsuarioBT());
				} else {
					resExcepcion.put("mensaje", "Usuario No Autorizado");
					resExcepcion.put("error", "Auditoria: Usuario No Autorizado");
					return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
				}
			} else {
				resExcepcion.put("mensaje", "Usuario No Autorizado");
				resExcepcion.put("error", "Es necesario los datos de auditoria");
				return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
			}
			
			HttpPost post = new HttpPost(instruccionAcademicaUrl);
			post.addHeader("content-type", "application/json");

			StringEntity params = new StringEntity(gson.toJson(empleado));
			post.setEntity(params);
			
			logger.info("Empleado: " + empleado.toString() + " API: " + instruccionAcademicaUrl);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity);
				Type listType = new TypeToken<ArrayList<InstruccionAcademica>>() { }.getType();
				instruccionAcademicaOut = gson.fromJson(result, listType);
			} else {
				logger.error("Error al obtener datos de Instrucción Académica: null");
				String jsonData = gson.toJson(empleado);
				auditoriaService.createAuditoria("002", "011", 99, "Error al obtener datos de Instrucción Académica: null", BsonDocument.parse(jsonData));
			}

		} catch (Exception e) {
			logger.error("Error al obtener datos de Instruccion Academica: " + e.getMessage());
			String jsonData = gson.toJson(empleado);
			auditoriaService.createAuditoria("002", "011", 99, "Error al obtener datos de Instruccion Academica: " + e.getMessage(), BsonDocument.parse(jsonData));
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				resExcepcion.put("mensaje", "Usuario No Autorizado");
				resExcepcion.put("error", "Usuario de sesión no concuerda con el de consulta");
				return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
			} else {
				resExcepcion.put("mensaje", "Error al obtener datos de Instruccion Academica");
				resExcepcion.put("error", e.getMessage());
				return new ResponseEntity<>(resExcepcion, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		return new ResponseEntity<List<InstruccionAcademica>>(instruccionAcademicaOut, HttpStatus.OK);
	}


	@PostMapping("/empleado/getEmpleadofechaNacimiento")
	public ResponseEntity<?> getEmpleadoFechaNacimiento() throws IOException {

		List<CumpleanosRes> listEmpleado = empleadoService.findfechaNacimientoDeHoy();

		logger.info("Lista empleado: " + listEmpleado.toString());
		return new ResponseEntity<List<CumpleanosRes>>(listEmpleado, HttpStatus.OK);
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
	
	@ApiOperation(notes = "Empleado accesos", value = "url proxy /empleadoaccesos")
	@PostMapping("empelado/accesos")
	public ResponseEntity<ResponseStatus> accesosPorUsuario(@RequestBody RequestAuditoria request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(empleadoService.consultaAccesos(request.getUsuarioOperacion()));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
