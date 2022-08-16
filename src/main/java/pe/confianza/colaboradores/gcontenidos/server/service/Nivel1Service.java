package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel1;

import java.util.List;
import java.util.Optional;

public interface Nivel1Service {

	public List<Nivel1> findAll();
	
	public Optional<Nivel1> findById(Long id);
	
}
