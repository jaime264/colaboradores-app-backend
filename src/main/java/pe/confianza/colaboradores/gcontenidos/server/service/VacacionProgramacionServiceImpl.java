package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.service.negocio.ProgramacionVacacionesValidacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

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
		logger.info("[BEGIN] registroProgramacion: {} - {} - {}", new Object[] {programacion.getUsuarioBT(), programacion.getFechaInicio(), programacion.getFechaFin()});
		programacionVacacionesValidacion.validarFechaRegistroProgramacion(programacion.getFechaInicio());
		Empleado empleado = empleadoService.actualizarInformacionEmpleado(programacion.getUsuarioBT().trim());
		if(empleado == null)
			throw new AppException("No existe el usuario " + programacion.getUsuarioBT());
		
		
		VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(programacion);
		vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);
		vacacionProgramacion.setFechaCrea(LocalDate.now());
		vacacionProgramacion.setUsuarioCrea(programacion.getUsuarioOperacion().trim());
		
		programacionVacacionesValidacion.actualizarPeriodo(empleado, programacion.getUsuarioOperacion().trim());
		programacionVacacionesValidacion.validarEmpleadoNuevo(vacacionProgramacion, empleado);
		programacionVacacionesValidacion.validarRangoFechas(vacacionProgramacion);
		vacacionProgramacion = programacionVacacionesValidacion.obtenerPeriodo(empleado, vacacionProgramacion);
		programacionVacacionesValidacion.validarTramoVacaciones(vacacionProgramacion);
		vacacionProgramacion = programacionVacacionesValidacion.obtenerOrdenProgramacion(vacacionProgramacion, programacion.getUsuarioOperacion());
		
		vacacionProgramacion = vacacionProgramacionDao.save(vacacionProgramacion);
		logger.info("[END] registroProgramacion");
		return VacacionProgramacionMapper.convert(vacacionProgramacion);
	
	}
	
	@Override
	public List<ResponseProgramacionVacacion> obtenerProgramacion(String estado, String periodo, String usuarioBt) {
		logger.info("[BEGIN] obtenerProgramacion: {} - {} - {}", new Object[] {estado, periodo, usuarioBt});
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
		logger.info("[END] obtenerProgramacion");
		return programacion.stream().map(p -> {
			return VacacionProgramacionMapper.convert(p);
		}).collect(Collectors.toList());
	}
	
	
	
	
	

}
