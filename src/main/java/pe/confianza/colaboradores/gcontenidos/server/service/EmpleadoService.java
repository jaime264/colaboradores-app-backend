package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;

public interface EmpleadoService {
	
	Empleado actualizarInformacionEmpleado(String usuarioBT);
	
	List<Empleado> listar();
	
	Empleado buscarPorUsuarioBT(String usuarioBT);
	
	List<EmplVacPerRes> listEmpleadoByprogramacion(Long codigo);
	
	List<Empleado> findfechaNacimientoDeHoy();

}
