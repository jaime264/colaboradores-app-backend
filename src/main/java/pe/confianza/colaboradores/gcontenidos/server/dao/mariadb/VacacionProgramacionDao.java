package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.VacacionProgramacion;

@Repository
public interface VacacionProgramacionDao extends JpaRepository<VacacionProgramacion, Long> {
	
	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1")
	List<VacacionProgramacion> findByUsuarioBT(String usuarioBT);

	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.idEstado = ?2 ")
	List<VacacionProgramacion> findByUsuarioBTAndIdEstado(String usuarioBT, int idEstado);

	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.periodo.descripcion = ?2 ")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodo(String usuarioBT, String periodo);

	@Query("SELECT vp FROM VacacionProgramacion vp WHERE vp.periodo.empleado.usuarioBT = ?1 AND vp.periodo.descripcion = ?2 AND vp.idEstado = ?3 ")
	List<VacacionProgramacion> findByUsuarioBTAndPeriodoAndEstado(String usuarioBT, String periodo, int idEstado);
}
