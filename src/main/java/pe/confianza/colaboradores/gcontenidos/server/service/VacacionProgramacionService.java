package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;

public interface VacacionProgramacionService {
	
	VacacionProgramacion registroSolicitud(RequestProgramacionVacacion programacion);
	
	
}
