package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Puesto;

@Repository
public interface PuestoDao extends JpaRepository<Puesto, Long>{

	Optional<Puesto> findOneByCodigo(long codigo);
}
