package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.Date;
import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;

public interface VacacionProgramacionService {
	
	ResponseProgramacionVacacion registroProgramacion(RequestProgramacionVacacion programacion);
	
	String obtenerPeriodoIncompleto(Date fechaIngreso, String usuarioBt);
	
	List<ResponseProgramacionVacacion> obtenerProgramacion(String estado, String periodo, String usuarioBt);
	
	
}
