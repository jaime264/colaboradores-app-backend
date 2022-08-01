package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel1;

import java.util.List;
import java.util.Optional;

@Repository
public interface Nivel1Dao extends MongoRepository<Nivel1, Long> {
	
	public List<Nivel1> findAll();
	
	@Query("{'id': ?0}")
	public Optional<Nivel1> findByIdNivel1(Long id);

}
