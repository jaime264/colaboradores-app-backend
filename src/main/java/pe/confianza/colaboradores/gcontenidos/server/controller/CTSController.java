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
import pe.confianza.colaboradores.gcontenidos.server.bean.CabeceraCTS;
import pe.confianza.colaboradores.gcontenidos.server.bean.DetalleCTS;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCTS;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class CTSController {
	
	private static final Logger logger = LoggerFactory.getLogger(CTSController.class);
	
	@Autowired
	private AuditoriaService auditoriaService;

	@Value("${rest.ctscab.url}")
	private String ctscabUrl;
	
	@Value("${rest.ctsdet.url}")
	private String ctsdetUrl;

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@RequestMapping("/ctscab")
	public ResponseEntity<?> ctscab(@RequestBody RequestCTS request) throws IOException {
		Map<String, Object> resExcepcion = new HashMap<>();
		Gson gson = new Gson();
		List<CabeceraCTS> listaCabecera = new ArrayList<CabeceraCTS>();

		try{
			//Validamos usuario de la sesión
			if (request.getLogAuditoria() != null) {
				if (request.getLogAuditoria().getUsuario() != null) {
					SecurityUtils.validateUserSession(request.getLogAuditoria().getUsuario());
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

			HttpPost post = new HttpPost(ctscabUrl);
			post.addHeader("content-type", "application/json");
			StringEntity params = new StringEntity(gson.toJson(request));
			post.setEntity(params);
			logger.info("Año: " + request.getAnio());
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				Type listType = new TypeToken<ArrayList<CabeceraCTS>>() { }.getType();
				listaCabecera = gson.fromJson(result, listType);
				String jsonData = gson.toJson(request);
				auditoriaService.createAuditoria("002", "007", 0, "OK", BsonDocument.parse(jsonData));
			} else {
				String jsonData = gson.toJson(request);
				auditoriaService.createAuditoria("002", "007", 99, "Error al obtener datos de cabecera CTS: null", BsonDocument.parse(jsonData));
			}
		} catch (Exception e) {
			logger.error("Error al obtener datos de cabecera CTS: " + e.getMessage());
			String jsonData = gson.toJson(request);
			auditoriaService.createAuditoria("002", "007", 99, "Error al obtener datos de cabecera CTS: " + e.getMessage(), BsonDocument.parse(jsonData));
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				resExcepcion.put("mensaje", "Usuario No Autorizado");
				resExcepcion.put("error", "Usuario de sesión no concuerda con el de consulta");
				return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
			} else {
				resExcepcion.put("mensaje", "Error al obtener datos de cabecera CTS");
				resExcepcion.put("error", e.getMessage());
				return new ResponseEntity<>(resExcepcion, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(listaCabecera, HttpStatus.OK);
	}

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@RequestMapping("/ctsdet")
	public ResponseEntity<?> ctsdet(@RequestBody RequestCTS request) throws IOException {
		Map<String, Object> resExcepcion = new HashMap<>();
		Gson gson = new Gson();
		DetalleCTS detalleCTSOut = new DetalleCTS();
		try {
			//Validamos usuario de la sesión
			if (request.getLogAuditoria() != null) {
				if (request.getLogAuditoria().getUsuario() != null) {
					SecurityUtils.validateUserSession(request.getLogAuditoria().getUsuario());
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

			HttpPost post = new HttpPost(ctsdetUrl);
			post.addHeader("content-type", "application/json");
			logger.info("Empleado: " + request.getIdEmpleado());
			StringEntity params = new StringEntity(gson.toJson(request));
			post.setEntity(params);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				detalleCTSOut = gson.fromJson(result, DetalleCTS.class);
				String jsonData = gson.toJson(request);
				auditoriaService.createAuditoria("002", "007", 0, "OK", BsonDocument.parse(jsonData));
			} else {
				String jsonData = gson.toJson(request);
				auditoriaService.createAuditoria("002", "007", 99, "Error al obtener datos de detalle CTS: null", BsonDocument.parse(jsonData));
			}
		} catch (Exception e) {
			logger.error("Error al obtener datos de detalle CTS: " + e.getMessage());
			String jsonData = gson.toJson(request);
			auditoriaService.createAuditoria("002", "007", 99, "Error al obtener datos de detalle CTS: " + e.getMessage(), BsonDocument.parse(jsonData));
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				resExcepcion.put("mensaje", "Usuario No Autorizado");
				resExcepcion.put("error", "Usuario de sesión no concuerda con el de consulta");
				return new ResponseEntity<>(resExcepcion, HttpStatus.UNAUTHORIZED);
			} else {
				resExcepcion.put("mensaje", "Error al obtener datos de detalle CTS");
				resExcepcion.put("error", e.getMessage());
				return new ResponseEntity<>(resExcepcion, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(detalleCTSOut, HttpStatus.OK);
	}
}
