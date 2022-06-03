package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

@Repository
public interface VacacionProgramacionDao extends JpaRepository<VacacionProgramacion, Long> {
	
	//List<VacacionProgramacion> findByEmpleado(Empleado empleado);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1")
	List<VacacionProgramacion> findByUsuarioBT(String usuarioBT);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.idEstado = ?2 ")
	List<VacacionProgramacion> findByUsuarioBTAndIdEstado(String usuarioBT, int idEstado);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.periodo.descripcion = ?2 ")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodo(String usuarioBT, String periodo);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.periodo.descripcion = ?2 AND vp.idEstado = ?3 ")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodoAndEstado(String usuarioBT, String periodo, int idEstado);
	
	/*List<VacacionProgramacion> findVacacionProgramacionByUsuarioBT(String usuarioBt);

	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndPeriodo(String usuarioBt, String periodo);
	
	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndIdEstado(String usuarioBt, int idEstado);
	
	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndPeriodoAndIdEstado(String usuarioBt, String periodo, int idEstado);*/
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.id = ?1 AND vp.idEstado = ?2 order by vp.orden asc")
	List<VacacionProgramacion> findByPeriodoAndEstado(long idPeriodo, int idEstado);
	
	@Procedure(procedureName =  "proc_vacacion_programacion_actualizar_estado")
	void actualizarEstadoProgramaciones();
	
	@Modifying
	@Query("update VacacionProgramacion v set v.idEstado = ?1 where v.id = ?2")
	void aprobarVacacionByPeriodo(int idEstado, Long id);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.id = ?1 order by vp.orden desc")
	List<VacacionProgramacion> findByIdPeriodo(long idPeriodo);
	
	@Query(value = "SELECT (CASE WHEN SUM(numero_dias) IS NULL THEN 0 ELSE SUM(numero_dias) END) FROM vacacion_programacion WHERE id_periodo = ?1 AND id_estado = ?2", nativeQuery = true)
	int obtenerSumaDiasPorIdPeriodoYEstado(long idPeriodo, int idEstado);
}
