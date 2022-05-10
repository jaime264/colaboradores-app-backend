package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Parametro;

@Repository
public interface ParametrosDao extends JpaRepository<Parametro, Long> {

	List<Parametro> findAll();

	Optional<Parametro> findOneByCodigo(String codigo);
}
