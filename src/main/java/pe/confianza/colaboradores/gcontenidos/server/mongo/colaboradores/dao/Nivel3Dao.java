package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel3;

@Repository
public interface Nivel3Dao extends MongoRepository<Nivel3, Long>{
	
	@Query("{'id_nivel2': ?0}")
	public List<Nivel3> findByNivel2(Long idNivel2);
}
