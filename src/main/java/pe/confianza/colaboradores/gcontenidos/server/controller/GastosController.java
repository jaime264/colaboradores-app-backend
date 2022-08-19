package pe.confianza.colaboradores.gcontenidos.server.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCentroCostos;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoPresupuestoAnualDetalle;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestTipoGasto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.SolictudGastoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;



@RestController
@RequestMapping("/api/gastos")
@Api(value = "Gastos API REST Endpoint", description = "Operaciones con gastos")
public class GastosController {
	
	@Autowired
	private GestionarPresupuestoNegocio gestionarPresupuestoNegocio;
	
	@Autowired
	private SolictudGastoNegocio solictudGastoNegocio;

	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de presupuestos anueales", value = "url proxy /gastos/prespuestos-anuales")
	@PostMapping("/prespuestos-anuales")
	public ResponseEntity<ResponseStatus> listarPresupuestosAnuales(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.listarPresupuestosAnuales(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de presupuestos anuales - conceptos", value = "url proxy /gastos/prespuestos-anuales/distribuciones/conceptos")
	@PostMapping("/prespuestos-anuales/distribuciones/conceptos")
	public ResponseEntity<ResponseStatus> presupuestoAnualPorGlgAsignado(@Valid @RequestBody RequestGastoPresupuestoAnualDetalle peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.detalleDistribucionPresupuestoAnualPorGlgAsignado(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de tipos de distribucion de montos", value = "url proxy /gastos/prespuestos-anuales/distribuciones/conceptos/tipos-distribucion-monto")
	@PostMapping("/prespuestos-anuales/distribuciones/conceptos/tipos-distribucion-monto")
	public ResponseEntity<ResponseStatus> listarConceptosTiposDistribucionMonto(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.listarTiposDistribucionMonto(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de tipos de distribucion de montos", value = "url proxy /gastos/prespuestos-anuales/distribuciones/conceptos/frecuencias-distribucion")
	@PostMapping("/prespuestos-anuales/distribuciones/conceptos/frecuencias-distribucion")
	public ResponseEntity<ResponseStatus> listarConceptosFrecuenciasDistribucion(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.listarFrecuenciaDistribucion(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	

	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Configurar distribucion de conceptos de un GLG", value = "url proxy /gastos/prespuestos-anuales/distribuciones/conceptos/distribucion-configurar")
	@PostMapping(value = "/prespuestos-anuales/distribuciones/conceptos/distribucion-configurar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseStatus> configurarDistribucionConcepto(@Valid @RequestBody RequestDistribucionConcepto peticion, @RequestPart("excelDistribucion") MultipartFile excelDistribucion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		gestionarPresupuestoNegocio.configurarDistribucionConcepto(peticion, excelDistribucion);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	
	//SolictudGastoNegocio
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "listar tipo de gasto", value = "url proxy /gastos/tipo-gasto")
	@PostMapping("/tipo-gasto")
	public ResponseEntity<ResponseStatus> listarTipoGasto(@Valid @RequestBody RequestTipoGasto peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.listarTipoGasto(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "listar concepto por tipo de gasto", value = "url proxy /gastos/conceptos")
	@PostMapping("/conceptos")
	public ResponseEntity<ResponseStatus> listarConceptoByTipoGasto(@Valid @RequestBody  RequestConcepto peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.listarConceptoByTipoGasto(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Listar centros de costo por agencia", value = "url proxy /gastos/centros-costo")
	@PostMapping("/centros-costo")
	public ResponseEntity<ResponseStatus> listarCentrosCostoByAgencia(@Valid @RequestBody  RequestCentroCostos peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.listarCentrosCostoByAgencia(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Registrar gasto", value = "url proxy /gastos/registrar-gasto")
	@PostMapping("/registrar-gasto")
	public ResponseEntity<ResponseStatus> registrarGastoEmpleado(@Valid @RequestBody  RequestGastoEmpleado gasto) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(solictudGastoNegocio.registrarGastoEmpleado(gasto));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
