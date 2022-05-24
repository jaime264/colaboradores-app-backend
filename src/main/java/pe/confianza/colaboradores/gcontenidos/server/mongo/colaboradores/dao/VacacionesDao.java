package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Vacacion;

@Repository
public interface VacacionesDao extends MongoRepository<Vacacion, Long> {

	Vacacion findByCodigoSpring(String codigoString);
	

}
