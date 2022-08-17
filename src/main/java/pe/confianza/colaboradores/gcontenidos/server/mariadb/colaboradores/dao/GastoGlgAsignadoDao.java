package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoGlgAsignado;

@Repository
public interface GastoGlgAsignadoDao extends JpaRepository<GastoGlgAsignado, Long> {

}
