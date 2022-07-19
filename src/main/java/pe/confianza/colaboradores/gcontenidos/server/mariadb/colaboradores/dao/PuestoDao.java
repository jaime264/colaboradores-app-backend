package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;

@Repository
public interface PuestoDao extends JpaRepository<Puesto, Long>{

	Optional<Puesto> findOneByCodigo(long codigo);
	
	@Query("SELECT p FROM Puesto p WHERE p.descripcion LIKE CONCAT('%',?1,'%') AND p.estadoRegistro = 'A' ORDER BY p.descripcion ASC")
	List<Puesto> listarPuestos(String descripcion);
}
