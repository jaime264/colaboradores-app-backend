package pe.confianza.colaboradores.gcontenidos.server.negocio;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionesExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionResumen;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface ExcepcionVacacionNegocio {
	
	Page<ResponseProgramacionVacacionResumen> resumenProgramaciones(RequestProgramacionesExcepcion filtro);

	ResponseStatus reprogramar(RequestProgramacionExcepcion reprogramacion);

	void validarDiasReprogramados(VacacionProgramacion programacion, RequestProgramacionExcepcion request);

	void actualizarPeriodo(Empleado empleado, long idPeriodo, String usuarioOperacion);

}
