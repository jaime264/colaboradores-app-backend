package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Parametro;

@Repository
public interface ParametrosDao extends MongoRepository<Parametro, Long> {
	
	public List<Parametro> findAll();

}
