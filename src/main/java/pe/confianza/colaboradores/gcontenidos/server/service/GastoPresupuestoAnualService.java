package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoPresupuestoAnual;

public interface GastoPresupuestoAnualService {
	
	List<GastoPresupuestoAnual> listarHabilitados();

}
