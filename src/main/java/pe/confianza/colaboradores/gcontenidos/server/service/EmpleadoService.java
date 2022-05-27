package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;

public interface EmpleadoService {
	
	public Empleado actualizarInformacionEmpleado(String usuarioBT);
	
	public List<Empleado> listar();
	
	public Empleado buscarPorUsuarioBT(String usuarioBT);
	
	public List<EmplVacPerRes> listEmpleadoByprogramacion(Long codigo);

}
