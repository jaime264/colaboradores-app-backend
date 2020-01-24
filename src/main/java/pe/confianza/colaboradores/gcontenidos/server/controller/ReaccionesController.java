package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReaccion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Reaccion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.ReaccionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://200.107.154.52:6020","http://localhost","http://localhost:8100","http://localhost:4200", "http://172.20.9.12:7445"})
public class ReaccionesController {
	
	@Autowired
	private ReaccionService reaccionService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@PostMapping("/reacciones/list")
	public ResponseEntity<?> show(@RequestBody RequestReaccion requestReaccion) {
		List<Reaccion> lstReacciones = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		
		try {
			lstReacciones = reaccionService.listReacciones();
		} catch(DataAccessException e) {
			String mensaje = "Error al realizar la consulta en la base de datos";
			String jsonData = gson.toJson(requestReaccion);
			auditoriaService.createAuditoria("002", "012", 99, mensaje + ": " + e.getMessage(),BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstReacciones == null) {
			String mensaje =  "Reacciones no existen en la base de datos!";
			String jsonData = gson.toJson(requestReaccion);
			auditoriaService.createAuditoria("002", "012", 0, mensaje, BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			String jsonData = gson.toJson(requestReaccion);
			auditoriaService.createAuditoria("002", "012", 0, "OK", BsonDocument.parse(jsonData));
		}
		
		return new ResponseEntity<List<Reaccion>>(lstReacciones, HttpStatus.OK);
	}

}
