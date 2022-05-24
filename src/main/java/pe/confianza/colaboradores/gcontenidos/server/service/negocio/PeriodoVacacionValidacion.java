package pe.confianza.colaboradores.gcontenidos.server.service.negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PeriodoVacacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

@Component
public class PeriodoVacacionValidacion {
	
	private static Logger logger = LoggerFactory.getLogger(PeriodoVacacionValidacion.class);
	
	@Autowired
	private PeriodoVacacionDao periodoVacacionDao;
	
	public PeriodoVacacion obtenerPeriodoProgramacion(Empleado empleado, VacacionProgramacion programacion) {
		logger.info("[BEGIN] obtenerPeriodoProgramacion");
		this.verificarExistePeriodoActual(empleado, programacion.getUsuarioCrea());
		LocalDate fechaActual = LocalDate.now();
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		periodos.stream().filter(p -> p.getAnio() >= fechaActual.getYear() - 1 ).findFirst();
		logger.info("[END] obtenerPeriodoProgramacion");
		return null;
	}
	
	/**
	 * Verifica si existe periodo actual, si no existe registra en BD
	 * @param empleado
	 * @param usuarioCrea
	 */
	public void verificarExistePeriodoActual(Empleado empleado, String usuarioCrea) {
		logger.info("[BEGIN] verificarExistePeriodoActual");
		LocalDate fechaActual = LocalDate.now();
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		Optional<PeriodoVacacion> optPeriodoActual = periodos.stream().filter(p -> p.getAnio() == (fechaActual.getYear() - 1)).findFirst();
		if(!optPeriodoActual.isPresent()) {
			//Crear periodo actual
			PeriodoVacacion periodo = new PeriodoVacacion();
			periodo.setAnio(fechaActual.getYear() - 1);
			periodo.setCodigoEmpleado(empleado.getCodigo());
			periodo.setCompletado(false);
			periodo.setDerecho(0.0);
			periodo.setDescripcion((fechaActual.getYear() - 1) + "-" + fechaActual.getYear());
			periodo.setDiasAprobadosGozar(0.0);
			periodo.setDiasGeneradosGozar(0.0);
			periodo.setDiasGozados(0.0);
			periodo.setDiasIndemnizables(0.0);
			periodo.setEmpleado(empleado);
			periodo.setFuente("APP");
			periodo.setFechaCrea(LocalDate.now());
			periodo.setUsuarioCrea(usuarioCrea);
			periodo.setMes(empleado.getFechaIngreso().getMonthValue());
			periodo.setNumero(periodos.size() + 1);
			periodo = periodoVacacionDao.save(periodo);
		}
		logger.info("[END] verificarExistePeriodoActual");
	}
	
	
	
	
	
	

}
