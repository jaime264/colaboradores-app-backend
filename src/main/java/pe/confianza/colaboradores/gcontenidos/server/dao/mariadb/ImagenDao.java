package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Imagen;

public interface ImagenDao extends JpaRepository<Imagen, Long>{

}
