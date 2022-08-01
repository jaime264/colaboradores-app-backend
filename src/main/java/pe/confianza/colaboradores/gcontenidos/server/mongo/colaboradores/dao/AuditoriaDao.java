package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Auditoria;

@Repository
public interface AuditoriaDao extends MongoRepository<Auditoria, Long> {

}
