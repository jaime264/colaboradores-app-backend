package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;

@Repository
public interface ComentarioDao extends MongoRepository<Comentario, Long> {
	
	@Query(value = "{}", fields="{ '_id' : 0}")
	public List<Comentario> findAll();
	
	@Query(value = "{}", fields="{ '_id' : 0}")
	public List<Comentario> findByIdPublicacion(Long idPublicacion);

}
