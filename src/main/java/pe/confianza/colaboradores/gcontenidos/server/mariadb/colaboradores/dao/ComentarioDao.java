package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;

@Repository
public interface ComentarioDao extends JpaRepository<Comentario, Long> {
	
	@Query("SELECT cm FROM Comentario cm WHERE cm.publicacion.id = ?1 and cm.activo=?2")
	public List<Comentario> findByPublicacion(Long idPublicacion, Boolean activo);
	
	@Query("SELECT cm FROM Comentario cm WHERE cm.activo = ?1 order by cm.id desc")
	public List<Comentario> listByActivo(Boolean activo);
	


}
