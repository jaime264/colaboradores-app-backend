package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.PublicacionUsuario;

import java.util.List;

@Repository
public interface PublicacionUsuarioDao extends MongoRepository<PublicacionUsuario, Long> {
	
	@Query(value="{'idPublicacion': ?0, 'idUsuario': ?1, 'idReaccion': {$gt: 0}}")
	public List<PublicacionUsuario> findAllReaction(Long idpost, String user);
	
	@Query(value="{'idPublicacion': ?0, 'idUsuario': ?1}")
	public PublicacionUsuario findByRelation(Long idpost, String user);


}
