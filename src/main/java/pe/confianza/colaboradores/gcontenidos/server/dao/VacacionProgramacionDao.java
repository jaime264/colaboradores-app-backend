package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Empleado;

@Repository
public interface VacacionProgramacionDao extends JpaRepository<VacacionProgramacion, Long> {
	
	List<VacacionProgramacion> findByEmpleado(Empleado empleado);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.empleado.usuarioBT = ?1")
	List<VacacionProgramacion> findByUsuarioBT(String usuarioBT);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.empleado.usuarioBT = ?1 AND vp.idEstado = ?2 ")
	List<VacacionProgramacion> findByUsuarioBTAndIdEstado(String usuarioBT, int idEstado);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.empleado.usuarioBT = ?1 AND vp.periodo = ?2 ")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodo(String usuarioBT, String periodo);
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.empleado.usuarioBT = ?1 AND vp.periodo = ?2 AND vp.idEstado = ?3 ")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodoAndEstado(String usuarioBT, String periodo, int idEstado);
	
	/*List<VacacionProgramacion> findVacacionProgramacionByUsuarioBT(String usuarioBt);

	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndPeriodo(String usuarioBt, String periodo);
	
	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndIdEstado(String usuarioBt, int idEstado);
	
	List<VacacionProgramacion> findVacacionProgramacionByUsuarioBTAndPeriodoAndIdEstado(String usuarioBt, String periodo, int idEstado);*/
}
