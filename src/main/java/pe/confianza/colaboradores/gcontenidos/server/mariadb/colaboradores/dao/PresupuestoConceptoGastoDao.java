package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoGasto;

@Repository
public interface PresupuestoConceptoGastoDao extends JpaRepository<PresupuestoConceptoGasto, Long>{

	Optional<PresupuestoConceptoGasto> findOneByCodigo(long codigo);
}
