package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface PeriodoVacacionService {
	
	void actualizarPeriodos(Empleado empleado, String usuarioOPeracion);
	
	void actualizarPeriodo(Empleado empleado, PeriodoVacacion periodo, String usuarioOperacion);
	
	void agregarNuevoPeriodo(Empleado empleado, int anio, int numero, String usuarioOperacion);
	
	List<PeriodoVacacion> obtenerPeriodosNoCompletados(Empleado empleado, VacacionProgramacion programacion);
	
	List<PeriodoVacacion> consultar(Empleado empleado);
}
