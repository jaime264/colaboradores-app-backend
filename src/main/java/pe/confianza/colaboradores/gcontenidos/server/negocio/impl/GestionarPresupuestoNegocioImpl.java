package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPresupuestoTipoGastoResumen;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoTipoGastoResumen;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoConceptoGasto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoGeneralGasto;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.exception.NotAuthorizedException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.PresupuestoConceptoGastoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mapper.PresupuestoGeneralGastoMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoAgenciaGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoGeneralGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoPeriodoGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoTipoGasto;
import pe.confianza.colaboradores.gcontenidos.server.negocio.GestionarPresupuestoNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AgenciaService;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.PresupuestoGeneralGastoService;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;
import pe.confianza.colaboradores.gcontenidos.server.service.PresupuestoConceptoGastoService;
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
	private PresupuestoGeneralGastoService presupuestoGeneralGastoService;
	
	@Autowired
	private PresupuestoConceptoGastoService presupuestoConceptoGastoService;
	
	
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Autowired
	private SeguridadService seguridadService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@Autowired
	private MessageSource messageSource;


	@Override
	public List<ResponsePresupuestoGeneralGasto> listarPresupuestosGenerales(RequestAuditoriaBase peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			registrarAuditoria(Constantes.COD_OK, Constantes.OK, peticion);
			return presupuestoGeneralGastoService.listarHabilitados().stream()
					.map(p -> PresupuestoGeneralGastoMapper.convert(p)).collect(Collectors.toList());
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] listarPresupuestosGenerales", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] listarPresupuestosGenerales", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] listarPresupuestosGenerales", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	@Override
	public ResponsePresupuestoTipoGastoResumen detallePresupuestoTipoGastoPorGlgAsignado(
			RequestPresupuestoTipoGastoResumen peticion) {
		try {
			seguridadService.validarLogAuditoria(peticion.getLogAuditoria());
			String usuarioOperacion = peticion.getLogAuditoria().getUsuario().trim();
			PresupuestoGeneralGasto presupuestoGeneral = presupuestoGeneralGastoService.buscarPorCodigo(peticion.getCodigoPresupuesto());
			if(presupuestoGeneral == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "app.error.objeto_no_encontrado"));
			Optional<PresupuestoTipoGasto> optPresupuestoTipoGasto = presupuestoGeneral.getPresupuestosTipoGasto().stream()
					.filter(p -> p.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor) && p.getTipo().getCodigo().equals(peticion.getCodigoTipoGasto()))
					.findFirst();
			if(!optPresupuestoTipoGasto.isPresent())
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "app.error.objeto_no_encontrado"));
			PresupuestoTipoGasto presupuestoTipoGasto = optPresupuestoTipoGasto.get();
			List<ResponsePresupuestoConceptoGasto> prespuestosConcepto = presupuestoTipoGasto.getPresupuestosConcepto().stream()
					.filter(p -> p.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor))
					.map(p -> PresupuestoConceptoGastoMapper.convert(p, usuarioOperacion))
					.collect(Collectors.toList());
			ResponsePresupuestoTipoGastoResumen resumen = new ResponsePresupuestoTipoGastoResumen();
			resumen.setDescripcionPresupuestoGeneral(presupuestoGeneral.getDescripcion());
			resumen.setPresupuestosConcepto(prespuestosConcepto);
			resumen.setPresupuestoAsignado(prespuestosConcepto.stream().mapToDouble(ResponsePresupuestoConceptoGasto::getPresupuestoAsignado).sum());
			resumen.setPresupuestoConsumido(prespuestosConcepto.stream().mapToDouble(ResponsePresupuestoConceptoGasto::getPresupuestoConsumido).sum());
			return resumen;
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] detallePresupuestoTipoGastoPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_EMPTY, Constantes.DATA_EMPTY, peticion);
			throw new ModelNotFoundException(e.getMessage()); 
		}  catch (NotAuthorizedException e) {
			logger.error("[ERROR] detallePresupuestoTipoGastoPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_NO_AUTORIZADO, e.getMessage(), peticion);
			throw new NotAuthorizedException(e.getMessage());
		} catch (AppException e) {
			logger.error("[ERROR] detallePresupuestoTipoGastoPorGlgAsignado", e);
			registrarAuditoria(Constantes.COD_ERR, e.getMessage(), peticion);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] detallePresupuestoTipoGastoPorGlgAsignado", e);
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
			PresupuestoConceptoGasto presupuestoConcepto = presupuestoConceptoGastoService.buscarPorCodigo(peticion.getCodigoPresupuestoConcepto());
			if(presupuestoConcepto == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "app.error.objeto_no_encontrado"));
			if(!presupuestoConcepto.getGlgAsignado().getEmpleado().getUsuarioBT().equals(usuarioOperacion))
				throw new AppException("Ud. no puede administrar este concepto");
			List<PresupuestoAgenciaGasto> distribucionXAgencia = new ArrayList<>();
			DistribucionPresupuestoFrecuencia frecuencia = DistribucionPresupuestoFrecuencia.buscar(peticion.getCodigoFrecuenciaDistribucion());
			if(frecuencia == null)
				throw new ModelNotFoundException("No existe la frecuencia de distribucion " + peticion.getCodigoFrecuenciaDistribucion());
			DistribucionPresupuestoTipo tipo = DistribucionPresupuestoTipo.buscar(peticion.getTipoDistribucionMonto());
			if(tipo == null) 
				throw new ModelNotFoundException("No existe el tipo de distribución " + peticion.getTipoDistribucionMonto());
			double presupuestoPorPeriodo = presupuestoConcepto.getPresupuestoAsignado() / (12 / frecuencia.valor);
			if(peticion.isDistribucionExcel()) {
				distribucionXAgencia = distribucionExcel(peticion, excelDistribucion, presupuestoConcepto);
				double presupuestoPorDistribuir = distribucionXAgencia.stream().mapToDouble(PresupuestoAgenciaGasto::getPresupuestoAsignado)
						.sum();
				
				if(presupuestoPorPeriodo < presupuestoPorDistribuir)
					throw new AppException("No puede distribuir más de lo asignado al presupuesto de concepto");
			
			} else {
				distribucionXAgencia = distribucion(peticion, presupuestoConcepto);
			}
			presupuestoConcepto.setTipoDistribucionMonto(tipo);
			presupuestoConcepto.setFrecuenciaDistribucion(frecuencia);
			presupuestoConcepto.setDistribucionUniforme(peticion.isDistribucionUniforme());
			presupuestoConcepto.setDistribucionVariable(peticion.isDistribucionVariable());
			presupuestoConcepto.setPresupuestosAgencia(distribucionXAgencia);
			presupuestoConceptoGastoService.actualizarDistribuido(presupuestoConcepto, usuarioOperacion);
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
	
	private List<PresupuestoAgenciaGasto> distribucionExcel(RequestDistribucionConcepto peticion, MultipartFile excelDistribucion, PresupuestoConceptoGasto concepto) {
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
				PresupuestoAgenciaGasto presupuestoAgencia = new PresupuestoAgenciaGasto();
				presupuestoAgencia.setCodigo(zdt.toInstant().toEpochMilli());
				presupuestoAgencia.setAgencia(agencia);
				presupuestoAgencia.setPresupuestoAsignado(presupuesto);
				presupuestoAgencia.setPresupuestoConcepto(concepto);
				return presupuestoAgencia;
			}).collect(Collectors.toList());
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] distribucionExcel", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (Exception e) {
			logger.error("[ERROR] distribucionExcel", e);
			throw new AppException("Error al leer excel", e);
		}
	}
	
	private List<PresupuestoAgenciaGasto> distribucion(RequestDistribucionConcepto peticion, PresupuestoConceptoGasto concepto) {
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
				concepto.getPresupuestoAsignado()
				, porcentaje, montoDelTotal);
		double presupuestoPorAgencia = presupuestoTotalAgencias / agencias.size();
		final double presupuestoPorAgenciaRedondeado = presupuestoPorAgencia = Math.round(presupuestoPorAgencia * 100) / 100;
		return agencias.stream().map(a -> {
			ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of(Constantes.TIME_ZONE));
			PresupuestoAgenciaGasto presupuestoAgencia = new PresupuestoAgenciaGasto();
			presupuestoAgencia.setCodigo(zdt.toInstant().toEpochMilli());
			presupuestoAgencia.setAgencia(a);
			presupuestoAgencia.setPresupuestoConcepto(concepto);
			presupuestoAgencia.setPresupuestoAsignado(presupuestoPorAgenciaRedondeado);
			return presupuestoAgencia;
		}).collect(Collectors.toList());
	}
	
	private PresupuestoAgenciaGasto asignarPeriodos(PresupuestoAgenciaGasto conceptoAgencia, DistribucionPresupuestoFrecuencia frecuencia, String usuarioOperacion) {
		int numeroPeriodos = 12 / frecuencia.valor;
		DateTimeFormatter fomratter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA);
		LocalDate fechaInicio = LocalDate.parse("01/01/" + LocalDate.now().getYear(), fomratter);
		for (int i = 1; i <= numeroPeriodos; i++) {
			LocalDate fechaFin = fechaInicio.plusMonths(i).minusDays(1);
			PresupuestoPeriodoGasto periodo = new PresupuestoPeriodoGasto();
			periodo.setActual(false);
			periodo.setFechaInicio(fechaInicio);
			periodo.setFechaFin(fechaFin);
			fechaInicio = fechaFin.plusDays(1);
		}
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
