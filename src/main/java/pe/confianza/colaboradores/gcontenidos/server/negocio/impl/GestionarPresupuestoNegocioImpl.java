package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoPresupuestoAnualDetalle;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestalAnual;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestoAnualDetalle;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoAnualDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.exception.NotAuthorizedException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.GastoPresupuestoAnualMapper;
import pe.confianza.colaboradores.gcontenidos.server.mapper.GastoPresupuestoDistribucionConceptoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoAnual;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.GastoPresupuestoAnualService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.service.SeguridadService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoFrecuencia;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoTipo;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
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
					.map(p -> GastoPresupuestoAnualMapper.convert(p)).collect(Collectors.toList());
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
	public ResponseGastoPresupuestoAnualDetalle detalleDistribucionPresupuestoAnualPorGlgAsignado(
			RequestGastoPresupuestoAnualDetalle peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			String usuarioOperacion = peticion.getLogAuditoria().getUsuario().trim();
			GastoPresupuestoAnual presupuestoAnual = gastoPresupuestoAnualService.buscarPorCodigo(peticion.getCodigoPresupuesto());
			if(presupuestoAnual == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "app.error.objeto_no_encontrado"));
			ResponseGastoPresupuestalAnual resPresupuestoAnual = GastoPresupuestoAnualMapper.convert(presupuestoAnual);
			List<ResponsePresupuestoAnualDistribucionConcepto> conceptos = presupuestoAnual.getDistribucionesConcepto().stream()
			.filter(c -> c.getGlgAsignado().getEmpleado().getUsuarioBT().equals(usuarioOperacion) && c.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor))
			.map(c -> GastoPresupuestoDistribucionConceptoMapper.convert(c))
			.collect(Collectors.toList());
			ResponseGastoPresupuestoAnualDetalle detalle = new ResponseGastoPresupuestoAnualDetalle();
			detalle.setPresupuestoAnual(resPresupuestoAnual);
			detalle.setConceptos(conceptos);
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return detalle;
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] detalleDistribucionPresupuestoAnualPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_EMPTY, Constantes.DATA_EMPTY, peticion);
			throw new ModelNotFoundException(e.getMessage()); 
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] detalleDistribucionPresupuestoAnualPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] detalleDistribucionPresupuestoAnualPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] detalleDistribucionPresupuestoAnualPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	@Override
	public List<Map<String, Object>> listarTiposDistribucionMonto(RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return Arrays.asList(DistribucionPresupuestoTipo.values()).stream()
					.map(t -> {
						Map<String, Object> tipo = new HashMap<>();
						tipo.put("codigo", t.codigo);
						tipo.put("descripcion", t.descripcion);
						return tipo;
					}).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarTiposDistribucionMonto", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] listarTiposDistribucionMonto", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarTiposDistribucionMonto", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public List<Map<String, Object>> listarFrecuenciaDistribucion(RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return Arrays.asList(DistribucionPresupuestoFrecuencia.values()).stream()
					.map(t -> {
						Map<String, Object> tipo = new HashMap<>();
						tipo.put("codigo", t.codigo);
						tipo.put("descripcion", t.descripcion);
						return tipo;
					}).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarFrecuenciaDistribucion", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] listarFrecuenciaDistribucion", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarFrecuenciaDistribucion", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	@Override
	public ResponsePresupuestoAnualDistribucionConcepto configurarDistribucionConcepto(
			RequestDistribucionConcepto peticion, MultipartFile excelDistribucion) {
		// TODO Auto-generated method stub
		return null;
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
