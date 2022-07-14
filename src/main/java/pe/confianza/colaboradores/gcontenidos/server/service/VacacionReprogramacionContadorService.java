package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionReprogramacionContador;

public interface VacacionReprogramacionContadorService {
	
	Optional<VacacionReprogramacionContador> obtenerPorEmpleadoAndAnio(Empleado empleado, int anio);
	
	VacacionReprogramacionContador registrar(long idEmpleado, int anio, String usuarioOperacion);
	
	VacacionReprogramacionContador actualizarContador(long idEmpleado, int anio, String usuarioOperacion);

}
