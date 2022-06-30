package pe.confianza.colaboradores.gcontenidos.server.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConsultaVacacionesReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ReprogramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/vacaciones/reprogramacion")
@Api(value = "Reprogramación de vacaciones API REST", description = "Operaciones con reprogramación de vacaciones")
public class VacacionesReprogramacionController {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionesReprogramacionController.class);
	
	@Autowired
	private ReprogramacionVacacionNegocio reprogramacionService;
	
	@ApiOperation(notes = "Lista de programaciones de del año", value = "url proxy /vacaciones/reprogramcion/programacion-anio")
	@PostMapping("/programacion-anio")
	public ResponseEntity<ResponseStatus> consultarProgramaciones(@Valid @RequestBody RequestConsultaVacacionesReprogramar request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(reprogramacionService.programacionAnual(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
