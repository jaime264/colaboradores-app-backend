package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel2;

@Repository
public interface Nivel2Dao extends MongoRepository<Nivel2, Long> {
	
	@Query("{'id_nivel1': ?0}")
	public List<Nivel2> findByNivel1(Long idNivel1);
	
	@Query("{'id': ?0}")
	public Optional<Nivel2> findByIdNivel2(Long id);
}
