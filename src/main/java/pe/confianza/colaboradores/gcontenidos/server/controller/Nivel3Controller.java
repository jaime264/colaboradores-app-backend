package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel3;
import pe.confianza.colaboradores.gcontenidos.server.service.Nivel2Service;
import pe.confianza.colaboradores.gcontenidos.server.service.Nivel3Service;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://200.107.154.52:6020","http://localhost","http://localhost:8100","http://localhost:4200"})
public class Nivel3Controller {
	
	@Autowired
	private Nivel2Service _nivel2Service;
	
	@Autowired
	private Nivel3Service _nivel3Service;
	
	private static Logger logger = LoggerFactory.getLogger(Nivel3Controller.class);
	
	@PostMapping("/nivel3")
	public ResponseEntity<?> showNivel3ByNivel2(@RequestBody Nivel2 nivel2) {
		List<Nivel3> lstNivel3 = null;
		Nivel2 niv2 = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			logger.info("Nivel3Controller");
			niv2 = _nivel2Service.findById(nivel2.getId());
			
			if(niv2 == null) {
				logger.info("devuelve null");
				response.put("mensaje", "nivel 2 no existe en la base de datos!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				logger.info("Nivel2: " + niv2.toString());
				lstNivel3 = _nivel3Service.findByNivel2(niv2);
				logger.info("size lstNivel3: " + lstNivel3.size());
				logger.info("Nivel3[0] desc nivel1: " + lstNivel3.get(0).getNivel2().getNivel1().getDescripcion());
			}
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
		return new ResponseEntity<List<Nivel3>>(lstNivel3, HttpStatus.OK);
	}

}
