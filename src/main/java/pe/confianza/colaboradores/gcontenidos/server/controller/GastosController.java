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
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCentroCostos;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestTipoGasto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.negocio.SolictudGastoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;



@RestController
@RequestMapping("/api/gastos")
@Api(value = "Gastos API REST Endpoint", description = "Operaciones con gastos")
public class GastosController {
	
	@Autowired
	private SolictudGastoNegocio solictudGastoNegocio;

	
	//SolictudGastoNegocio
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "listar tipo de gasto", value = "url proxy /tipogastolist")
	@PostMapping("/tipo-gasto")
	public ResponseEntity<ResponseStatus> listarTipoGasto(@Valid @RequestBody RequestTipoGasto peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.listarTipoGasto(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "listar concepto por tipo de gasto", value = "url proxy /conceptosbytipo")
	@PostMapping("/conceptos")
	public ResponseEntity<ResponseStatus> listarConceptoByTipoGasto(@Valid @RequestBody  RequestConcepto peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.listarConceptoByTipoGasto(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Listar centros de costo por agencia", value = "url proxy /centrocostobyagencia")
	@PostMapping("/centros-costo")
	public ResponseEntity<ResponseStatus> listarCentrosCostoByAgencia(@Valid @RequestBody  RequestCentroCostos peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.listarCentrosCostoByAgencia(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Registrar gasto", value = "url proxy /registrargasto")
	@PostMapping("/registrar-gasto")
	public ResponseEntity<ResponseStatus> registrarGastoEmpleado(@Valid @RequestBody  RequestGastoEmpleado gasto) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.registrarGastoEmpleado(gasto));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
