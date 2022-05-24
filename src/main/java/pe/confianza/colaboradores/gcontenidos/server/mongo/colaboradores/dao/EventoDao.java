package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Evento;

@Repository
public interface EventoDao extends MongoRepository<Evento, Long> {
	
	@Query(value = "{}", fields="{ '_id' : 0}")
	public List<Evento> findAll();

}
