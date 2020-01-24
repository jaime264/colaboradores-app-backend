package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.InstruccionAcademica;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100",
		"http://localhost:4200", "http://172.20.9.12:7445" })
public class EmpleadoController {

	private static Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Value("${rest.perfilEmpleado.url}")
	private String perfilEmpleadoUrl;
	
	@Value("${rest.instruccion.academica.url}")
	private String instruccionAcademicaUrl;
	
	@RequestMapping("/empleado")
	public ResponseEntity<?> unidadoperativabydivision(@RequestBody Empleado empleado) throws IOException {
		
		HttpPost post = new HttpPost(perfilEmpleadoUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(empleado));
		post.setEntity(params);
		logger.info("Empleado: " + empleado.getIdEmpleado());
		Empleado empleadoOut = new Empleado();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

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
		}
		return new ResponseEntity<Empleado>(empleadoOut, HttpStatus.OK);
	}
	
	@RequestMapping("/empleado/instruccionacademica")
	public ResponseEntity<?> instruccionAcademica(@RequestBody Empleado empleado) throws IOException {
		
		HttpPost post = new HttpPost(instruccionAcademicaUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(empleado));
		post.setEntity(params);
		logger.info("Empleado: " + empleado.getIdEmpleado());
		List<InstruccionAcademica> instruccionAcademicaOut = new ArrayList<InstruccionAcademica>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

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
		}
		return new ResponseEntity<List<InstruccionAcademica>>(instruccionAcademicaOut, HttpStatus.OK);
	}
}
