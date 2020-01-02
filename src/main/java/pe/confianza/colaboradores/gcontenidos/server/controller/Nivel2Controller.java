package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;
import pe.confianza.colaboradores.gcontenidos.server.service.Nivel1Service;
import pe.confianza.colaboradores.gcontenidos.server.service.Nivel2Service;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://200.107.154.52:6020","http://localhost","http://localhost:8100","http://localhost:4200"})
public class Nivel2Controller {

	@Autowired
	private Nivel1Service _nivel1Service;
	
	@Autowired
	private Nivel2Service _nivel2Service;
	
	private static Logger logger = LoggerFactory.getLogger(Nivel2Controller.class);
	
	@PostMapping("/nivel2")
	public ResponseEntity<?> showNivel2ByNivel1(@RequestBody Nivel1 nivel1){
		List<Nivel2> lstNivel2 = null;
		Optional<Nivel1> niv1 = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			logger.info("Nivel2Controller");
			niv1 = _nivel1Service.findById(nivel1.getId());
			
			if(niv1 == null) {
				response.put("mensaje", "nivel 1 no existe en la base de datos!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				logger.info(niv1.toString());
				lstNivel2 = _nivel2Service.findByNivel1(nivel1.getId());
			}
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
		return new ResponseEntity<List<Nivel2>>(lstNivel2, HttpStatus.OK);
	}
}
