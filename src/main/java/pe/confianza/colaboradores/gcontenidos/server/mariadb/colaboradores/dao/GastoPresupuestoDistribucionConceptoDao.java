package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucionConcepto;

@Repository
public interface GastoPresupuestoDistribucionConceptoDao extends JpaRepository<GastoPresupuestoDistribucionConcepto, Long>{

}
