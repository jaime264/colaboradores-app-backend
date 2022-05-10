package pe.confianza.colaboradores.gcontenidos.server.dao.mariadb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Comentario;

@Repository
public interface ComentarioDao extends JpaRepository<Comentario, Long> {
	
	@Query("SELECT cm FROM comentario cm WHERE cm.publicacion.id = ?1")
	public List<Comentario> findByPublicacion(Long idPublicacion);

}
