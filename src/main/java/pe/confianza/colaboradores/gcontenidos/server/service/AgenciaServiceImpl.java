package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.AgenciaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;

@Service
public class AgenciaServiceImpl implements AgenciaService {

	@Autowired
	private AgenciaDao dao;
	
	@Override
	public List<Agencia> listarHabilitados() {
		List<Agencia> agencias = dao.listarHabilitados();
		agencias = agencias == null ? new ArrayList<>() : agencias;
		return agencias;
	}

	@Override
	public List<Agencia> listarPorCorredor(String codigoCorredor) {
		List<Agencia> agencias = dao.listarHabilitadosPorCorredor(codigoCorredor);
		agencias = agencias == null ? new ArrayList<>() : agencias;
		return agencias;
	}

	@Override
	public Agencia buscarPorCodigo(String codigo) {
		List<Agencia> agencias = dao.listarAgenciaPorCodigo(codigo);
		if(agencias == null)
			return null;
		if(agencias.isEmpty())
			return null;
		if(agencias.size() > 1)
			return null;
		return agencias.get(0);
	}

	@Override
	public List<Agencia> listarPorTerritorio(String codigoTerritorio) {
		List<Agencia> agencias = dao.listarHabilitadosPorTerritorio(codigoTerritorio);
		agencias = agencias == null ? new ArrayList<>() : agencias;
		return agencias;
	}

}
