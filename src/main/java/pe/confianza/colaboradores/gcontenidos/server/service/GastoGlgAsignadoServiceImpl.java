package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoGlgAsignadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoGlgAsignado;

@Service
public class GastoGlgAsignadoServiceImpl implements GastoGlgAsignadoService{

	@Autowired
	private GastoGlgAsignadoDao dao;
	
	@Override
	public boolean empleadoEsGlgAsignado(long codigoEmpleado) {
		List<GastoGlgAsignado> glgs = dao.buscarPorCodigoEmpleado(codigoEmpleado);
		if(glgs == null)
			return false;
		if(glgs.isEmpty())
			return false;
		return true;
	}

}
