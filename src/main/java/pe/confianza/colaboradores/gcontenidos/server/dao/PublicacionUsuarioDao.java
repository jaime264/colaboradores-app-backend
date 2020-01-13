package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionUsuario;

@Repository
public interface PublicacionUsuarioDao extends MongoRepository<PublicacionUsuario, Long> {
	
	@Query(value="{'idPublicacion': ?0, 'idUsuario': ?1, 'idReaccion': {$gt: 0}}")
	public List<PublicacionUsuario> findAllReaction(Long idpost, String user);
	
	@Query(value="{'idPublicacion': ?0, 'idUsuario': ?1}")
	public PublicacionUsuario findByRelation(Long idpost, String user);


}
