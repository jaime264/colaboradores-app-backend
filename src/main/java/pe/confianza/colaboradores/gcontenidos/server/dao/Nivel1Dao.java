package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;

@Repository
public interface Nivel1Dao extends MongoRepository<Nivel1, Long> {
	
	public List<Nivel1> findAll();
	
	public Optional<Nivel1> findById(Long id);

}
