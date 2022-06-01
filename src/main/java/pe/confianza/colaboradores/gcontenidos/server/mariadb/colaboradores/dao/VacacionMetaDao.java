package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;

@Repository
public interface VacacionMetaDao extends JpaRepository<VacacionMeta, Long> {
	
	@Query("SELECT vm FROM VacacionMeta vm WHERE vm.anio = ?1 AND vm.empleado.id = ?2")
	List<VacacionMeta> findByAnioAndIdEmpleado(int anio, long idEmpleado);

}
