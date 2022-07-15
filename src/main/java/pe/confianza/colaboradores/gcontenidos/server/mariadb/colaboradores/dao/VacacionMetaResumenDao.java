package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;

@Repository
public interface VacacionMetaResumenDao extends JpaRepository<VacacionMetaResumen, Long>{

	@Query("SELECT v FROM VacacionMetaResumen v WHERE v.anio = ?1")
	List<VacacionMetaResumen> listarResumenPorAnio(int anio);
	
	@Query("SELECT v FROM VacacionMetaResumen v WHERE UPPER(v.empleadoNombreCompleto) LIKE ?1 AND v.anio = ?2 ORDER BY v.empleadoNombreCompleto ASC")
	Page<VacacionMetaResumen> consultarPorNombre(String nombre, int anio, Pageable pageable);
	
	@Query("SELECT v FROM VacacionMetaResumen v WHERE puestoId = ?1 AND v.anio = ?2 ORDER BY v.empleadoNombreCompleto ASC")
	Page<VacacionMetaResumen> consultarPorPuestoId(long id, int anio, Pageable pageable);
}
