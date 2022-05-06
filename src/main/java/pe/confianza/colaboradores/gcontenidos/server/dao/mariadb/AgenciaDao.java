package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Agencia;

@Repository
public interface AgenciaDao extends JpaRepository<Agencia, Long> {

	Optional<Agencia> findOneByCodigo(String codigo);
}
