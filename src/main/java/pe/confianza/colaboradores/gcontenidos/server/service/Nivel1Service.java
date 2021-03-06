package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel1;

public interface Nivel1Service {

	public List<Nivel1> findAll();
	
	public Optional<Nivel1> findById(Long id);
	
}
