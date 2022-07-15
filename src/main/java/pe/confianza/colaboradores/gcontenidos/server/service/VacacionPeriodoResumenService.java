package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionPeriodoResumen;

public interface VacacionPeriodoResumenService {
	
	List<VacacionPeriodoResumen> listarPorAnio(int anio);


}
