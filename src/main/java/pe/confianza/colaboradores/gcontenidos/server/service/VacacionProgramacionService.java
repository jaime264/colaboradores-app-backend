package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

public interface VacacionProgramacionService {
	
	List<VacacionProgramacion> listarPorPeriodoYEstado(PeriodoVacacion periodo, EstadoVacacion estado);
	
	VacacionProgramacion registrar(VacacionProgramacion programacion, String usuarioOperacion);
	
	VacacionProgramacion actualizar(VacacionProgramacion programacion, String usuarioOperacion);
	
	VacacionProgramacion buscarPorId(long idProgramacion);
	
	void eliminar(long idProgramacion);
	
	List<VacacionProgramacion> buscarPorUsuarioBTYPeriodo(String usuarioBT, String periodo);
	
	List<VacacionProgramacion> buscarPorUsuarioBTYEstado(String usuarioBT, EstadoVacacion estado);
	
	List<VacacionProgramacion> buscarPorUsuarioBTYPeriodoYEstado(String usuarioBT, String periodo, EstadoVacacion estado);
	
	List<VacacionProgramacion> buscarPorUsuarioBT(String usuarioBT);
	
	void actualizarEstadoProgramaciones();
	
	public void aprobarVacacionPeriodos(List<VacacionPeriodo> vacacionPeriodos);
	
	VacacionProgramacion obtenerUltimaProgramacion(long idPeriodo);
	
	List<VacacionProgramacion> listarPorPeriodo(long idPeriodo);
	
}
