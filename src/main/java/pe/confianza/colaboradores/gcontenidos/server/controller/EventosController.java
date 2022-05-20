package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestEvento;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Evento;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.EventoService;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class EventosController {
	
	private static Logger logger = LoggerFactory.getLogger(EventosController.class);
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@PostMapping("/eventos/list")
	public ResponseEntity<?> show(@RequestBody RequestEvento requestEvento) {
		List<Evento> lstEventos = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		try {
			lstEventos = eventoService.listEventos();
		} catch(Exception e) {
			logger.error("Error al obtener eventos: " + e.getMessage());
			String mensaje = "Error al realizar la consulta en la base de datos";
			String jsonData = gson.toJson(requestEvento);
			auditoriaService.createAuditoria("002", "009", 99, mensaje + ": " + e.getMessage(), BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
		
		return new ResponseEntity<List<Evento>>(lstEventos, HttpStatus.OK);
	}

}
