package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

@Repository
public interface VacacionProgramacionDao extends JpaRepository<VacacionProgramacion, Long> {
	
	//List<VacacionProgramacion> findByEmpleado(Empleado empleado);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.estadoRegistro = 'A'")
	List<VacacionProgramacion> findByUsuarioBT(String usuarioBT);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.idEstado = ?2 AND vp.estadoRegistro = 'A'")
	List<VacacionProgramacion> findByUsuarioBTAndIdEstado(String usuarioBT, int idEstado);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.periodo.descripcion = ?2 AND vp.estadoRegistro = 'A'")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodo(String usuarioBT, String periodo);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.periodo.descripcion = ?2 AND vp.idEstado = ?3 AND vp.estadoRegistro = 'A'")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodoAndEstado(String usuarioBT, String periodo, int idEstado);
	
	/*List<VacacionProgramacion> findVacacionProgramacionByUsuarioBT(String usuarioBt);

	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndPeriodo(String usuarioBt, String periodo);
	
	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndIdEstado(String usuarioBt, int idEstado);
	
	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndPeriodoAndIdEstado(String usuarioBt, String periodo, int idEstado);*/
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.id = ?1 AND vp.idEstado = ?2 AND vp.estadoRegistro = 'A' order by vp.orden asc")
	List<VacacionProgramacion> findByPeriodoAndEstado(long idPeriodo, int idEstado);
	
	@Procedure(procedureName =  "proc_vacacion_programacion_actualizar_estado")
	void actualizarEstadoProgramaciones();
	
	@Modifying
	@Query("update VacacionProgramacion v set v.idEstado = ?1 where v.id = ?2")
	void aprobarVacacionByPeriodo(int idEstado, Long id);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.id = ?1 AND vp.estadoRegistro = 'A' order by vp.orden desc")
	List<VacacionProgramacion> findByIdPeriodo(long idPeriodo);
	
	@Query(value = "SELECT (CASE WHEN SUM(numero_dias) IS NULL THEN 0 ELSE SUM(numero_dias) END) FROM vacacion_programacion WHERE id_periodo = ?1 AND id_estado = ?2 AND estado_registro = 'A'", nativeQuery = true)
	int obtenerSumaDiasPorIdPeriodoYEstado(long idPeriodo, int idEstado);
	
	@Procedure(name = "VacacionProgramacion.programacionContarPorUnidadNegocio")
	long contarProgramacionPorUnidadNegocioEmpleado(@Param("idEmpleado") long idEmpleado, @Param("strFechaInicioProgramacion") String strFechaInicioProgramacion, @Param("strFechaFinProgramacion") String strFechaFinProgramacion);
	
	@Procedure(name = "VacacionProgramacion.programacionContarPorCorredorYPuesto")
	long contarProgramacionPorCorredorEmpleadoPuesto(@Param("idEmpleado") long idEmpleado, @Param("descripcionPuesto") String descripcionPuesto, @Param("strFechaInicioProgramacion") String strFechaInicioProgramacion, @Param("strFechaFinProgramacion") String strFechaFinProgramacion);
	
	@Procedure(name = "VacacionProgramacion.programacionContarPorTerrirotioYPuesto")
	long contarProgramacionPorTerritorioEmpleadoPuesto(@Param("idEmpleado") long idEmpleado, @Param("descripcionPuesto") String descripcionPuesto, @Param("strFechaInicioProgramacion") String strFechaInicioProgramacion, @Param("strFechaFinProgramacion") String strFechaFinProgramacion);
	
	@Procedure(name = "VacacionProgramacion.programacionContarPorPuesto")
	long contarProgramacionPorEmpleadoPuesto(@Param("idEmpleado") long idEmpleado, @Param("descripcionPuesto") String descripcionPuesto, @Param("strFechaInicioProgramacion") String strFechaInicioProgramacion, @Param("strFechaFinProgramacion") String strFechaFinProgramacion);
	
	
	@Procedure(name = "VacacionProgramacion.programacionContarPorAgencia")
	long contarProgramacionPorEmpleadoAgencia(@Param("idEmpleado") long idEmpleado, @Param("descripcionPuesto") String descripcionPuesto, @Param("strFechaInicioProgramacion") String strFechaInicioProgramacion, @Param("strFechaFinProgramacion") String strFechaFinProgramacion);



}
