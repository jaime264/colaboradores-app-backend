package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel2;

public interface Nivel2Service {

	public List<Nivel2> findByNivel1(Long idNivel1);
	
	public Optional<Nivel2> findById(Long id);
}
