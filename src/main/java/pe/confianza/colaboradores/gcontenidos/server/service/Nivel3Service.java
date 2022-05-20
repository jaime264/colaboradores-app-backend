package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Nivel3;

public interface Nivel3Service {

	public List<Nivel3> findByNivel2(Long idNivel2);
}
