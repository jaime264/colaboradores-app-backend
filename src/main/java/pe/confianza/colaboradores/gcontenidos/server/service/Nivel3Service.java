package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel3;

import java.util.List;

public interface Nivel3Service {

	public List<Nivel3> findByNivel2(Long idNivel2);
}
