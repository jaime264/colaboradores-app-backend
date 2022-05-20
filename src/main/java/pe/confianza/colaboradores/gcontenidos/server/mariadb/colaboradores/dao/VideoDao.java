package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Video;

public interface VideoDao extends JpaRepository<Video, Long>{
	
	@Query("SELECT vd FROM Video vd WHERE vd.publicacion.id = ?1 order by vd.id desc")
	public List<Imagen> listVideoByPublicacion(Long idPublicacion);
	
}
