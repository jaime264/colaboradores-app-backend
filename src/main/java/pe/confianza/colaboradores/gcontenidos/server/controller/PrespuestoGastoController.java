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
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPresupuestoTipoGastoResumen;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/prespupuestos")
@Api(value = "Gestión de presupuestos API REST Endpoint", description = "Operaciones con prespuestos de gastos")
public class PrespuestoGastoController {

	@Autowired
	private GestionarPresupuestoNegocio gestionarPresupuestoNegocio;
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consultar presupuestos generales", value = "url proxy /prespupuestos")
	@PostMapping
	public ResponseEntity<ResponseStatus> listarPresupuestosAnuales(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.listarPresupuestosGenerales(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de presupuesto por tipo de gasto", value = "url proxy /prespupuestos/tipo-gasto/resumen")
	@PostMapping("/tipo-gasto/resumen")
	public ResponseEntity<ResponseStatus> presupuestoAnualPorGlgAsignado(@Valid @RequestBody RequestPresupuestoTipoGastoResumen peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.detallePresupuestoTipoGastoPorGlgAsignado(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de tipos de distribucion de montos", value = "url proxy /presupuestos/tipos-distribucion-monto")
	@PostMapping("/tipos-distribucion-monto")
	public ResponseEntity<ResponseStatus> listarConceptosTiposDistribucionMonto(@Valid @RequestBody  RequestAuditoriaBase peticion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(gestionarPresupuestoNegocio.listarTiposDistribucionMonto(peticion));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de tipos de distribucion de montos", value = "url proxy /presupuestos/frecuencias-distribucion")
	@PostMapping("/frecuencias-distribucion")
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
}
