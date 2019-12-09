package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;

public interface Nivel2Dao extends CrudRepository<Nivel2, Long>{
	
	public List<Nivel2> findByNivel1(Nivel1 nivel1);
}
