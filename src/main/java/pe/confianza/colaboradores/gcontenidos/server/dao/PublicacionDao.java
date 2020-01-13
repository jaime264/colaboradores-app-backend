package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;

@Repository
public interface PublicacionDao extends MongoRepository<Publicacion, Long> {
	
	public List<Publicacion> findAll();
	
	@Query(value="{'usuarios.usuarioBT': ?0, 'fecha': {$gt: ?1}}")
	public List<Publicacion> findAllUser(String user, Long lastPost);
	
	@Query(value="{'id': ?0}")
	public Optional<Publicacion> findByIdPost(Long id);

}
