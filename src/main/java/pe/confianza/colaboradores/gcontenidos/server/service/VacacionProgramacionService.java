package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

public interface VacacionProgramacionService {
	
	List<VacacionProgramacion> listarPorPeriodoYEstado(PeriodoVacacion periodo, EstadoVacacion estado);
	
	List<VacacionProgramacion> registrar(List<VacacionProgramacion> programaciones, String usuarioOperacion);
	
	List<VacacionProgramacion> modificar(List<VacacionProgramacion> programaciones, String usuarioOperacion);
	
	VacacionProgramacion registrar(VacacionProgramacion programacion, String usuarioOperacion);
	
	VacacionProgramacion actualizar(VacacionProgramacion programacion, String usuarioOperacion);
	
	VacacionProgramacion buscarPorId(long idProgramacion);
	
	void eliminar(long idProgramacion, String usuarioOperacion);
	
	List<VacacionProgramacion> buscarPorUsuarioBTYPeriodo(String usuarioBT, String periodo);
	
	List<VacacionProgramacion> buscarPorUsuarioBTYEstado(String usuarioBT, EstadoVacacion estado);
	
	List<VacacionProgramacion> buscarPorUsuarioBTYPeriodoYEstado(String usuarioBT, String periodo, EstadoVacacion estado);
	
	List<VacacionProgramacion> buscarPorUsuarioBT(String usuarioBT);
	
	void actualizarEstadoProgramaciones();
	
	public void aprobarVacacionPeriodos(List<VacacionPeriodo> vacacionPeriodos);
	
	VacacionProgramacion obtenerUltimaProgramacion(long idPeriodo);
	
	List<VacacionProgramacion> listarPorPeriodo(long idPeriodo);
	
	int obtenerSumaDiasPorPeriodoYEstado(long idPeriodo, EstadoVacacion estado);
	
	long contarProgramacionPorUnidadNegocioEmpleado(Long idEmpleado, String descripcionPuesto, LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar);
	
	long contarProgramacionPorCorredorEmpleadoPuesto(long idEmpleado, String descripcionPuesto, LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar);
	
	long contarProgramacionPorTerritorioEmpleadoPuesto(long idEmpleado, String descripcionPuesto,LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar);
	
	long contarProgramacionPorEmpleadoPuesto(long idEmpleado, String descripcionPuesto,LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar);
	
	long contarProgramacionPorEmpleadoAgencia(long idEmpleado, String descripcionPuesto,LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar);
	
	long contarProgramacionPorEmpleadoRedOperaciones(long idEmpleado, LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar);
	
	List<EmplVacPerRes> listEmpleadoByprogramacion(RequestProgramacionEmpleado reqPrograEmp);
	
	List<Map<String, String>> listFilstrosVacacion(RequestProgramacionEmpleado reqFiltros);
	
	List<Map<String, String>> listFilstrosVacacionAprobacion(RequestFiltroVacacionesAprobacion reqFiltros);
	
	List<VacacionProgramacion> listarProgramacionesPorAnio(int anio, String usuarioBT);
	
	Map<Empleado, List<VacacionProgramacion>> listarProgramacionesPorAnioYAprobadorNivelI(int anio, long codigoAprobador);
	
	Map<Empleado, List<VacacionProgramacion>> listarProgramacionesPorAnioYAprobadorNivelII(int anio, long codigoAprobador);

	
	Page<VacacionProgramacion> listarProgramacionesDiferenteRegistrado(String nombre, Pageable pageable);
	
	List<EmplVacPerRes> listEmpleadoProgramacionFilter(RequestProgramacionEmpleado reqPrograEmp);
}
