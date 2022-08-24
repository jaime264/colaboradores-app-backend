package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoConceptoDistribucionGasto;

@Repository
public interface PresupuestoConceptoDistribucionGastoDao extends JpaRepository<PresupuestoConceptoDistribucionGasto, Long>{

}
