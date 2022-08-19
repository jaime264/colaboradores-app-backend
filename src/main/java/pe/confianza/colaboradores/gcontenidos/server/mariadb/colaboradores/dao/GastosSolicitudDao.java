package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastosSolicitud;

public interface GastosSolicitudDao extends JpaRepository<GastosSolicitud, Long>{

}
