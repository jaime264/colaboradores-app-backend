package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.dao.ParametrosDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Parametro;

@Service
public class ParametrosServiceImpl implements ParametrosService {
	
	@Autowired 
	private ParametrosDao parametrosDao;
	
	@Override
	public List<Parametro> listParams() {
		return parametrosDao.findAll();
	}

}
