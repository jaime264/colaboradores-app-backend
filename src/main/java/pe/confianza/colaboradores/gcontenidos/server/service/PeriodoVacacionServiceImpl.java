package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class PeriodoVacacionServiceImpl implements PeriodoVacacionService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PeriodoVacacionServiceImpl.class);
	
	@Autowired
	private PeriodoVacacionDao periodoVacacionDao;
	
	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;
	
	@Autowired
	private CargaParametros cargaParametros;

	@Override
	public void actualizarPeriodos(Empleado empleado, String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarPeriodos {}", empleado.getUsuarioBT());
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		LocalDate[] periodoTrunco = Utilitario.rangoPeriodoTrunco(fechaIngreso);
		LOGGER.info("fechaIngreso: {} Periodo trunco: {} - {}", new Object[] {fechaIngreso, periodoTrunco[0], periodoTrunco[1]});
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		periodos = periodos != null ? periodos : new ArrayList<>();
		int anioPeriodoTrunco =  periodoTrunco[0].getYear();
		PeriodoVacacion periodoMaximo = periodos.stream().max(Comparator.comparing(PeriodoVacacion::getNumero)).orElse(null);
		int numero = periodoMaximo == null ? 0 : periodoMaximo.getNumero();
		Optional<PeriodoVacacion> optPeriodoActual = periodos.stream().filter(p -> p.getAnio() == anioPeriodoTrunco).findFirst();
		if(!optPeriodoActual.isPresent()) {
			agregarNuevoPeriodo(empleado, anioPeriodoTrunco, numero + 1, usuarioOperacion.trim());
		}
		periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		List<Long> idPeriodos = periodos.stream().map(p -> p.getId()).collect(Collectors.toList());
		for (Long id : idPeriodos) {
			consolidarResumenDias(id, usuarioOperacion);
			actualizarPeriodo(empleado, id, usuarioOperacion);
		}
		LOGGER.info("[END] actualizarPeriodos");
	}
	
	@Override
	public void consolidarResumenDias(long idPeriodo, String usuarioOperacion) {
		LOGGER.info("[BEGIN] consolidarResumenDias {}", idPeriodo);
		Optional<PeriodoVacacion> optPeriodo = periodoVacacionDao.findById(idPeriodo);
		if(optPeriodo.isPresent()) {
			double diasRegistrados = vacacionProgramacionDao.obtenerSumaDiasPorIdPeriodoYEstado(idPeriodo, EstadoVacacion.REGISTRADO.id);
			double diasGenerados = vacacionProgramacionDao.obtenerSumaDiasPorIdPeriodoYEstado(idPeriodo, EstadoVacacion.GENERADO.id);
			double diasAprobados = vacacionProgramacionDao.obtenerSumaDiasPorIdPeriodoYEstado(idPeriodo, EstadoVacacion.APROBADO.id);
			double diasGozados = vacacionProgramacionDao.obtenerSumaDiasPorIdPeriodoYEstado(idPeriodo, EstadoVacacion.GOZADO.id);
			PeriodoVacacion periodo = optPeriodo.get();
			periodo.setDiasRegistradosGozar(diasRegistrados);
			periodo.setDiasGeneradosGozar(diasGenerados);
			periodo.setDiasAprobadosGozar(diasAprobados);
			periodo.setDiasGozados(diasGozados);
			periodo.setUsuarioModifica(usuarioOperacion.trim());
			periodo.setFechaModifica(LocalDateTime.now());
			if(EstadoMigracion.IMPORTADO.valor.equals(periodo.getEstadoMigracion())) {
				periodo.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
			}
			periodoVacacionDao.saveAndFlush(periodo);
		}
		LOGGER.info("[END] consolidarResumenDias");
	}
	
	@Override
	public void actualizarPeriodo(Empleado empleado, long idPeriodo, String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarPeriodo {}", idPeriodo);
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		LocalDate fechaActual = LocalDate.now();
		Optional<PeriodoVacacion> optPeriodo = periodoVacacionDao.findById(idPeriodo);
		if(!optPeriodo.isPresent())
			return;
		
		PeriodoVacacion periodo = optPeriodo.get();
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
		if(EstadoMigracion.IMPORTADO.valor.equals(periodo.getEstadoMigracion()) || EstadoMigracion.EXPORTADO.valor.equals(periodo.getEstadoMigracion())) {
			periodo.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
		}
		periodo.setFechaInicioPeriodo(empleado.getFechaIngreso().plusYears(periodo.getAnio() - empleado.getFechaIngreso().getYear()));
		periodo.setFechaFinPeriodo(empleado.getFechaIngreso().plusYears(periodo.getAnio() - empleado.getFechaIngreso().getYear() + 1).plusDays(-1));
		periodo.setFechaLimiteIndemnizacion(empleado.getFechaIngreso().plusYears(periodo.getAnio() - empleado.getFechaIngreso().getYear() + 2).plusDays(-1));
		periodo.setUsuarioModifica(usuarioOperacion.trim());
		periodo.setFechaModifica(LocalDateTime.now());
		periodoVacacionDao.saveAndFlush(periodo);
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
		periodo.setDiasRegistradosGozar(0.0);
		periodo.setEmpleado(empleado);
		periodo.setFuente("APP");
		periodo.setFechaCrea(LocalDateTime.now());
		periodo.setUsuarioCrea(usuarioOperacion);
		periodo.setMes(empleado.getFechaIngreso().getMonthValue());
		periodo.setNumero(numero);
		periodo.setFechaInicioPeriodo(empleado.getFechaIngreso().plusYears(anio - empleado.getFechaIngreso().getYear()));
		periodo.setFechaFinPeriodo(empleado.getFechaIngreso().plusYears(anio - empleado.getFechaIngreso().getYear() + 1).plusDays(-1));
		periodo.setFechaLimiteIndemnizacion(empleado.getFechaIngreso().plusYears(anio - empleado.getFechaIngreso().getYear()).plusMonths(cargaParametros.getMesesParaIndemnizacion()) .plusDays(-1));
		periodo.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		periodo.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		periodo = periodoVacacionDao.saveAndFlush(periodo);
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
