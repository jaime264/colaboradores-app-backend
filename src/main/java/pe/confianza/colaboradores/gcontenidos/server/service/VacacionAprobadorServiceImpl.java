package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionAprobadorNivelIDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionAprobadorNivelIIDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelI;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelII;

@Service
public class VacacionAprobadorServiceImpl implements VacacionAprobadorService {
	
	@Autowired
	private VacacionAprobadorNivelIDao vacacionAprobadorNivelIDao;
	
	@Autowired
	private VacacionAprobadorNivelIIDao vacacionAprobadorNivelIIDao;

	@Override
	public List<VacacionAprobadorNivelI> listarAprobadoresNivelI() {
		List<VacacionAprobadorNivelI> aprobadores = vacacionAprobadorNivelIDao.findAll();
		aprobadores = aprobadores == null ? new ArrayList<>() : aprobadores;
		return aprobadores;
	}

	@Override
	public List<VacacionAprobadorNivelII> listarAprobadoresNivelII() {
		List<VacacionAprobadorNivelII> aprobadores = vacacionAprobadorNivelIIDao.findAll();
		aprobadores = aprobadores == null ? new ArrayList<>() : aprobadores;
		return aprobadores;
	}

}
