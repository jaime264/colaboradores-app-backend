package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.GastoPresupuestoAnualDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoAnual;

@Service
public class GastoPresupuestoAnualImpl implements GastoPresupuestoAnualService {
	
	@Autowired
	private GastoPresupuestoAnualDao gastoPresupuestoAnualDao;

	@Override
	public List<GastoPresupuestoAnual> listarHabilitados() {
		List<GastoPresupuestoAnual> prepuestos = gastoPresupuestoAnualDao.listarHabilitados();
		prepuestos = prepuestos == null ? new ArrayList<>() : prepuestos;
		return prepuestos;
	}

}
