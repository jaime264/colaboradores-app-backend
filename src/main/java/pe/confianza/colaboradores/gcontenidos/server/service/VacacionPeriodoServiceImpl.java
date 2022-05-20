package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.PeriodoVacacionDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.service.negocio.PeriodoVacacionValidacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class VacacionPeriodoServiceImpl implements VacacionPeriodoService {
	
	private static Logger logger = LoggerFactory.getLogger(PeriodoVacacionValidacion.class);
	
	@Autowired
	private PeriodoVacacionDao periodoVacacionDao;
	
	@Autowired
	private VacacionProgramacionDao programacionVacacionDao;

	@Override
	public void actualizarPeriodo(Empleado empleado, String usuarioOperacion) {
		logger.info("[BEGIN] actualizarPeriodo");
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaLimiteIdemnizable = fechaIngreso.plusYears(fechaActual.getYear() - fechaIngreso.getYear());
		logger.info("fechaIngreso: " + fechaIngreso + " fechaLimiteIdemnizable: " + fechaLimiteIdemnizable);
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		periodos = periodos != null ? periodos : new ArrayList<>();
		Optional<PeriodoVacacion> optPeriodoActual = periodos.stream().filter(p -> p.getAnio() == (fechaLimiteIdemnizable.getYear() - 1 )).findFirst();
		if(!optPeriodoActual.isPresent()) {
			agregarNuevoPeriodo(empleado, fechaActual.getYear() - 1, 0, usuarioOperacion);
		}
		periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		
		for (PeriodoVacacion periodo : periodos) {
			
			List<VacacionProgramacion> progRegistradas = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.REGISTRADO.id);
			long totalDiasRegistrados = progRegistradas != null ? progRegistradas.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum() : 0;
			List<VacacionProgramacion> progGenerados = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.GENERADO.id);
			long totalDiasGenerados = progGenerados != null ? progGenerados.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum() : 0;
			List<VacacionProgramacion> progAprobados = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.APROBADO.id);
			long totalDiasAprobados = progAprobados != null ? progAprobados.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum() : 0;
			List<VacacionProgramacion> progGozadas = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.GOZADO.id);
			long totalDiasGozados = progGozadas != null ? progGozadas.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum() : 0;
			periodo.setDiasRegistradosGozar((double) totalDiasRegistrados);
			periodo.setDiasGeneradosGozar((double) totalDiasGenerados);
			periodo.setDiasAprobadosGozar((double) totalDiasAprobados);
			periodo.setDiasGozados((double) totalDiasGozados);
			
			if(!periodo.isCompletado()) {
				periodo.setCodigoEmpleado(empleado.getCodigo());
				
				LocalDate fechaLimiteIdemnizablePeriodo = fechaIngreso.plusYears(periodo.getAnio() - fechaIngreso.getYear() + 1);
				
				logger.info("Periodo: " + periodo.getDescripcion() + " fechaIndemnizable: " + fechaLimiteIdemnizablePeriodo);
				
				if(fechaLimiteIdemnizablePeriodo.isBefore(fechaActual)) {
					periodo.setCompletado(true);
					periodo.setDerecho(30.0);
					periodo.setDiasIndemnizables(periodo.getDerecho() - periodo.getDiasGozados());
				} else {
					periodo.setDerecho(Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaActual));
					periodo.setDiasIndemnizables(0.0);
					periodo.setDiasPendientesGozar(periodo.getDerecho() - periodo.getDiasGozados());
				}
			} else {
				periodo.setDerecho(Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaActual));
				periodo.setDiasPendientesGozar(periodo.getDerecho() - periodo.getDiasGozados());
			}
			periodo.setUsuarioModifica(usuarioOperacion);
			periodo.setFechaModifica(LocalDate.now());
		}
		logger.info("[END] actualizarPeriodo");
	}

	@Override
	public void agregarNuevoPeriodo(Empleado empleado, int anio, int numero, String usuarioOperacion) {
		logger.info("[BEGIN] agregarNuevoPeriodo " + anio + "-" + (anio + 1));
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
		periodo = periodoVacacionDao.save(periodo);
		logger.info("[END] agregarNuevoPeriodo");
	}

	@Override
	public PeriodoVacacion obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion) {
		logger.info("[BEGIN] obtenerPeriodo ");
		PeriodoVacacion periodoSeleccionado = null;
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId())
				.stream().filter(p -> !p.isCompletado()).collect(Collectors.toList());
		if(periodos != null) {
			for (PeriodoVacacion periodo : periodos) {
				if(programacion.getNumeroDias() <= periodo.getDiasPendientesGozar()) {
					periodoSeleccionado = periodo;
					break;
				}
			}
		}
		logger.info("[END] obtenerPeriodo ");
		return periodoSeleccionado;
	}
	

	

}