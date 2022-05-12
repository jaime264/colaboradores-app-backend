package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionOld;

@Repository
public interface PublicacionDao extends MongoRepository<PublicacionOld, Long> {
	
	public List<PublicacionOld> findAll();
	
	@Query(value="{'usuarios.usuarioBT': ?0, 'fecha': {$gt: ?1}}", sort="{'fecha': -1}", fields="{ '_id' : 0}")
	public List<PublicacionOld> findAllUser(String user, Long lastPost, Pageable page);
	
	@Query(value="{'usuarios.usuarioBT': ?0, 'fecha': {$lt: ?1}}", sort="{'fecha': -1}", fields="{ '_id' : 0}")
	public List<PublicacionOld> findAllUserBack(String user, Long lastPost, Pageable page);
	
	@Query(value="{'id': ?0}")
	public Optional<PublicacionOld> findByIdPost(Long id);

}
