package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.PeriodoVacacionDao;
import pe.confianza.colaboradores.gcontenidos.server.dao.mariadb.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

@Service
public class VacacionPeriodoServiceImpl implements VacacionPeriodoService {
	
	@Autowired
	private PeriodoVacacionDao periodoVacacionDao;
	
	private VacacionProgramacionDao programacionVacacionDao;

	@Override
	public void actualizarPeriodo(Empleado empleado) {
		LocalDate fechaIngreso = empleado.getFechaIngreso();
		LocalDate fechaActual = LocalDate.now();
		List<PeriodoVacacion> periodos = periodoVacacionDao.findByIdEmpleado(empleado.getId());
		for (PeriodoVacacion periodo : periodos) {
			
			List<VacacionProgramacion> progRegistradas = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.REGISTRADO.id);
			long totalDiasRegistrados = progRegistradas.stream().mapToLong(VacacionProgramacion::getNumeroDias).sum();
			List<VacacionProgramacion> progGenerados = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.GENERADO.id);
			long totalDiasGenerados = progGenerados.stream().mapToLong(VacacionProgramacion::getNumeroDias).sum();
			List<VacacionProgramacion> progAprobados = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.APROBADO.id);
			long totalDiasAprobados = progAprobados.stream().mapToLong(VacacionProgramacion::getNumeroDias).sum();
			List<VacacionProgramacion> progGozadas = programacionVacacionDao.findByPeriodoAndEstado(periodo.getId(), EstadoVacacion.GOZADO.id);
			long totalDiasGozados = progGozadas.stream().mapToLong(VacacionProgramacion::getNumeroDias).sum();
			
			LocalDate fechaLimiteIdemnizable = fechaIngreso.plusYears(periodo.getAnio() - fechaIngreso.getYear());
			
			if(!periodo.isCompletado()) {
				
				periodo.setCodigoEmpleado(empleado.getCodigo());
				periodo.setDiasRegistradosGozar((double) totalDiasRegistrados);
				periodo.setDiasGeneradosGozar((double) totalDiasGenerados);
				periodo.setDiasAprobadosGozar((double) totalDiasAprobados);
				periodo.setDiasGozados((double) totalDiasGozados);
				
				
				if(fechaLimiteIdemnizable.isBefore(fechaActual)) {
					periodo.setCompletado(true);
					periodo.setDiasIndemnizables(periodo.getDerecho() - periodo.getDiasGozados());
				}
				
				
			} else {
				
				
			}
			
			periodo.setFechaModifica(LocalDate.now());
		}
		
	}
	

	

}