package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestBuscarPorId;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class ParametrosController {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosController.class);
	
	@Autowired
	private ParametrosService parametrosService;
	
	@PostMapping("/parametro/list")
	public ResponseEntity<?> showParams() {
		List<Parametro> listParams = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			listParams = parametrosService.listParams();
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(listParams == null) {
			String mensaje = "Parametros no existen en la base de datos!";
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Parametro>>(listParams, HttpStatus.OK);
	}
	
	@PostMapping("/parametro/registrar")
	public ResponseEntity<ResponseStatus> registroParametro(@Valid @RequestBody RequestParametro request) {
		logger.info("Parametro: {} valor {}" , new Object[] { request.getCodigo(), request.getValor() });
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseParametro parametro = parametrosService.registrar(request);
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(parametro);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/parametro")
	public ResponseEntity<ResponseStatus> obtenerParametro(@RequestBody RequestBuscarPorId request) {
		logger.info("Obtener parametro con id: {} " , new Object[] { request.getId() });
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(parametrosService.buscarPorId(request.getId()));
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
