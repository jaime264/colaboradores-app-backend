package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PeriodoVacacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class PeriodoVacacionServiceImpl implements PeriodoVacacionService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PeriodoVacacionServiceImpl.class);
	
	@Autowired
	private PeriodoVacacionDao periodoVacacionDao;

	@Override
	public void actualizarPeriodos(Empleado empleado, String usuarioOPeracion) {
		LOGGER.info("[BEGIN] actualizarPeriodos " + empleado.getUsuarioBT());
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaLimitePeriodo = fechaIngreso.plusYears(fechaActual.getYear() - fechaIngreso.getYear());
		LOGGER.info("fechaIngreso: " + fechaIngreso + " fechaLimitePeriodo: " + fechaLimitePeriodo);
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		periodos = periodos != null ? periodos : new ArrayList<>();
		int anio = fechaActual.isBefore(fechaLimitePeriodo) ? (fechaLimitePeriodo.getYear() - 1) : fechaLimitePeriodo.getYear();
		PeriodoVacacion periodoMaximo = periodos.stream().max(Comparator.comparing(PeriodoVacacion::getNumero)).orElse(null);
		int numero = periodoMaximo == null ? 0 : periodoMaximo.getNumero();
		Optional<PeriodoVacacion> optPeriodoActual = periodos.stream().filter(p -> p.getAnio() == anio).findFirst();
		if(!optPeriodoActual.isPresent()) {
			agregarNuevoPeriodo(empleado, anio, numero + 1, usuarioOPeracion.trim());
		}
		periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		for (PeriodoVacacion periodo : periodos) {
			actualizarPeriodo(empleado, periodo, usuarioOPeracion);
		}
		LOGGER.info("[END] actualizarPeriodo");
	}
	
	@Override
	public void actualizarPeriodo(Empleado empleado, PeriodoVacacion periodo, String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarPeriodo");
		periodoVacacionDao.actualizarDias(periodo.getId());
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		LocalDate fechaActual = LocalDate.now();
		Optional<PeriodoVacacion> optPeriodo = periodoVacacionDao.findById(periodo.getId());
		if(!optPeriodo.isPresent())
			return;
		periodo = optPeriodo.get();
		periodo.setCodigoEmpleado(empleado.getCodigo());
		
		LOGGER.info("Empleado: {} - Fecha ingreso: {} - Inicio: {} Fin: {} Idemnizable: {}", 
				new Object[] { empleado.getUsuarioBT(), empleado.getFechaIngreso(), periodo.getFechaInicioPeriodo(), periodo.getFechaFinPeriodo(), periodo.getFechaLimiteIndemnizacion()} );
		if(!periodo.isCompletado()) {
			if(periodo.getFechaLimiteIndemnizacion().isBefore(fechaActual)) {
				periodo.setCompletado(true);
				periodo.setDerecho(30.0);
				periodo.setDiasIndemnizables(periodo.getDerecho() - periodo.getDiasGozados());
			} else {
				if(periodo.getFechaFinPeriodo().isBefore(fechaActual)) {
					periodo.setDerecho(30.0);
				} else {
					periodo.setDerecho(Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaActual));
				}
				periodo.setCompletado(false);
				periodo.setDiasIndemnizables(0.0);
				periodo.setDiasPendientesGozar(periodo.getDerecho() - periodo.getDiasGozados());
			}
		}
		periodo.setUsuarioModifica(usuarioOperacion.trim());
		periodo.setFechaModifica(LocalDate.now());
		periodoVacacionDao.save(periodo);
		LOGGER.info("[BEGIN] actualizarPeriodo");
	}

	@Override
	public void agregarNuevoPeriodo(Empleado empleado, int anio, int numero, String usuarioOperacion) {
		LOGGER.info("[BEGIN] agregarNuevoPeriodo " + anio + "-" + (anio + 1));
		PeriodoVacacion periodo = new PeriodoVacacion();
		periodo.setAnio(anio);
		periodo.setCodigoEmpleado(empleado.getCodigo());
		periodo.setCompletado(false);
		periodo.setDerecho(0.0);
		periodo.setDescripcion(anio + "-" + (anio + 1));
		periodo.setDiasAprobadosGozar(0.0);
		periodo.setDiasGeneradosGozar(0.0);
		periodo.setDiasGozados(0.0);
		periodo.setDiasIndemnizables(0.0);
		periodo.setDiasPendientesGozar(0.0);
		periodo.setEmpleado(empleado);
		periodo.setFuente("APP");
		periodo.setFechaCrea(LocalDate.now());
		periodo.setUsuarioCrea(usuarioOperacion);
		periodo.setMes(empleado.getFechaIngreso().getMonthValue());
		periodo.setNumero(numero);
		periodo.setFechaInicioPeriodo(empleado.getFechaIngreso().plusYears(anio - empleado.getFechaIngreso().getYear()));
		periodo.setFechaFinPeriodo(empleado.getFechaIngreso().plusYears(anio - empleado.getFechaIngreso().getYear() + 1).plusDays(-1));
		periodo.setFechaLimiteIndemnizacion(empleado.getFechaIngreso().plusYears(anio - empleado.getFechaIngreso().getYear() + 2).plusDays(-1));
		periodo = periodoVacacionDao.save(periodo);
		LOGGER.info("[END] agregarNuevoPeriodo");
	}
	
	@Override
	public List<PeriodoVacacion> obtenerPeriodosNoCompletados(Empleado empleado, VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] obtenerPeriodosNoCompletados ");
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		periodos = periodos == null ? new ArrayList<>() : periodos;
		periodos = periodos.stream().filter(p -> !p.isCompletado()).collect(Collectors.toList());
		LOGGER.info("[END] obtenerPeriodosNoCompletados ");
		return periodos;
	}

	@Override
	public List<PeriodoVacacion> consultar(Empleado empleado) {
		LOGGER.info("[BEGIN] consultar");
		List<PeriodoVacacion> lstPeriodo = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		lstPeriodo = lstPeriodo == null ? new ArrayList<>() : lstPeriodo;
		LOGGER.info("[BEGIN] consultar");
		return lstPeriodo;
	}


}
