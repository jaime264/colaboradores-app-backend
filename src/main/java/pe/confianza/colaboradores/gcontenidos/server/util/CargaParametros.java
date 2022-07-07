package pe.confianza.colaboradores.gcontenidos.server.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
				logger.info(cod + " : " + parametro.getValor());
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
			return LocalDate.parse(DIA_INICIO_REPROGRAMACION + "/" + (fechaActual.getMonthValue() < 10 ? "0" + fechaActual.getMonthValue() : fechaActual.getMonthValue()) + "/" + fechaActual.getYear(), formatter);
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
			String leyendaEstado = populateParametro(estado.codigoParametroLeyenda);
			ResponseEstadoVacacion estadoRes = new ResponseEstadoVacacion();
			estadoRes.setCodigo(estado.id);
			estadoRes.setDescripcion(descripcionEstado);
			estadoRes.setLeyenda(leyendaEstado);
			estados.add(estadoRes);
		}
		return estados;
	}
	
	public String getEstadoProgramacionDescripcion(int idEstadoProgramacion) {
		EstadoVacacion estado = EstadoVacacion.getEstado(idEstadoProgramacion);
		return populateParametro(estado.codigoParametro);
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
	

	@PostConstruct
	protected void initialize() {
		logger.info("[BEGIN] initialize");
		populateParametros();

		loadParametros();
		logger.info("[END] initialize");
	}
	
	private void loadParametros() {
		FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = populateParametro(ParametrosConstantes.Vacaciones.FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES);
		FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = populateParametro(ParametrosConstantes.Vacaciones.FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES);
		HORA_ENVIO_NOTIFICACIONES_VACACIONES = populateParametro(ParametrosConstantes.Vacaciones.HORA_ENVIO_NOTIFICACIONES_VACACIONES);
		DIA_INICIO_REPROGRAMACION = populateParametro(ParametrosConstantes.Vacaciones.DIA_INICIO_REPROGRAMACION);
		DIA_FIN_REPROGRAMACION = populateParametro(ParametrosConstantes.Vacaciones.DIA_FIN_REPROGRAMACION);
		DIAS_MAXIMO_VACACIONES_ADELANTADAS = populateParametro(ParametrosConstantes.Vacaciones.DIAS_MAXIMO_VACACIONES_ADELANTADAS);
		MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS = populateParametro(ParametrosConstantes.Vacaciones.MESES_MINIMO_ANTIGUEDAD_VACACIONES_ADELANTADAS);
		DIAS_INTERVALO_NOTIFICACIONES = populateParametro(ParametrosConstantes.Vacaciones.DIAS_INTERVALO_NOTIFICACIONES);
		
		ANIO_PRESENTE = populateParametro(ParametrosConstantes.Genericos.ANIO_PRESENTE);
		
	}

}
