package pe.confianza.colaboradores.gcontenidos.server.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/gastos")
@Api(value = "Gastos API REST Endpoint", description = "Operaciones con gastos")
public class GastosController {
	
	@Autowired
	private GestionarPresupuestoNegocio gestionarPresupuestoNegocio;
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de presupuestos anuales", value = "url proxy /gastos/prespuestos-anuales")
	@PostMapping("/prespuestos-anuales")
	public ResponseEntity<ResponseStatus> listarPresupuestosAnuales(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.listarPresupuestosAnuales(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
