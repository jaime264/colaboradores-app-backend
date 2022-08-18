package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

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

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.NotAuthorizedException;
import pe.confianza.colaboradores.gcontenidos.server.negocio.AgenciaNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AgenciaService;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.CorredorService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.service.SeguridadService;
import pe.confianza.colaboradores.gcontenidos.server.service.TerritorioService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class AgenciaNegocioImpl implements AgenciaNegocio {
	
	private static Logger logger = LoggerFactory.getLogger(AgenciaNegocioImpl.class);
	
	@Autowired
	private TerritorioService territorioService;
	
	@Autowired
	private CorredorService corredorService;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Autowired
	private SeguridadService seguridadService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public List<Map<String, Object>> listarTerritorios(RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return territorioService.listarTerritorios().stream()
					.map(t -> {
						Map<String, Object> territorio = new HashMap<>();
						territorio.put("codigo", t.getCodigo());
						territorio.put("descripcion", t.getDescripcion());
						return territorio;
					}).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarTerritorios", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] listarTerritorios", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarTerritorios", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public List<Map<String, Object>> listarCorredores(String codigoTerritorio, RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return corredorService.listar(codigoTerritorio).stream()
					.map(c -> {
						Map<String, Object> corredor = new HashMap<>();
						corredor.put("codigo", c.getCodigo());
						corredor.put("descripcion", c.getDescripcion());
						return corredor;
					}).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarCorredores", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] listarCorredores", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarCorredores", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public List<Map<String, Object>> listarAgencias(String codigoCorredor, RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return agenciaService.listarPorCorredor(codigoCorredor).stream()
					.map(a -> {
						Map<String, Object> agencia = new HashMap<>();
						agencia.put("codigo", a.getCodigo());
						agencia.put("descripcion", a.getDescripcion());
						return agencia;
					}).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarAgencias", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] listarAgencias", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarAgencias", e);
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
