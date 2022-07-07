package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionPeriodoResumen;

@Repository
public interface VacacionPeriodoResumenDao extends JpaRepository<VacacionPeriodoResumen, Long> {

	@Query("SELECT v FROM VacacionPeriodoResumen v WHERE v.anio = ?1")
	List<VacacionPeriodoResumen> listarPorAnio(int anio);
	
}
