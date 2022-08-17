package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoTipo;

@Repository
public interface GastoConceptoTipoDao extends JpaRepository<GastoConceptoTipo, Long>{

}
