package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

public class VacacionProgramacionMapper {
	
	public static VacacionProgramacion convert(final RequestProgramacionVacacion source) {
		VacacionProgramacion destination = new VacacionProgramacion();
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setNumeroDias(Utilitario.obtenerDiferenciaDias(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroSabados(Utilitario.obtenerCantidadSabados(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroDomingos(Utilitario.obtenerCantidadDomingos(source.getFechaInicio(), source.getFechaFin()));
		return destination;
	}
	
	public static ResponseProgramacionVacacion convert(final VacacionProgramacion source) {
		ResponseProgramacionVacacion destination = new ResponseProgramacionVacacion();
		destination.setId(source.getId());
		destination.setUsuarioBT(source.getPeriodo().getEmpleado().getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setIdEstado(source.getEstado().id);
		destination.setDescripcionEstado(source.getEstado().descripcion);
		destination.setDias(source.getNumeroDias());
		destination.setPeriodo(source.getPeriodo().getDescripcion());
		destination.setOrden(source.getOrden());
		return destination;
	}
	
	public static VacacionProgramacion clone(final VacacionProgramacion source) {
		VacacionProgramacion destination = new VacacionProgramacion();
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setNumeroDias(Utilitario.obtenerDiferenciaDias(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroSabados(Utilitario.obtenerCantidadSabados(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroDomingos(Utilitario.obtenerCantidadDomingos(source.getFechaInicio(), source.getFechaFin()));
		return destination;
	}
	
	public static ResponseProgramacionVacacionReprogramar convertReprogramacion(final VacacionProgramacion source) {
		ResponseProgramacionVacacionReprogramar destination = new ResponseProgramacionVacacionReprogramar();
		destination.setId(source.getId());
		destination.setUsuarioBT(source.getPeriodo().getEmpleado().getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setIdEstado(source.getEstado().id);
		destination.setDescripcionEstado(source.getEstado().descripcion);
		destination.setDias(source.getNumeroDias());
		destination.setPeriodo(source.getPeriodo().getDescripcion());
		destination.setOrden(source.getOrden());
		return destination;
	}
	
	

	
}
