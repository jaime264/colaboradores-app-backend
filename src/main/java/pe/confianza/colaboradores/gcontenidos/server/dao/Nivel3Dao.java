package pe.confianza.colaboradores.gcontenidos.server.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel3;

public interface Nivel3Dao extends CrudRepository<Nivel3, Long>{
	
	public List<Nivel3> findByNivel2(Nivel2 nivel2);
}
