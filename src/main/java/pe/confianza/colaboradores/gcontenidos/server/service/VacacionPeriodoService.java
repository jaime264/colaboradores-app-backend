package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface VacacionPeriodoService {
	
	void actualizarPeriodo(Empleado empleado, String usuarioOperacion);

	void agregarNuevoPeriodo(Empleado empleado, int anio, int numero, String usuarioOperacion);
	
	PeriodoVacacion obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion);
}
