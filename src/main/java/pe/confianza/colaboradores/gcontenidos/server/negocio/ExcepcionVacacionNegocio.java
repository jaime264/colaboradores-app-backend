package pe.confianza.colaboradores.gcontenidos.server.negocio;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionesExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionResumen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface ExcepcionVacacionNegocio {
	
	Page<ResponseProgramacionVacacionResumen> resumenProgramaciones(RequestProgramacionesExcepcion filtro);

	void reprogramar(RequestProgramacionExcepcion reprogramacion);

	void validarDiasReprogramados(VacacionProgramacion programacion, RequestProgramacionExcepcion request);

}
