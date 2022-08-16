package pe.confianza.colaboradores.gcontenidos.server.controller;

import com.google.gson.Gson;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestEvento;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Evento;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.EventoService;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class EventosController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventosController.class);
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@PostMapping("/eventos/list")
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	public ResponseEntity<?> show(@RequestBody RequestEvento requestEvento) {
		List<Evento> lstEventos = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		try {
			//Validamos usuario de la sesión
			if (requestEvento.getLogAuditoria() != null) {
				if (requestEvento.getLogAuditoria().getUsuario() != null) {
					SecurityUtils.validateUserSession(requestEvento.getLogAuditoria().getUsuario());
				} else {
					response.put("mensaje", "Usuario No Autorizado");
					response.put("error", "Auditoria: Usuario No Autorizado");
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				}
			} else {
				response.put("mensaje", "Usuario No Autorizado");
				response.put("error", "Es necesario los datos de auditoria");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

			lstEventos = eventoService.listEventos();
			if(lstEventos == null) {
				String mensaje = "Eventos no existen en la base de datos: null";
				String jsonData = gson.toJson(requestEvento);
				auditoriaService.createAuditoria("002", "009", 0, mensaje ,BsonDocument.parse(jsonData));
				response.put("mensaje", mensaje);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				String jsonData = gson.toJson(requestEvento);
				auditoriaService.createAuditoria("002", "009", 0, "OK", BsonDocument.parse(jsonData));
			}
		} catch(Exception e) {
			logger.error("Error al obtener eventos: " + e.getMessage());
			String mensaje = "Error al realizar la consulta en la base de datos";
			String jsonData = gson.toJson(requestEvento);
			auditoriaService.createAuditoria("002", "009", 99, mensaje + ": " + e.getMessage(), BsonDocument.parse(jsonData));
			if (e.getMessage().equals(SecurityUtils.UNAUTHORIZED)) {
				response.put("mensaje", "Usuario No Autorizado");
				response.put("error", "Usuario de sesión no concuerda con el de consulta");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			} else {
				response.put("mensaje", mensaje);
				response.put("error", e.getMessage());
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(lstEventos, HttpStatus.OK);
	}

}
