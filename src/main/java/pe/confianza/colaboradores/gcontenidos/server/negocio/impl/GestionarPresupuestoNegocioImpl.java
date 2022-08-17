package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestalAnual;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.NotAuthorizedException;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.GastoPresupuestoAnualService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.service.SeguridadService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class GestionarPresupuestoNegocioImpl implements GestionarPresupuestoNegocio {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosServiceImpl.class);
	
	@Autowired
	private GastoPresupuestoAnualService gastoPresupuestoAnualService;
	
	@Autowired
	private SeguridadService seguridadService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Autowired
	private MessageSource messageSource;


	@Override
	public List<ResponseGastoPresupuestalAnual> listarPresupuestosAnuales(RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return gastoPresupuestoAnualService.listarHabilitados().stream()
					.map(p -> {
						ResponseGastoPresupuestalAnual res = new ResponseGastoPresupuestalAnual();
						res.setActivo(p.isActivo());
						res.setCodigo(p.getCodigo());
						res.setDescripcion(p.getDescripcion());
						res.setPresupuestoUsado(0);
						return res;
					}).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarReporteAcceso", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] eliminarAccesoReporte", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarReporteAcceso", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	@Override
	public void registrarAuditoria(int status, String mensaje, Object data) {
		Gson gson = new Gson();
		try {
			String jsonData = gson.toJson(data);
			auditoriaService.createAuditoria("002", Constantes.GASTOS_GESTIONAR_PRESUPUESTO,
					status, mensaje, BsonDocument.parse(jsonData));
		} catch (Exception e) {
			logger.error("[ERROR] registrarAuditoria", e);
		}
		
	}

}
