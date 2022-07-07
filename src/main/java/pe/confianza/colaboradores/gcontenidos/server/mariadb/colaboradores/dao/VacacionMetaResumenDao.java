package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;

@Repository
public interface VacacionMetaResumenDao extends JpaRepository<VacacionMetaResumen, Long>{

	@Query("SELECT v FROM VacacionMetaResumen v WHERE v.anio = ?1")
	List<VacacionMetaResumen> listarResumenPorAnio(int anio);
}
