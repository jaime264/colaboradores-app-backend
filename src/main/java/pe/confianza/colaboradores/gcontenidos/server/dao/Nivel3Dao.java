package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel3;

@Repository
public interface Nivel3Dao extends MongoRepository<Nivel3, Long>{
	
	@Query()
	public List<Nivel3> findByNivel2(Long idNivel2);
}
