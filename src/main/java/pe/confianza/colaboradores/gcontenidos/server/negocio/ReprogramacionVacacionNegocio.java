package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConsultaVacacionesReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramarVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface ReprogramacionVacacionNegocio {
	
	List<ResponseProgramacionVacacionReprogramar> programacionAnual(RequestConsultaVacacionesReprogramar request);
	
	List<ResponseProgramacionVacacion> reprogramarTramo(RequestReprogramarVacacion request);
	
	ResponseProgramacionVacacion vacacionesAdelantadas(RequestProgramacionVacacion request);
	
	void validarPeriodoReprogramacion();
	
	void validarPermisoReprogramar(VacacionProgramacion programacion, String usuarioBT);
	
	void validarDiasReprogramados(VacacionProgramacion programacion, RequestReprogramarVacacion request);

	void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado);
}
