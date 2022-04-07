package pe.confianza.colaboradores.gcontenidos.server.mapper;

import java.util.concurrent.TimeUnit;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.VacacionProgramacion;

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
		destination.setDias((int)(TimeUnit.DAYS.convert(source.getFechaFin().getTime() - source.getFechaInicio().getTime(), TimeUnit.MILLISECONDS)));
		return destination;
	}
	
	

	
}
