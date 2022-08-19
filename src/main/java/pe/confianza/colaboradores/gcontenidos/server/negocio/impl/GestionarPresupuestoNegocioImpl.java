package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoAnual;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConceptoAgencia;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AgenciaService;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.GastoPresupuestoAnualService;
import pe.confianza.colaboradores.gcontenidos.server.service.GastoPresupuestoDistribucionConceptoAgenciaService;
import pe.confianza.colaboradores.gcontenidos.server.service.GastoPresupuestoDistribucionConceptoService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.service.SeguridadService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoFrecuencia;
import pe.confianza.colaboradores.gcontenidos.server.util.DistribucionPresupuestoTipo;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;
import pe.confianza.colaboradores.gcontenidos.server.util.file.read.xlsx.XLSXFeatureCollection;

@Service
public class GestionarPresupuestoNegocioImpl implements GestionarPresupuestoNegocio {
	
	private static Logger logger = LoggerFactory.getLogger(ParametrosServiceImpl.class);
	
	@Autowired
	private GastoPresupuestoAnualService gastoPresupuestoAnualService;
	
	@Autowired
	private GastoPresupuestoDistribucionConceptoService gastoPresupuestoDistribucionConceptoService;
	
	@Autowired
	private GastoPresupuestoDistribucionConceptoAgenciaService gastoPresupuestoDistribucionConceptoAgenciaService;
	
	@Autowired
	private AgenciaService agenciaService;
	
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
	public void configurarDistribucionConcepto(
			RequestDistribucionConcepto peticion, MultipartFile excelDistribucion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			String usuarioOperacion = peticion.getLogAuditoria().getUsuario();
			GastoPresupuestoDistribucionConcepto concepto = gastoPresupuestoDistribucionConceptoService.buscarPorCodigo(peticion.getCodigoConcepto());
			if(concepto == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "app.error.objeto_no_encontrado"));
			List<GastoPresupuestoDistribucionConceptoAgencia> distribucionXAgencia = new ArrayList<>();
			DistribucionPresupuestoFrecuencia frecuencia = DistribucionPresupuestoFrecuencia.buscar(peticion.getCodigoFrecuenciaDistribucion());
			if(frecuencia == null)
				throw new ModelNotFoundException("No existe la frecuencia de distribucion " + peticion.getCodigoFrecuenciaDistribucion());
			DistribucionPresupuestoTipo tipo = DistribucionPresupuestoTipo.buscar(peticion.getTipoDistribucionMonto());
			if(tipo == null) 
				throw new ModelNotFoundException("No existe el tipo de distribución " + peticion.getTipoDistribucionMonto());
			double presupuestoPorPeriodo = concepto.getPresupuesto() / (12 / frecuencia.valor);
			if(peticion.isDistribucionExcel()) {
				distribucionXAgencia = distribucionExcel(peticion, excelDistribucion, concepto);
				double presupuestoPorDistribuir = distribucionXAgencia.stream().mapToDouble(GastoPresupuestoDistribucionConceptoAgencia::getPresupuesto)
						.sum();
				
				if(presupuestoPorPeriodo < presupuestoPorDistribuir)
					throw new AppException("No puede distribuir más de lo asignado al presupuesto de concepto");
			
			} else {
				distribucionXAgencia = distribucion(peticion, concepto);
			}
			concepto.setTipoDistribucionMonto(tipo);
			concepto.setFrecuenciaDistribucion(frecuencia);
			concepto.setDistribucionUniforme(peticion.isDistribucionUniforme());
			concepto.setDistribucionVariable(peticion.isDistribucionVariable());
			concepto.setDistribucionesAgencia(distribucionXAgencia);
			gastoPresupuestoDistribucionConceptoService.actualizarDistribuido(concepto, usuarioOperacion);
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] configurarDistribucionConcepto", e);
			registrarAuditoria(Constantes.COD_EMPTY, Constantes.DATA_EMPTY, peticion);
			throw new ModelNotFoundException(e.getMessage()); 
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] configurarDistribucionConcepto", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] configurarDistribucionConcepto", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] configurarDistribucionConcepto", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	private List<GastoPresupuestoDistribucionConceptoAgencia> distribucionExcel(RequestDistribucionConcepto peticion, MultipartFile excelDistribucion, GastoPresupuestoDistribucionConcepto concepto) {
		try {
			XLSXFeatureCollection xlsxFeatureCollection = new XLSXFeatureCollection(excelDistribucion.getInputStream());
			xlsxFeatureCollection.read();
			return xlsxFeatureCollection.getCollection().getRows().stream()
			.map(row -> {
				String codigoAgencia = (String) row.getRow().get("CODIGO_AGENCIA");
				Agencia agencia = agenciaService.buscarPorCodigo(codigoAgencia);
				if(agencia == null)
					throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "agenciaService", codigoAgencia));
				Double presupuesto =  row.getRow().get("PRESUPUESTO") == null ? 0 : (Double) row.getRow().get("PRESUPUESTO");
				ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of(Constantes.TIME_ZONE));
				GastoPresupuestoDistribucionConceptoAgencia conceptoAgencia = new GastoPresupuestoDistribucionConceptoAgencia();
				conceptoAgencia.setCodigo(zdt.toInstant().toEpochMilli());
				conceptoAgencia.setAgencia(agencia);
				conceptoAgencia.setPresupuesto(presupuesto);
				conceptoAgencia.setDistribucionConcepto(concepto);
				return conceptoAgencia;
			}).collect(Collectors.toList());
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] distribucionExcel", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (Exception e) {
			logger.error("[ERROR] distribucionExcel", e);
			throw new AppException("Error al leer excel", e);
		}
	}
	
	private List<GastoPresupuestoDistribucionConceptoAgencia> distribucion(RequestDistribucionConcepto peticion, GastoPresupuestoDistribucionConcepto concepto) {
		List<Agencia> agencias = new ArrayList<Agencia>();
		if(peticion.isTodasAgencias()) {
			agencias = agenciaService.listarHabilitados();
		} else {
			if(!peticion.getTerritoriosSeleccionados().isEmpty()) {
				for (String codigo : peticion.getTerritoriosSeleccionados()) {
					agencias.addAll(agenciaService.listarPorTerritorio(codigo));
				}
			}
			if(!peticion.getCorredoresSeleccionandos().isEmpty()) {
				for (String codigo : peticion.getTerritoriosSeleccionados()) {
					agencias.addAll(agenciaService.listarPorCorredor(codigo));
				}
			}
			if(!peticion.getAgenciasSeleccionadas().isEmpty()) {
				for (String codigo : peticion.getAgenciasSeleccionadas()) {
					Agencia agencia = agenciaService.buscarPorCodigo(codigo);
					if(agencia == null)
						throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "agenciaService", codigo));
					agencias.add(agencia);
				}
			}
		}
		DistribucionPresupuestoTipo tipo = DistribucionPresupuestoTipo.buscar(peticion.getTipoDistribucionMonto());
		if(tipo == null)
			throw new ModelNotFoundException("No existe el tipo de distribución " + peticion.getTipoDistribucionMonto());
		Double porcentaje = tipo.codigo == DistribucionPresupuestoTipo.UN_PORCENTAJE_TOTAL.codigo ? peticion.getValorMontoDistribuir() : null ;
		Double montoDelTotal = peticion.getValorMontoDistribuir();
		double presupuestoTotalAgencias = DistribucionPresupuestoTipo.calcularMontoDistribuir(peticion.getTipoDistribucionMonto(),
				concepto.getPresupuesto()
				, porcentaje, montoDelTotal);
		double presupuestoPorAgencia = presupuestoTotalAgencias / agencias.size();
		final double presupuestoPorAgenciaRedondeado = presupuestoPorAgencia = Math.round(presupuestoPorAgencia * 100) / 100;
		return agencias.stream().map(a -> {
			ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of(Constantes.TIME_ZONE));
			GastoPresupuestoDistribucionConceptoAgencia conceptoAgencia = new GastoPresupuestoDistribucionConceptoAgencia();
			conceptoAgencia.setCodigo(zdt.toInstant().toEpochMilli());
			conceptoAgencia.setAgencia(a);
			conceptoAgencia.setDistribucionConcepto(concepto);
			conceptoAgencia.setPresupuesto(presupuestoPorAgenciaRedondeado);
			return conceptoAgencia;
		}).collect(Collectors.toList());
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
