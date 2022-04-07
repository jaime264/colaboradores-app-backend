package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;

public interface VacacionProgramacionService {
	
	ResponseProgramacionVacacion registroSolicitud(RequestProgramacionVacacion programacion);
	
	
}
