package pe.confianza.colaboradores.gcontenidos.server.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionesExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ExcepcionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/vacaciones/excepciones")
@Api(value = "Promacion de excepciones de vacaciones API REST", description = "Operaciones con excepciones de vacaciones")
public class VacacionExcepcionController {
	
	@Autowired
	private ExcepcionVacacionNegocio excepcionVacacionNegocio;
	
	@ApiOperation(notes = "Consultar programaciones", value = "url proxy /vacaciones/excepciones/programaciones")
	@PostMapping("/programaciones")
	public ResponseEntity<ResponseStatus> listarProgramaciones(@Valid @RequestBody RequestProgramacionesExcepcion filtro) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(excepcionVacacionNegocio.resumenProgramaciones(filtro));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Reprogramar programacion", value = "url proxy /vacaciones/excepciones/reprogramar")
	@PostMapping("/reprogramar")
	public ResponseEntity<ResponseStatus> reprogramar(@Valid @RequestBody RequestProgramacionExcepcion reprogramacion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		excepcionVacacionNegocio.reprogramar(reprogramacion);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
