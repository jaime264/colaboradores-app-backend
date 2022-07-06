package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionPeriodoResumenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionPeriodoResumen;

@Service
public class VacacionPeriodoResumenServiceImpl implements VacacionPeriodoResumenService {
	
	@Autowired
	private VacacionPeriodoResumenDao vacacionPeriodoResumenDao;

	@Override
	public List<VacacionPeriodoResumen> listarPorAnio(int anio) {
		List<VacacionPeriodoResumen> resumenes = vacacionPeriodoResumenDao.listarPorAnio(anio);
		resumenes = resumenes == null ? new ArrayList<>() : resumenes;
		return resumenes;
	}

}
