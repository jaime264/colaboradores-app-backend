package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Publicacion;

@Repository
public interface PublicacionAppDao extends JpaRepository<Publicacion, Long> {

}
