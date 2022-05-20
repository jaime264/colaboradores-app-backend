package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.VacacionProgramacion;

public interface VacacionPeriodoService {
	
	void actualizarPeriodo(Empleado empleado, String usuarioOperacion);

	void agregarNuevoPeriodo(Empleado empleado, int anio, int numero, String usuarioOperacion);
	
	PeriodoVacacion obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion);
}
