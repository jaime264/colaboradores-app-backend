package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;

@Repository
public interface Nivel2Dao extends MongoRepository<Nivel2, Long> {
	
	@Query("{'nivel2.id_nivel1': ?0}")
	public List<Nivel2> findByNivel1(Long idNivel1);
}
