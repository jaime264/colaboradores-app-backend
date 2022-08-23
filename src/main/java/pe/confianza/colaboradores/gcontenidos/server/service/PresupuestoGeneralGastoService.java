package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PresupuestoGeneralGasto;

public interface PresupuestoGeneralGastoService {
	
	List<PresupuestoGeneralGasto> listarHabilitados();
	
	PresupuestoGeneralGasto buscarPorCodigo(long codigo);

}
