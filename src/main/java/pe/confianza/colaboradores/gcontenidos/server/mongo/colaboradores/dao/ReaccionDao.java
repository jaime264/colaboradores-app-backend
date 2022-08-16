package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Reaccion;

import java.util.List;

@Repository
public interface ReaccionDao extends MongoRepository<Reaccion, Long> {
	
	@Query(value = "{}", fields="{ '_id' : 0}")
	public List<Reaccion> findAll();

}
