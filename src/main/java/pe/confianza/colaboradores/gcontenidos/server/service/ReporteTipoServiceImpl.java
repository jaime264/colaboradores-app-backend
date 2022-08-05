package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReporteTipoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteTipo;

@Service
public class ReporteTipoServiceImpl implements ReporteTipoService {

	@Autowired
	private ReporteTipoDao reporteTipoDao;
	
	
	@Override
	public List<ReporteTipo> listarActivos() {
		List<ReporteTipo> tipos = reporteTipoDao.listarActivos();
		tipos = tipos == null ? new ArrayList<>() : tipos;
		return tipos;
	}


	@Override
	public ReporteTipo buscarPorCodigo(String codigo) {
		Optional<ReporteTipo> optReporte = reporteTipoDao.findOneByCodigo(codigo);
		if(optReporte.isPresent())
			return optReporte.get();
		return null;
	}

}