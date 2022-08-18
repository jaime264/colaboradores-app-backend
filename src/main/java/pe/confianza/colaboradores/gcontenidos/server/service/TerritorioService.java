package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;

public interface TerritorioService {
	
	List<Territorio> listarTerritorios();
	
	Territorio buscarPorCodigo(String codigo);

}
