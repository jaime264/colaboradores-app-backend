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

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;
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
	public ResponseEntity<?> show(@RequestBody LogAuditoria logAuditoria) {
		List<Reaccion> lstReacciones = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			lstReacciones = reaccionService.listReacciones();
			Gson gson = new Gson();
			String jsonData = gson.toJson(logAuditoria);
			auditoriaService.createAuditoria("002", "012", 0, BsonDocument.parse(jsonData));
		} catch(DataAccessException e) {
			Gson gson = new Gson();
			String jsonData = gson.toJson(logAuditoria);
			auditoriaService.createAuditoria("002", "012", 99, BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstReacciones == null) {
			response.put("mensaje", "Reacciones no existen en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Reaccion>>(lstReacciones, HttpStatus.OK);
	}

}
