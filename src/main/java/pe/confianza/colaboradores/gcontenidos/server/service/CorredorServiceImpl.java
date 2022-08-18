package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.CorredorDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;

@Service
public class CorredorServiceImpl implements CorredorService{

	@Autowired
	private CorredorDao dao;
	
	@Override
	public List<Corredor> listar(String codigoTerritorio) {
		List<Corredor> corredores = dao.listarhabilitadosPorTerritorio(codigoTerritorio);
		corredores = corredores == null ? new ArrayList<>() : corredores;
		return corredores;
	}

	@Override
	public Corredor buscarPorCodigo(String codigo) {
		List<Corredor> corredores = dao.listarhabilitadosPorCodigo(codigo);
		if(corredores == null)
			return null;
		if(corredores.isEmpty())
			return null;
		if(corredores.size() > 1)
			return null;
		return corredores.get(0);
	}

}
