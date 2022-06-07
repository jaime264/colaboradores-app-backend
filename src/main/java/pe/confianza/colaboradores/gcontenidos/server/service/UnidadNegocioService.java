package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadNegocio;

public interface UnidadNegocioService {
	
	UnidadNegocio obtenerUnidadNegocioPorCodigo(long codigo);

}
