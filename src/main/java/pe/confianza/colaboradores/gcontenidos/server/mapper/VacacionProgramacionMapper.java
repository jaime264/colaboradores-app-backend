package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

public class VacacionProgramacionMapper {
	
	public static VacacionProgramacion convert(final RequestProgramacionVacacion source) {
		VacacionProgramacion destination = new VacacionProgramacion();
		destination.setCodigoSpring(source.getCodigoSpring());
		destination.setCodigoSpring(source.getCodigoSpring());
		destination.setUsuarioBT(source.getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		return destination;
	}
	
	public static ResponseProgramacionVacacion convert(final VacacionProgramacion source) {
		ResponseProgramacionVacacion destination = new ResponseProgramacionVacacion();
		destination.setId(source.getId());
		destination.setCodigoSpring(source.getCodigoSpring());
		destination.setUsuarioBT(source.getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setIdEstado(source.getEstado().id);
		destination.setDescripcionEstado(source.getEstado().descripcion);
		destination.setDias(Utilitario.obtenerDieferenciaDias(source.getFechaInicio(), source.getFechaFin()));
		destination.setPeriodo(source.getPeriodo());
		return destination;
	}
	
	

	
}
