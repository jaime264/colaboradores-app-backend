package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.UnidadNegocioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadNegocio;

@Service
public class UnidadNegocioServiceImpl implements UnidadNegocioService {

	@Autowired
	private UnidadNegocioDao unidadNegocioDao;
	
	@Override
	public UnidadNegocio obtenerUnidadNegocioPorCodigo(long codigo) {
		Optional<UnidadNegocio> optUnidadNegocio = unidadNegocioDao.findOneByCodigo(codigo);
		if(!optUnidadNegocio.isPresent())
			return null;
		return optUnidadNegocio.get();
	}

}
