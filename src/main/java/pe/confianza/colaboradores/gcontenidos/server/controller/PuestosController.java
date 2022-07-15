package pe.confianza.colaboradores.gcontenidos.server.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroPuesto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.service.PuestoService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/puestos")
public class PuestosController {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosController.class);
	
	@Autowired
	private PuestoService service;

	@ApiOperation(notes = "Consultar puestos disponibles", value = "url proxy /puestos")
	@PostMapping
	public ResponseEntity<ResponseStatus> consultar(@Valid @RequestBody RequestFiltroPuesto filtro) {
		logger.info("[BEGIN] consultar");
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(service.consultar(filtro));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
}
