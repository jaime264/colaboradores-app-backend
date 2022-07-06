package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;

public interface VacacionMetaResumenService {

	List<VacacionMetaResumen> listarResumenAnio(int anio);
}
