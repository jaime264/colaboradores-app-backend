package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Reaccion;

@Repository
public interface ReaccionDao extends MongoRepository<Reaccion, Long> {
	
	@Query(value = "{}", fields="{ '_id' : 0}")
	public List<Reaccion> findAll();

}
