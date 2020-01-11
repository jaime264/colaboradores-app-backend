package pe.confianza.colaboradores.gcontenidos.server.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Vacacion;

@Repository
public interface VacacionesDao extends MongoRepository<Vacacion, Long> {

	Vacacion findByCodigoSpring(String codigoString);
}
