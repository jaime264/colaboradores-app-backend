package pe.confianza.colaboradores.gcontenidos.server.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.ParametroMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;

@Service
public class ParametrosServiceImpl implements ParametrosService {

	@Autowired
	private ParametrosConstants parametrosConstants;

	@Override
	public List<Parametro> listParams() {
		return parametrosConstants.findAll();
	}

	@Override
	public ResponseParametro registrar(RequestParametro request) {
		Parametro parametro = parametrosConstants.addParametro(request);
		return ParametroMapper.convert(parametro);
	}

	@Override
	public Parametro buscarPorCodigo(String codigo) {
		return parametrosConstants.search(codigo);
	}

	@Override
	public ResponseParametro buscarPorId(long id) {		
		Parametro parametro = parametrosConstants.search(id);
		if (parametro == null)
			throw new ModelNotFoundException("No existe parámetro con id " + id);
		return ParametroMapper.convert(parametro);
	}

	@Override
	public String buscarValorPorCodigo(String codigo) {
		return buscarPorCodigo(codigo).getValor();
	}

}
