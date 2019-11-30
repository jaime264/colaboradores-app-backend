package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;

public interface Nivel1Dao extends CrudRepository<Nivel1, Long>{
	
	public List<Nivel1> findAll();

}
