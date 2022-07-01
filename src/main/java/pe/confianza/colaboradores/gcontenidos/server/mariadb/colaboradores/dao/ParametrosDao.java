package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;

@Repository
public interface ParametrosDao extends JpaRepository<Parametro, Long> {

	List<Parametro> findAll();
	
	@Query(value = "SELECT p FROM Parametro p WHERE p.estadoRegistro = 'A'")
	List<Parametro> listarActivos();

	Optional<Parametro> findOneByCodigo(String codigo);
}
