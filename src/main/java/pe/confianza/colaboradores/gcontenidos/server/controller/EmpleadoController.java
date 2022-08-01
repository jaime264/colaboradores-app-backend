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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.InstruccionAcademica;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
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

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Value("${rest.perfilEmpleado.url}")
	private String perfilEmpleadoUrl;
	
	@Value("${rest.instruccion.academica.url}")
	private String instruccionAcademicaUrl;

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@RequestMapping("/empleado")
	public ResponseEntity<?> unidadoperativabydivision(@RequestBody Empleado empleado) throws IOException {
		Gson gson = new Gson();
		Map<String, Object> resExcepcion = new HashMap<>();

		Empleado empleadoOut = new Empleado();
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

			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				empleadoOut = gson.fromJson(result, Empleado.class);
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
		return new ResponseEntity<>(empleadoOut, HttpStatus.OK);
	}

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@RequestMapping("/empleado/instruccionacademica")
	public ResponseEntity<?> instruccionAcademica(@RequestBody Empleado empleado) throws IOException {
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
			logger.info("Empleado: " + empleado.getIdEmpleado());

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
		return new ResponseEntity<>(instruccionAcademicaOut, HttpStatus.OK);
	}
}
