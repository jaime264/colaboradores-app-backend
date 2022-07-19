package pe.confianza.colaboradores.gcontenidos.server.service;


import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface VacacionMetaService {
	
	/**
	 * Obtener la meta del anio de un empleado
	 * @param anio
	 * @param idEmpleado
	 * @return
	 */
	VacacionMeta obtenerVacacionPorAnio(int anio, long idEmpleado);
	
	/**
	 * consolidar la meta del anio de un empleado
	 * @param empleado
	 * @param anio
	 * @param usuarioOperacion
	 * @return
	 */
	VacacionMeta consolidarMetaAnual(Empleado empleado, int anio, String usuarioOperacion);

	/**
	 * actualiza la meta, actualiza los dáis de la meta
	 * @param empleado
	 * @param anio
	 * @param diasActualizar
	 * @param usuarioOperacion
	 * @return
	 */
	VacacionMeta actualizarMeta(int anio, VacacionProgramacion programacion, boolean cancelarProgramcion,
			String usuarioOperacion);
	
	VacacionMeta actualizarMeta(VacacionMeta meta, String usuarioOperacion);
	
	Optional<VacacionMeta> obtenerMeta(long id);
	

}
