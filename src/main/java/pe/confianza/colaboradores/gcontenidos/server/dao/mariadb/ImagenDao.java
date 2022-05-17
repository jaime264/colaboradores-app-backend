package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Imagen;

public interface ImagenDao extends JpaRepository<Imagen, Long>{

	@Query("SELECT im FROM Imagen im WHERE im.publicacion.id = ?1 order by im.id desc")
	public List<Imagen> listImagenByPublicacion(Long idPublicacion);

	
}
