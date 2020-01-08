package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Dispositivo;

@Repository
public interface DispositivoDao extends MongoRepository<Dispositivo, Long> {
	
	@Query(value="{'usuario': ?0}")
	public Optional<Dispositivo> findByUser(String user);

}
