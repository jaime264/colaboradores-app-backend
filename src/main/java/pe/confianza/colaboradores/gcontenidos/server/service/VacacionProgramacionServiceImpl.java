package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.bean.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class VacacionProgramacionServiceImpl implements VacacionProgramacionService {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionProgramacionServiceImpl.class);
	
	
	private final int DIAS_VACACIONES_PERIODO = 30;
	
	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;
	
	@Autowired
	private EmpleadoApi empleadoApi;

	@Override
	public ResponseProgramacionVacacion registroProgramacion(RequestProgramacionVacacion programacion) {
		if(programacion.getFechaInicio().after(programacion.getFechaFin())) 
			throw new AppException("La fecha de inicio no puede ser mayor a la fecha fin");
		Empleado empleado = new Empleado();
		empleado.setUsuarioBT(programacion.getUsuarioBT());
		empleado = empleadoApi.getPerfil(empleado);
		String periodoVacacion = obtenerPeriodoIncompleto(new Date(empleado.getFechaIng()), programacion.getUsuarioBT());
		VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(programacion);
		vacacionProgramacion.setId(Instant.now().toEpochMilli());
		vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);
		vacacionProgramacion.setPeriodo(periodoVacacion);
		vacacionProgramacion = vacacionProgramacionDao.save(vacacionProgramacion);
		return VacacionProgramacionMapper.convert(vacacionProgramacion);
	
	}
	
	@Override
	public String obtenerPeriodoIncompleto(Date fechaIngreso, String usuarioBt) {
		logger.info("[INICIO] obtenerPeriodo: {} - {}", new Object[] {fechaIngreso, usuarioBt});
		String periodoVacacion = "";
		List<String> periodos = Utilitario.obtenerPeriodosVacaciones(fechaIngreso);
		for (String periodo : periodos) {
			List<VacacionProgramacion> programaciones = vacacionProgramacionDao.findVacacionProgramacionByUsuarioBTAndPeriodo(usuarioBt, periodo);
			int diasAcumuladoPeriodo = 0;
			for (VacacionProgramacion prog : programaciones) {
				if(prog.getEstado().id == EstadoVacacion.APROBADO.id || prog.getEstado().id == EstadoVacacion.GOZADO.id) {
					diasAcumuladoPeriodo += Utilitario.obtenerDieferenciaDias(prog.getFechaInicio(), prog.getFechaFin());
				}
			}
			logger.info("Usuario: {} - Periodo: {} - Dias: {}", new Object[] {usuarioBt, periodo, diasAcumuladoPeriodo});
			if(diasAcumuladoPeriodo < DIAS_VACACIONES_PERIODO) {
				periodoVacacion = periodo;
				break;
			} else {
				diasAcumuladoPeriodo = 0;
			}
		}
		logger.info("[FIN] obtenerPeriodo: {} " , new Object[] {periodoVacacion});
		return periodoVacacion;
	}

	@Override
	public List<ResponseProgramacionVacacion> obtenerProgramacion(String estado, String periodo, String usuarioBt) {
		logger.info("[INICIO] obtenerProgramacion: {} - {} - {}", new Object[] {estado, periodo, usuarioBt});
		List<VacacionProgramacion> programacion = new ArrayList<>();
		EstadoVacacion estadoSeleccionado = EstadoVacacion.getEstado(estado);
		if(estadoSeleccionado == null && !StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findVacacionProgramacionByUsuarioBTAndPeriodo(usuarioBt, periodo);
		}
		if(estadoSeleccionado != null && StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findVacacionProgramacionByUsuarioBTAndIdEstado(usuarioBt, estadoSeleccionado.id);
		}
		if(estadoSeleccionado != null && !StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findVacacionProgramacionByUsuarioBTAndPeriodoAndIdEstado(usuarioBt, periodo, estadoSeleccionado.id);
		}
		if(estadoSeleccionado == null && StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findVacacionProgramacionByUsuarioBT(usuarioBt);
		}
		logger.info("[FIN] obtenerProgramacion");
		return programacion.stream().map(p -> {
			return VacacionProgramacionMapper.convert(p);
		}).collect(Collectors.toList());
	}
	
	
	
	

}
