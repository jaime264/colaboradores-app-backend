package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Evento;
import pe.confianza.colaboradores.gcontenidos.server.service.EventoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://200.107.154.52:6020","http://localhost","http://localhost:8100","http://localhost:4200"})
public class EventosController {
	
	@Autowired
	private EventoService eventoService;
	
	@PostMapping("/eventos/list")
	public ResponseEntity<?> show() {
		List<Evento> lstEventos = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			lstEventos = eventoService.listEventos(); 
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstEventos == null) {
			response.put("mensaje", "Eventos no existen en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Evento>>(lstEventos, HttpStatus.OK);
	}

}
