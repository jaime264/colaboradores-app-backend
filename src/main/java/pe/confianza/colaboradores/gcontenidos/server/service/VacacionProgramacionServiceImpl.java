package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pe.confianza.colaboradores.gcontenidos.server.api.spring.EmpleadoApi;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.service.negocio.ProgramacionVacacionesValidacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class VacacionProgramacionServiceImpl implements VacacionProgramacionService {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionProgramacionServiceImpl.class);
	
	
	private final int DIAS_VACACIONES_PERIODO = 30;
	
	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private ProgramacionVacacionesValidacion programacionVacacionesValidacion;

	@Override
	public ResponseProgramacionVacacion registroProgramacion(RequestProgramacionVacacion programacion) {
		Empleado empleado = empleadoService.actualizarInformacionEmpleado(programacion.getUsuarioBT());
		if(empleado == null)
			throw new AppException("No existe el usuario " + programacion.getUsuarioBT());
		
		VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(programacion);
		vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);
		vacacionProgramacion.setPeriodo("");
		vacacionProgramacion.setEmpleado(empleado);
		vacacionProgramacion.setFechaCrea(LocalDate.now());
		vacacionProgramacion.setUsuarioCrea(programacion.getUsuarioOperacion());
		
		programacionVacacionesValidacion.validarFechas(vacacionProgramacion);
		programacionVacacionesValidacion.validarTramoVacaciones(vacacionProgramacion);
		vacacionProgramacion = programacionVacacionesValidacion.obtenerOrdenProgramacion(vacacionProgramacion, programacion.getUsuarioOperacion());
		
		vacacionProgramacion = vacacionProgramacionDao.save(vacacionProgramacion);
		return VacacionProgramacionMapper.convert(vacacionProgramacion);
	
	}
	
	@Override
	public List<ResponseProgramacionVacacion> obtenerProgramacion(String estado, String periodo, String usuarioBt) {
		logger.info("[INICIO] obtenerProgramacion: {} - {} - {}", new Object[] {estado, periodo, usuarioBt});
		List<VacacionProgramacion> programacion = new ArrayList<>();
		EstadoVacacion estadoSeleccionado = EstadoVacacion.getEstado(estado);
		if(estadoSeleccionado == null && !StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findByUsuarioBTAndPeriodo(usuarioBt, periodo);
		}
		if(estadoSeleccionado != null && StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findByUsuarioBTAndIdEstado(usuarioBt, estadoSeleccionado.id);
		}
		if(estadoSeleccionado != null && !StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findByUsuarioBTAndPeriodoAndEstado(usuarioBt, periodo, estadoSeleccionado.id);
		}
		if(estadoSeleccionado == null && StringUtils.isEmpty(periodo)) {
			programacion = vacacionProgramacionDao.findByUsuarioBT(usuarioBt);
		}
		logger.info("[FIN] obtenerProgramacion");
		return programacion.stream().map(p -> {
			return VacacionProgramacionMapper.convert(p);
		}).collect(Collectors.toList());
	}
	
	
	
	
	

}
