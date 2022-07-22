package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorJefe;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelI;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelII;

public interface VacacionAprobadorService {
	
	List<VacacionAprobadorNivelI> listarAprobadoresNivelI();
	
	List<VacacionAprobadorNivelII> listarAprobadoresNivelII();
	
	List<VacacionAprobadorJefe> listarJefesInmmediato();

}
