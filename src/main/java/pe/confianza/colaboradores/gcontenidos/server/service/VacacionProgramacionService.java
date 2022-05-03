package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;

public interface VacacionProgramacionService {
	
	ResponseProgramacionVacacion registroProgramacion(RequestProgramacionVacacion programacion);
	
	List<ResponseProgramacionVacacion> obtenerProgramacion(String estado, String periodo, String usuarioBt);
	
	
}
