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
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ParametrosDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;

@Component
public class ParametrosConstants {

	private static Logger logger = LoggerFactory.getLogger(ParametrosConstants.class);
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	private ParametrosDao parametrosDao;

	private static List<Parametro> listParams = new ArrayList<>();

	private void populateParametros() {
		logger.info("[BEGIN] populateParametros");
		listParams = parametrosDao.findAll();
		logger.info("[END] populateParametros");
	}

	private String populateParametro(String cod) {
		logger.info("[BEGIN] populateParametro " + cod);
		for (Parametro parametro : listParams) {
			if (parametro.getCodigo().equals(cod)) {
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
	
	public LocalDate getFechaInicioRegistroProgramacion(int anio) {
		if(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES != null) {
			return LocalDate.parse(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES + "/" + anio, formatter);
		}
		throw new AppException("No existe el parámetro de fecha inicio de registro de programación");
	}
	
	public LocalDate getFechaFinRegistroProgramacion(int anio) {
		if(FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES != null) {
			return LocalDate.parse(FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES + "/" + anio, formatter);
		}
		throw new AppException("No existe el parámetro de fecha fin de registro de programación");
	}
	
	public LocalDate getFechaCorteMeta(int anio) {
		if(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES != null) {
			return LocalDate.parse(FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES + "/" + anio, formatter).minusDays(1);
		}
		throw new AppException("No existe el parámetro de fecha inicio de registro de programación");
	}
	
	public Integer getHoraEnvioNotificacionVacaciones() {
		if(HORA_ENVIO_NOTIFICACIONES_VACACIONES != null ) {
			return Integer.parseInt(HORA_ENVIO_NOTIFICACIONES_VACACIONES);
		} 
		throw new AppException("No existe el parámetro de hora de envio de notificaciones");
	}
	

	// Parametros Vacaciones
	public String FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = null;
	public String FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = null;
	public String HORA_ENVIO_NOTIFICACIONES_VACACIONES = null;
	
	

	@PostConstruct
	protected void initialize() {
		logger.info("[BEGIN] initialize");
		populateParametros();

		loadParametros();
		logger.info("[END] initialize");
	}
	
	private void loadParametros() {
		FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = populateParametro("FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES");
		FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = populateParametro("FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES");
		HORA_ENVIO_NOTIFICACIONES_VACACIONES = populateParametro("HORA_ENVIO_NOTIFICACIONES_VACACIONES");
	}

}
