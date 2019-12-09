package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel2;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Nivel3;

public interface Nivel3Service {

	public List<Nivel3> findByNivel2(Nivel2 nivel2);
}
