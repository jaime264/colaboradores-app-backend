package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;

@Repository
public interface ParametrosDao extends JpaRepository<Parametro, Long> {

	List<Parametro> findAll();

	Optional<Parametro> findOneByCodigo(String codigo);
}
