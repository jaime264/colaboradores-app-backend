package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Parametro;

public interface ParametrosService {
	
	List<Parametro> listParams();
	
	ResponseParametro registrar(RequestParametro request);
	
	Parametro buscarPorCodigo(String codigo);
	
	ResponseParametro buscarPorId(long id);

}
