package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;

public interface Nivel2Service {

	public List<Nivel2> findByNivel1(Long idNivel1);
	
	public Nivel2 findById(Long id);
}
