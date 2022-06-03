package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadNegocio;

@Repository
public interface UnidadNegocioDao extends JpaRepository<UnidadNegocio, Long> {
	
	Optional<UnidadNegocio> findOneByCodigo(long codigo);

}
