package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoDistribucion;

@Repository
public interface GastoPresupuestoDistribucionDao extends JpaRepository<GastoPresupuestoDistribucion, Long>{

}
