package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroPuesto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePuesto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Puesto;

public interface PuestoService {
	
	List<ResponsePuesto> consultar(RequestFiltroPuesto filtro);
	
	Puesto buscarPorId(long id);

}
