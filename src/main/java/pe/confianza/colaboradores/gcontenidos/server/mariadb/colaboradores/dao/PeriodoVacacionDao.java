package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;


@Repository
public interface PeriodoVacacionDao extends JpaRepository<PeriodoVacacion, Long> {

	@Query("SELECT pv FROM PeriodoVacacion pv WHERE pv.empleado.id = ?1 order by pv.numero asc")
	List<PeriodoVacacion> findByIdEmpleado(long idEmpleado);
	
	@Modifying
	@Procedure(name = "PeriodoVacacion.consolidarResumenDias")
	void actualizarDias(@Param("periodoId") long periodoId);	
	
}
