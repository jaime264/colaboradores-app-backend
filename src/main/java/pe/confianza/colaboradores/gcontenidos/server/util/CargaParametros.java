package pe.confianza.colaboradores.gcontenidos.server.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseEstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ParametrosDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;

@Component
public class CargaParametros {

	private static Logger logger = LoggerFactory.getLogger(CargaParametros.class);
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	private ParametrosDao parametrosDao;

	private static List<Parametro> listParams = new ArrayList<>();

	private void populateParametros() {
		logger.info("[BEGIN] populateParametros");
		listParams = parametrosDao.listarActivos();
		logger.info("[END] populateParametros");
	}

	private String populateParametro(String cod) {
		logger.info("[BEGIN] populateParametro " + cod);
		for (Parametro parametro : listParams) {
			if (parametro.getCodigo().equals(cod)) {
				logger.error(cod + " : " + parametro.getValor());
				return parametro.getValor();
			}
		}
		return StringUtils.EMPTY;
	}
	
	public Parametro addParametro(RequestParametro nuevoParametro) {
		logger.info("[BEGIN] addParametro");
		String valorBuscado = populateParametro(nuevoParametro.getCodigo());
		if(valorBuscado.isEmpty()) {
			Parametro parametro = ParametroMapper.convert(nuevoParametro);
			parametro.setUsuarioCrea(nuevoParametro.getUsuarioOperacion());
			parametro.setFechaCrea(LocalDateTime.now());
			parametro.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
			parametro = parametrosDao.save(parametro);
			listParams.add(parametro);
			loadParametros();
			return parametro;
		}
		throw new AppException("Ya existe un parametro con el código " + nuevoParametro.getCodigo());
	}
	
	public Parametro actualizarParametro(String codigo, String nuevoValor, String nuevaDescripcion, String usuarioModifica) {
		logger.info("[BEGIN] addParametro");
		Parametro buscado = search(codigo);
		if(buscado != null) {
			if(nuevaDescripcion != null)
				buscado.setDescripcion(nuevaDescripcion);
			buscado.setValor(nuevoValor);
			buscado.setUsuarioModifica(usuarioModifica);
			buscado.setFechaModifica(LocalDateTime.now());
			buscado = parametrosDao.save(buscado);
			populateParametros();
			loadParametros();
			return buscado;
		}
		logger.info("[END] addParametro");
		return null;
	}
	
	public Parametro search(String codigo) {
		for (Parametro parametro : listParams) {
			if(parametro.getCodigo().equals(codigo)) {
				return parametro;
			}
		}
		return null;
	}
	
	public Parametro search(long id) {
		for (Parametro parametro : listParams) {
			if(parametro.getId() == id) {
				return parametro;
			}
		}
		return null;
	}
	
	public List<Parametro> listarPorTipoYSubTipo(String codigoTipo, String codigoSubtipo) {
		List<Parametro> params = listParams.stream().filter(p -> p.getTipo().getCodigo().equals(codigoTipo) && codigoSubtipo.equals(p.getSubTipo()))
				.collect(Collectors.toList());
		params = params == null ? new ArrayList<>() : params;
		return params;
	}
	
	public List<Parametro> findAll() {
		return listParams;
	}
	
	public int getAnioPresente() {
		if(ANIO_PRESENTE != null)
			return Integer.parseInt(ANIO_PRESENTE);
		throw new AppException("No existe el parámetro año presente");
	}
	
	public int getMetaVacacionAnio() {
		return this.getAnioPresente() + 1;
	}
	
	public LocalDate getFechaInicioRegistroProgramacion() {
		if(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES != null) {
			return LocalDate.parse(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES + "/" + this.getAnioPresente(), formatter);
		}
		throw new AppException("No existe el parámetro de fecha inicio de registro de programación");
	}
	
	public LocalDate getFechaFinRegistroProgramacion() {
		if(FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES != null) {
			return LocalDate.parse(FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES + "/" + this.getAnioPresente(), formatter);
		}
		throw new AppException("No existe el parámetro de fecha fin de registro de programación");
	}
	
	public LocalDate getFechaMaximaAprobacionProgramaciones() {
		if(FECHA_MAXIMA_APROBACION != null) {
			return LocalDate.parse(FECHA_MAXIMA_APROBACION + "/" + this.getAnioPresente(), formatter);
		}
		throw new AppException("No existe el parámetro de fecha máxima de aprobación");
	}
	
	public LocalDate getFechaCorteMeta() {
		if(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES != null) {
			return LocalDate.parse(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES + "/" + this.getAnioPresente(), formatter).minusDays(1);
		}
		throw new AppException("No existe el parámetro de fecha inicio de registro de programación");
	}
	
	public Integer getHoraEnvioNotificacionVacaciones() {
		if(HORA_ENVIO_NOTIFICACIONES_VACACIONES != null ) {
			return Integer.parseInt(HORA_ENVIO_NOTIFICACIONES_VACACIONES);
		} 
		throw new AppException("No existe el parámetro de hora de envio de notificaciones");
	}
	
	public LocalDate getFechaInicioReprogramacion() {
		if(DIA_INICIO_REPROGRAMACION != null) {
			LocalDate fechaActual = LocalDate.now();
			int diaInicio = Integer.parseInt(DIA_INICIO_REPROGRAMACION);
			return LocalDate.parse((diaInicio < 10 ? "0" + diaInicio : diaInicio ) + "/" + (fechaActual.getMonthValue() < 10 ? "0" + fechaActual.getMonthValue() : fechaActual.getMonthValue()) + "/" + fechaActual.getYear(), formatter);
		}
		throw new AppException("No existe el parámetro de inicio de reprogramación");
	}
	
	public LocalDate getFechaFinReprogramacion() {
		if(DIA_FIN_REPROGRAMACION != null){
			LocalDate fechaActual = LocalDate.now();
			return LocalDate.parse(DIA_FIN_REPROGRAMACION + "/" + (fechaActual.getMonthValue() < 10 ? "0" + fechaActual.getMonthValue() : fechaActual.getMonthValue()) + "/" + fechaActual.getYear(), formatter);
		}
		throw new AppException("No existe el parámetro de inicio de reprogramacion");
	}
	
	public List<ResponseEstadoVacacion> getEstadosProgramacion() {
		List<ResponseEstadoVacacion> estados = new ArrayList<>();
		for (EstadoVacacion estado : EstadoVacacion.values()) {
			String descripcionEstado = populateParametro(estado.codigoParametro);
			String[] desaciptionArray = descripcionEstado.split("-");
			ResponseEstadoVacacion estadoRes = new ResponseEstadoVacacion();
			estadoRes.setCodigo(estado.id);
			estadoRes.setDescripcion(desaciptionArray[0].trim());
			estadoRes.setLeyenda(desaciptionArray[1].trim());
			estados.add(estadoRes);
		}
		return estados;
	}
	
	public String[] getEstadoProgramacionDescripcion(int idEstadoProgramacion) {
		EstadoVacacion estado = EstadoVacacion.getEstado(idEstadoProgramacion);
		String descripcionParametro = populateParametro(estado.codigoParametro);
		String[] desaciptionArray = descripcionParametro.split("-");
		return new String[] {desaciptionArray[0], desaciptionArray[1]};
	}

	
	public int getDiasMaximoVacacionesAdelantadas() {
		if(DIAS_MAXIMO_VACACIONES_ADELANTADAS != null)
			return Integer.parseInt(DIAS_MAXIMO_VACACIONES_ADELANTADAS);
		throw new AppException("No existe el parámetro de días máximo de vacaciones adelantadas");
	}
	
	public int getMesesAntiguedadVacacionesAdelantadas() {
		if(MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS != null)
			return Integer.parseInt(MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS);
		throw new AppException("No existe el parámetro de meses de antiguedad para vacaciones adelantadas");
	}
	
	public int getIntervaloDiasRecordatorioVacaciones() {
		if(DIAS_INTERVALO_NOTIFICACIONES != null)
			return Integer.parseInt(DIAS_INTERVALO_NOTIFICACIONES);
		throw new AppException("No existe el parámetro de intervalo de días de recordatorio de vacaciones");
	}
	
	public int getDiaNotificacionReprogramacion() {
		if(DIA_NOTIFICACION_REPROGRAMACION != null)
			return Integer.parseInt(DIA_NOTIFICACION_REPROGRAMACION);
		throw new AppException("No existe el parámetro de día de envio notificación de reprogramación");
	}
	
	public int getCantidadMaximaReprogramacionAnio() {
		if(CANTIDAD_MAX_REPROGRAMACIONES_ANIO != null)
			return Integer.parseInt(CANTIDAD_MAX_REPROGRAMACIONES_ANIO);
		throw new AppException("No existe el parámetro de reprogramaciones año");
	}
	
	public int getDiasMinimoTramosAntesPrimeraMitad() {
		if(DIAS_MINIMOS_TRAMOS_PRIMERA_MITAD != null)
			return Integer.parseInt(DIAS_MINIMOS_TRAMOS_PRIMERA_MITAD);
		throw new AppException("No existe el parámetro de días mínimos de tramos vacaciones");
	}
	
	public int getTotalVacacionesAnio() {
		if(TOTAL_VACACIONES_ANIO != null)
			return Integer.parseInt(TOTAL_VACACIONES_ANIO);
		throw new AppException("No existe el parámetro total del vacaciones por año");
	}
	
	public int getMitadTotalVacacionesAnio() {
		if(TOTAL_VACACIONES_ANIO != null)
			return Integer.parseInt(TOTAL_VACACIONES_ANIO) / 2;
		throw new AppException("No existe el parámetro total del vacaciones por año");
	}
	
	public double getVacacionesGanadasPorMes() {
		if(TOTAL_VACACIONES_ANIO != null)
			return Integer.parseInt(TOTAL_VACACIONES_ANIO) / 12;
		throw new AppException("No existe el parámetro total del vacaciones por año");
	}
	
	public int getMesesParaIndemnizacion() {
		if(LIMITE_TIEMPO_INDEMNIZABLE != null)
			return Integer.parseInt(LIMITE_TIEMPO_INDEMNIZABLE);
		throw new AppException("No existe el parámetro límite para indemnización");
	}
	
	public int getSabadosMinPorPeriodo() {
		if(SABADOS_MIN_POR_PERIODO != null)
			return Integer.parseInt(SABADOS_MIN_POR_PERIODO);
		throw new AppException("No existe el parámetro sábados por periodo");
	}
	
	public int getDomingosMinPorPeriodo() {
		if(DOMINGOS_MIN_POR_PERIODO != null)
			return Integer.parseInt(DOMINGOS_MIN_POR_PERIODO);
		throw new AppException("No existe el parámetro domingos por periodo");
	}
	
	public int getDiaLimiteAprobacionReprogramacion() {
		if(DIA_LIMITE_APROBACION_REPROGRAMACION != null)
			return Integer.parseInt(DIA_LIMITE_APROBACION_REPROGRAMACION);
		throw new AppException("No existe el parámetro día límite de aprobación de reprogramación");
	}
	
	public Double getMontoMaximoGastoMenor() {
		if(GASTO_MENOR_MONTO_MAXIMO != null)
			return Double.parseDouble(GASTO_MENOR_MONTO_MAXIMO);
		throw new AppException("No existe el parámetro Monto maximo de gasto menor");
	}
	
	// Parametros genericos
	public String ANIO_PRESENTE = null;
	

	// Parametros Vacaciones
	public String FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = null;
	public String FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = null;
	public String HORA_ENVIO_NOTIFICACIONES_VACACIONES = null;
	public String DIA_INICIO_REPROGRAMACION = null;
	public String DIA_FIN_REPROGRAMACION = null;
	public String DIAS_MAXIMO_VACACIONES_ADELANTADAS = null;
	public String MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS = null;
	public String DIAS_INTERVALO_NOTIFICACIONES = null;
	public String DIA_NOTIFICACION_REPROGRAMACION = null;
	public String DIAS_MINIMOS_TRAMOS_PRIMERA_MITAD = null;
	public String TOTAL_VACACIONES_ANIO = null;
	public String LIMITE_TIEMPO_INDEMNIZABLE = null;
	public String SABADOS_MIN_POR_PERIODO = null;
	public String DOMINGOS_MIN_POR_PERIODO = null;
	public String DIA_LIMITE_APROBACION_REPROGRAMACION = null;
	public String FECHA_MAXIMA_APROBACION = null;
	
	public String CANTIDAD_MAX_REPROGRAMACIONES_ANIO = null;
	
	public String MENSAJE_COLABORDOR_INICIO_PROGRAMACION = null;
	public String MENSAJE_COLABORDOR_META_INCOMPLETA =  null;
	public String MENSAJE_COLABORDOR_SIN_REGISTRO = null;
	public String MENSAJE_JEFE_SIN_REGISTRO_PROGRAMACIONES = null;
	public String MENSAJE_JEFE_PENDIENTE_APROBACION = null;
	public String MENSAJE_COLABORDOR_REPROGRAMACION = null;
	public String GASTO_MENOR_MONTO_MAXIMO = null;

	@PostConstruct
	protected void initialize() {
		logger.info("[BEGIN] initialize");
		populateParametros();

		loadParametros();
		logger.info("[END] initialize");
	}
	
	private void loadParametros() {
		
		//vacaciones - politicas regularorias
		DIAS_MAXIMO_VACACIONES_ADELANTADAS = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.DIAS_MAXIMO_VACACIONES_ADELANTADAS);
		MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS);
		DIAS_MINIMOS_TRAMOS_PRIMERA_MITAD = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.DIAS_MINIMOS_TRAMOS_PRIMERA_MITAD);
		TOTAL_VACACIONES_ANIO = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.TOTAL_VACACIONES_ANIO);
		LIMITE_TIEMPO_INDEMNIZABLE = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.LIMITE_TIEMPO_INDEMNIZABLE);
		SABADOS_MIN_POR_PERIODO = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.SABADOS_MIN_POR_PERIODO);
		DOMINGOS_MIN_POR_PERIODO = populateParametro(ParametrosConstantes.VacacionesPoliticaRegulatoria.DOMINGOS_MIN_POR_PERIODO);
		
		//vacaciones - programacion anual
		FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = populateParametro(ParametrosConstantes.VacacionesProgramacionAnual.FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES);
		FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = populateParametro(ParametrosConstantes.VacacionesProgramacionAnual.FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES);
		FECHA_MAXIMA_APROBACION = populateParametro(ParametrosConstantes.VacacionesProgramacionAnual.FECHA_MAXIMA_APROBACION);
		
		//vacaciones - reprogramacion
		DIA_INICIO_REPROGRAMACION = populateParametro(ParametrosConstantes.VacacionesReprogramacion.DIA_INICIO_REPROGRAMACION);
		DIA_FIN_REPROGRAMACION = populateParametro(ParametrosConstantes.VacacionesReprogramacion.DIA_FIN_REPROGRAMACION);
		DIA_LIMITE_APROBACION_REPROGRAMACION = populateParametro(ParametrosConstantes.VacacionesReprogramacion.DIA_LIMITE_APROBACION_REPROGRAMACION);
		CANTIDAD_MAX_REPROGRAMACIONES_ANIO = populateParametro(ParametrosConstantes.VacacionesReprogramacion.CANTIDAD_MAX_REPROGRAMACIONES_ANIO);
		
		//vacaciones - notificacioens
		HORA_ENVIO_NOTIFICACIONES_VACACIONES = populateParametro(ParametrosConstantes.VacacionesNotificacion.HORA_ENVIO_NOTIFICACIONES_VACACIONES);
		DIAS_INTERVALO_NOTIFICACIONES = populateParametro(ParametrosConstantes.VacacionesNotificacion.DIAS_INTERVALO_NOTIFICACIONES);
		DIA_NOTIFICACION_REPROGRAMACION = populateParametro(ParametrosConstantes.VacacionesNotificacion.DIA_NOTIFICACION_REPROGRAMACION);
		MENSAJE_COLABORDOR_INICIO_PROGRAMACION = populateParametro(ParametrosConstantes.VacacionesNotificacion.MENSAJE_COLABORDOR_INICIO_PROGRAMACION);
		MENSAJE_COLABORDOR_META_INCOMPLETA = populateParametro(ParametrosConstantes.VacacionesNotificacion.MENSAJE_COLABORDOR_META_INCOMPLETA);
		MENSAJE_COLABORDOR_SIN_REGISTRO = populateParametro(ParametrosConstantes.VacacionesNotificacion.MENSAJE_COLABORDOR_SIN_REGISTRO);
		MENSAJE_JEFE_SIN_REGISTRO_PROGRAMACIONES = populateParametro(ParametrosConstantes.VacacionesNotificacion.MENSAJE_JEFE_SIN_REGISTRO_PROGRAMACIONES);
		MENSAJE_JEFE_PENDIENTE_APROBACION = populateParametro(ParametrosConstantes.VacacionesNotificacion.MENSAJE_JEFE_PENDIENTE_APROBACION);
		MENSAJE_COLABORDOR_REPROGRAMACION = populateParametro(ParametrosConstantes.VacacionesNotificacion.MENSAJE_COLABORDOR_REPROGRAMACION);
		GASTO_MENOR_MONTO_MAXIMO = populateParametro(ParametrosConstantes.Gastos.GASTO_MENOR_MONTO_MAXIMO);
		
		
		
		
		//parametros generales
		ANIO_PRESENTE = populateParametro(ParametrosConstantes.Genericos.ANIO_PRESENTE);
		
	}

}
