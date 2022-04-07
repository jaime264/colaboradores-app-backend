package pe.confianza.colaboradores.gcontenidos.server.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;

@Repository
public interface VacacionProgramacionDao extends MongoRepository<VacacionProgramacion, Long> {

	
}
