package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.time.LocalDate;
import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCancelarProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGenerarProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarVacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestResumenVacaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseResumenVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;

public interface ProgramacionVacacionNegocio {

	ResponseProgramacionVacacion registro(RequestProgramacionVacacion programacion);
	
	void cancelar(RequestCancelarProgramacionVacacion cancelacion);
	
	List<ResponseProgramacionVacacion> generar(RequestGenerarProgramacionVacacion request);
	
	List<ResponseProgramacionVacacion> consultar(RequestListarVacacionProgramacion request);
	
	ResponseResumenVacacion consultar(RequestResumenVacaciones request);
	
	void validarFechaRegistro(LocalDate fechaInicioVacacion);
	
	void validarPeriodoRegistro(LocalDate fechaEvaluar);
	
	void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado);
	
	void validarRangoFechas(VacacionProgramacion programacion);
	
	void obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion);
	
	void validarTramoVacaciones(VacacionProgramacion programacion);
	
	void obtenerOrden(VacacionProgramacion programacion, String usuarioModifica);
	
	void actualizarPeriodo(Empleado empleado, String usuarioOperacion);
	
	void actualizarPeriodo(Empleado empleado, long idPeriodo, String usuarioOperacion);
	

}
