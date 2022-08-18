package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.TerritorioDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;

@Service
public class TerritorioServiceImpl implements TerritorioService{

	@Autowired
	private TerritorioDao dao;
	
	@Override
	public List<Territorio> listarTerritorios() {
		List<Territorio> territorios = dao.listarHabilitados();
		territorios = territorios == null ? new ArrayList<>() : territorios;
		return territorios;
	}

	@Override
	public Territorio buscarPorCodigo(String codigo) {
		List<Territorio> territorios = dao.listarHabilitadosPorCodigo(codigo);
		if(territorios == null)
			return null;
		if(territorios.isEmpty())
			return null;
		if(territorios.size() > 1)
			return null;
		return territorios.get(0);
	}

}
