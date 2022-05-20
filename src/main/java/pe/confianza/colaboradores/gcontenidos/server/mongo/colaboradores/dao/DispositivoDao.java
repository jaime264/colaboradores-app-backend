package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Dispositivo;

@Repository
public interface DispositivoDao extends MongoRepository<Dispositivo, Long> {
	
	@Query(value="{'usuario': ?0}")
	public Optional<Dispositivo> findByUser(String user);
	
	@Query(value="{'usuario': { $in: ?0 }}", fields="{ '_id' : 0}")
	public List<Dispositivo> findByIdDeviceUser(List<String> users);

}
