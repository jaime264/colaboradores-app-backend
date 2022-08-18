package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;

public interface AgenciaService {
	
	List<Agencia> listarHabilitados();
	
	List<Agencia> listarPorCorredor(String codigoCorredor);
	
	Agencia buscarPorCodigo(String codigo);

}
