package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.PresupuestoGeneralGastoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoGeneralGasto;

@Service
public class PresupuestoGeneralGastoServiceImpl implements PresupuestoGeneralGastoService {
	
	@Autowired
	private PresupuestoGeneralGastoDao presupuestoGeneralGastoDao;

	@Override
	public List<PresupuestoGeneralGasto> listarHabilitados() {
		List<PresupuestoGeneralGasto> prepuestos = presupuestoGeneralGastoDao.listarHabilitados();
		prepuestos = prepuestos == null ? new ArrayList<>() : prepuestos;
		return prepuestos;
	}

	@Override
	public PresupuestoGeneralGasto buscarPorCodigo(long codigo) {
		Optional<PresupuestoGeneralGasto> opt = presupuestoGeneralGastoDao.findOneByCodigo(codigo);
		if(opt.isPresent())
			return opt.get();
		return null;
	}

}
