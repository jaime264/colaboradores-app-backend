package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseAcceso;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTerminosCondiciones;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;

public interface EmpleadoService {
	
	Empleado actualizarInformacionEmpleado(String usuarioBT);
	
	List<Empleado> listar();
	
	Empleado buscarPorUsuarioBT(String usuarioBT);
	
	Empleado buscarPorCodigo(Long codigo);
		
	List<Empleado> findfechaNacimientoDeHoy();
	
	int obtenerCantidadEmpleadosPorPuestoYUnidadNegocio(long codigoUnidadNegocio, String descripcionPuesto);
	
	ResponseTerminosCondiciones aceptarTerminosCondiciones(String usuarioBT);
	
	ResponseTerminosCondiciones consultarTerminosCondiciones(String usuarioBT);
	
	Empleado getEmpleadoCorredorTerritorioBt(String usuarioBT);
	
	List<ResponseAcceso> consultaAccesos(String usuarioBT);

}
