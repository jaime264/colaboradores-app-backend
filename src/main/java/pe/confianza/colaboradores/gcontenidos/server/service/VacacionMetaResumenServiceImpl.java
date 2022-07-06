package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionMetaResumenDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;

@Service
public class VacacionMetaResumenServiceImpl implements VacacionMetaResumenService {
	
	@Autowired
	private VacacionMetaResumenDao vacacionMetaResumenDao;

	@Override
	public List<VacacionMetaResumen> listarResumenAnio(int anio) {
		List<VacacionMetaResumen> resumenes = vacacionMetaResumenDao.listarResumenPorAnio(anio);
		resumenes = resumenes == null ? new ArrayList<>() : resumenes;
		return resumenes;
	}

}
