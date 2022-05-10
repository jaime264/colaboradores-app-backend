package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.PeriodoVacacion;


@Repository
public interface PeriodoVacacionDao extends JpaRepository<PeriodoVacacion, Long> {

	@Query("SELECT pv FROM PeriodoVacacion pv WHERE pv.empleado.id = ?1 order by pv.numero asc")
	List<PeriodoVacacion> findByIEmpleado(long idEmpleado);
	
	
}
