package pe.confianza.colaboradores.gcontenidos.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionEntity;

@Repository
public interface PublicacionAppDao extends JpaRepository<PublicacionEntity, Long> {

}
