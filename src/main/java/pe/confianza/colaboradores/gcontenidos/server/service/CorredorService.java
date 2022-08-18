package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;

public interface CorredorService {
	
	List<Corredor> listar(String codigoTerritorio);
	
	Corredor buscarPorCodigo(String codigo);

}
