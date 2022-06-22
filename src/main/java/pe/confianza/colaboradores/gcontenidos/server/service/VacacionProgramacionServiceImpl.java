package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

@Service
public class VacacionProgramacionServiceImpl implements VacacionProgramacionService {

	private static Logger logger = LoggerFactory.getLogger(VacacionProgramacionServiceImpl.class);

	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;

	@Override
	public void actualizarEstadoProgramaciones() {
		logger.info("[BEGIN] actualizarEstadoProgramaciones");
		vacacionProgramacionDao.actualizarEstadoProgramaciones();
		logger.info("[END] actualizarEstadoProgramaciones");
	}

	@Override
	public List<VacacionProgramacion> listarPorPeriodoYEstado(PeriodoVacacion periodo, EstadoVacacion estado) {
		logger.info("[BEGIN] listarPorPeriodoYEstado");
		List<VacacionProgramacion> programaciones = vacacionProgramacionDao.findByPeriodoAndEstado(periodo.getId(),
				estado.id);
		programaciones = programaciones == null ? new ArrayList<>() : programaciones;
		logger.info("[END] listarPorPeriodoYEstado");
		return programaciones;
	}
	
	@Override
	public List<VacacionProgramacion> registrar(List<VacacionProgramacion> programaciones, String usuarioOperacion) {
		programaciones.forEach(programacion -> {
			programacion.setUsuarioCrea(usuarioOperacion);
			programacion.setFechaCrea(LocalDateTime.now());
			programacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
			programacion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		});
		return vacacionProgramacionDao.saveAll(programaciones);
	}

	@Override
	public VacacionProgramacion registrar(VacacionProgramacion programacion, String usuarioOperacion) {
		programacion.setUsuarioCrea(usuarioOperacion);
		programacion.setFechaCrea(LocalDateTime.now());
		programacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		programacion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		return vacacionProgramacionDao.saveAndFlush(programacion);
	}

	@Override
	public VacacionProgramacion actualizar(VacacionProgramacion programacion, String usuarioOperacion) {
		programacion.setUsuarioModifica(usuarioOperacion);
		programacion.setFechaModifica(LocalDateTime.now());
		return vacacionProgramacionDao.saveAndFlush(programacion);
	}

	@Override
	public void eliminar(long idProgramacion, String usuarioOperacion) {
		Optional<VacacionProgramacion> optProgramacion = vacacionProgramacionDao.findById(idProgramacion);
		if (!optProgramacion.isPresent())
			throw new ModelNotFoundException("No existe la programación con id " + idProgramacion);
		VacacionProgramacion progamacion = optProgramacion.get();
		progamacion.setEstadoRegistro(EstadoRegistro.INACTIVO.valor);
		if(EstadoMigracion.IMPORTADO.valor.equals(progamacion.getEstadoMigracion()))
			progamacion.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
		actualizar(progamacion, usuarioOperacion);
	}

	@Override
	public VacacionProgramacion buscarPorId(long idProgramacion) {
		Optional<VacacionProgramacion> optProgramacion = vacacionProgramacionDao.findById(idProgramacion);
		if (!optProgramacion.isPresent())
			throw new ModelNotFoundException("No existe la programación con id " + idProgramacion);
		return optProgramacion.get();
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBTYPeriodo(String usuarioBT, String periodo) {
		logger.info("[BEGIN] buscarPorUsuarioBTYPeriodo");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByUsuarioBTAndPeriodo(usuarioBT,
				periodo);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBTYPeriodo");
		return lstProgramacion;
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBTYEstado(String usuarioBT, EstadoVacacion estado) {
		logger.info("[BEGIN] buscarPorUsuarioBTYEstado");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByUsuarioBTAndIdEstado(usuarioBT,
				estado.id);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBTYEstado");
		return lstProgramacion;
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBTYPeriodoYEstado(String usuarioBT, String periodo,
			EstadoVacacion estado) {
		logger.info("[BEGIN] buscarPorUsuarioBTYPeriodoYEstado");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao
				.findByUsuarioBTAndPeriodoAndEstado(usuarioBT, periodo, estado.id);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBTYPeriodoYEstado");
		return lstProgramacion;
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBT(String usuarioBT) {
		logger.info("[BEGIN] buscarPorUsuarioBT");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByUsuarioBT(usuarioBT);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBT");
		return lstProgramacion;
	}

	@Override
	@Transactional
	public void aprobarVacacionPeriodos(List<VacacionPeriodo> vacacionPeriodos) {
		// TODO Auto-generated method stub
		try {
			vacacionPeriodos.forEach(e -> {
				vacacionProgramacionDao.aprobarVacacionByPeriodo(e.getIdEstado(), e.getIdProgramacion());
			});
		} catch (Exception e2) {
			logger.info(e2.getMessage());
		}

	}

	@Override
	public VacacionProgramacion obtenerUltimaProgramacion(long idPeriodo) {
		logger.info("[BEGIN] obtenerUltimaProgramacion");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByIdPeriodo(idPeriodo);
		if(lstProgramacion == null)
			return null;
		if(lstProgramacion.isEmpty())
			return null;
		logger.info("[END] obtenerUltimaProgramacion");
		return lstProgramacion.get(0);
	}

	@Override
	public List<VacacionProgramacion> listarPorPeriodo(long idPeriodo) {
		logger.info("[BEGIN] listarPorPeriodo");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByIdPeriodo(idPeriodo);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] listarPorPeriodo");
		return lstProgramacion;
	}

	@Override
	public int obtenerSumaDiasPorPeriodoYEstado(long idPeriodo, EstadoVacacion estado) {
		logger.info("[BEGIN] obtenerSumaDiasPorPeriodoYEstado {} - {}", new Object[] {idPeriodo, EstadoVacacion.REGISTRADO});
		int diasPorPeriodoEstado = vacacionProgramacionDao.obtenerSumaDiasPorIdPeriodoYEstado(idPeriodo, estado.id);
		logger.info("[BEGIN] obtenerSumaDiasPorPeriodoYEstado");
		return diasPorPeriodoEstado;
	}

	@Override
	public long contarProgramacionPorUnidadNegocioEmpleado(Long idEmpleado, LocalDate fechaIncioProgramacion,
			LocalDate fechaFinProgramacion) {
		logger.info("[BEGIN] contarProgramacionPorUnidadNegocioEmpleado {}", idEmpleado);
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorUnidadNegocioEmpleado(idEmpleado, strFechaInicio, strFechaFin);
	}

	@Override
	public long contarProgramacionPorCorredorEmpleadoPuesto(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion) {
		logger.info("[BEGIN] contarProgramacionPorCorredorEmpleadoPuesto {} - {}", new Object[] {idEmpleado, descripcionPuesto});
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorCorredorEmpleadoPuesto(idEmpleado, descripcionPuesto, strFechaInicio, strFechaFin);
	}

	@Override
	public long contarProgramacionPorTerritorioEmpleadoPuesto(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion) {
		logger.info("[BEGIN] contarProgramacionPorTerritorioEmpleadoPuesto {} - {}", new Object[] {idEmpleado, descripcionPuesto});
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorTerritorioEmpleadoPuesto(idEmpleado, descripcionPuesto, strFechaInicio, strFechaFin);
	}

	@Override
	public long contarProgramacionPorEmpleadoPuesto(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion) {
		logger.info("[BEGIN] contarProgramacionPorEmpleadoPuesto {} - {}", new Object[] {idEmpleado, descripcionPuesto});
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorEmpleadoPuesto(idEmpleado, descripcionPuesto, strFechaInicio, strFechaFin);
	}

	@Override
	public long contarProgramacionPorEmpleadoAgencia(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion) {
		logger.info("[BEGIN] contarProgramacionPorEmpleadoAgencia {} - {}", new Object[] {idEmpleado, descripcionPuesto});
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorEmpleadoAgencia(idEmpleado, descripcionPuesto, strFechaInicio, strFechaFin);
	}

	@Override
	public List<EmplVacPerRes> listEmpleadoByprogramacion(RequestProgramacionEmpleado reqPrograEmp) {
		List<EmplVacPerRes> listEmp = new ArrayList<EmplVacPerRes>();
		
		Optional<Empleado> emJefe = empleadoDao.findOneByUsuarioBT(reqPrograEmp.getUsuarioBt());

		List<Empleado> emsByJefe = empleadoDao.findByCodigoJefe(emJefe.get().getCodigo());

		if (!CollectionUtils.isEmpty(emsByJefe)) {
			for (Empleado e : emsByJefe) {

				List<VacacionProgramacion> lVp = empleadoDao.findPeriodosByEmpleado(e.getId());
				for (VacacionProgramacion v : lVp) {

					EmplVacPerRes emp = new EmplVacPerRes();
					emp.setNombres(e.getNombres());
					emp.setApellidoPaterno(e.getApellidoPaterno());
					emp.setApellidoMaterno(e.getApellidoMaterno());
					emp.setIdEmpleado(e.getId());
					emp.setPuesto(e.getPuesto().getDescripcion());
					emp.setUsuarioBt(e.getUsuarioBT());

					VacacionPeriodo vp = new VacacionPeriodo();
					emp.setIdProgramacion(v.getId());
					emp.setFechaInicio(v.getFechaInicio());
					emp.setFechaFin(v.getFechaFin());
					emp.setIdEstado(v.getIdEstado());
					emp.setPeriodo(v.getPeriodo().getDescripcion());

					listEmp.add(emp);
				}
			}
		}
		return listEmp;
	}
	
	

}
