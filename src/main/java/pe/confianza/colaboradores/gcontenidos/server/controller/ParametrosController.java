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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.RequestParametroActualizacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAccesoReporteActualizacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAccesoReporteEliminar;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAccesoReporteRegistro;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestBuscarPorId;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReporteAcceso;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestModificarMetaVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.config.AuthoritiesConstants;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api")
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
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consultar tipos de parametros para vacaciones", value = "url proxy /parametros/vacaciones/tipos")
	@GetMapping("/parametros/vacaciones/tipos")
	public ResponseEntity<ResponseStatus> listarTipoParametrosVacaciones() {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(parametrosService.listaParametrovaccionesTipos());
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consultar para de vacaciones por tipo", value = "url proxy /parametros/vacaciones/{tipo}")
	@GetMapping("/parametros/vacaciones/{tipo}")
	public ResponseEntity<ResponseStatus> listarParametrosVacacionesPorTipo(@PathVariable String tipo){
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(parametrosService.listarParametrosVacacionesPorTipo(tipo));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Actualizar parametro de vacaciones", value = "url proxy /parametros/vacaciones/actualizar")
	@PutMapping("/parametros/vacaciones/actualizar")
	public ResponseEntity<ResponseStatus> actualizarParametrosVacaciones(@Valid @RequestBody RequestParametroActualizacion request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(parametrosService.actualizarParametroVacaciones(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consultar vacaciones meta por empleado", value = "url proxy /parametros/vacaciones/meta")
	@PostMapping("/parametros/vacaciones/meta")
	public ResponseEntity<ResponseStatus> listarEmpleadosMetaVacaciones(@Valid @RequestBody RequestFiltroEmpleadoMeta filtro) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(parametrosService.listarVacacionMeta(filtro));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Actualizar vacaciones meta por empleado", value = "url proxy /parametros/vacaciones/meta")
	@PutMapping("/parametros/vacaciones/meta")
	public ResponseEntity<ResponseStatus> actualizarMeta(@Valid @RequestBody RequestModificarMetaVacacion modificacion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		parametrosService.actualizarMeta(modificacion);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consultar tipos de reportes", value = "url proxy /parametros/vacaciones/reportes/tipos")
	@GetMapping("/parametros/vacaciones/reportes/tipos")
	public ResponseEntity<ResponseStatus> tiposResportes() {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(parametrosService.listarTiposReporte());
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Registrar acceso a reporte por puesto", value = "url proxy /parametros/vacaciones/reportes")
	@PostMapping("/parametros/vacaciones/reportes")
	public ResponseEntity<ResponseStatus> registrarAccesoReporte(@Valid @RequestBody RequestAccesoReporteRegistro registro) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		parametrosService.registrarAccesoReporte(registro);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Actualizar acceso a reporte por puesto", value = "url proxy /parametros/vacaciones/reportes")
	@PutMapping("/parametros/vacaciones/reportes")
	public ResponseEntity<ResponseStatus> actualizarAccesoReporte(@Valid @RequestBody RequestAccesoReporteActualizacion actualizacion) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		parametrosService.actualizarAccesoReporte(actualizacion);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Consulta de accesos a reportes", value = "url proxy /parametros/vacaciones/reportes/consulta")
	@PostMapping("/parametros/vacaciones/reportes/consulta")
	public ResponseEntity<ResponseStatus> actualizarAccesoReporte(@Valid @RequestBody RequestListarReporteAcceso request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(parametrosService.listarReporteAcceso(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MOVILIDAD})
	@ApiOperation(notes = "Eliminar de acceso a reportes", value = "url proxy /parametros/vacaciones/reportes")
	@DeleteMapping("/parametros/vacaciones/reportes")
	public ResponseEntity<ResponseStatus> eliminarAccesoReporte(@Valid @RequestBody RequestAccesoReporteEliminar request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		parametrosService.eliminarAccesoReporte(request);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		
	}
	
	

}
