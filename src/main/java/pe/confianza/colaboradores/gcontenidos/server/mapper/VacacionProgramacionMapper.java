package pe.confianza.colaboradores.gcontenidos.server.mapper;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramacionTramo;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionResumen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

public class VacacionProgramacionMapper {
	
	public static VacacionProgramacion convert(final RequestProgramacionVacacion source) {
		VacacionProgramacion destination = new VacacionProgramacion();
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setNumeroDias(Utilitario.obtenerDiferenciaDias(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroSabados(Utilitario.obtenerCantidadSabados(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroDomingos(Utilitario.obtenerCantidadDomingos(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroReprogramaciones(0);
		destination.setIdProgramacionOriginal(null);
		destination.setVacacionesAdelantadas(false);
		destination.setInterrupcion(false);
		destination.setAnulacion(false);
		destination.setDiasGozados(0);
		destination.setDiasPendientesGozar(destination.getNumeroDias());
		destination.setDiasAnulados(0);
		destination.setDiasReprogramados(0);
		return destination;
	}
	
	public static VacacionProgramacion convert(final RequestReprogramacionTramo source, final VacacionProgramacion programacionSource) {
		VacacionProgramacion destination = new VacacionProgramacion();
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setNumeroDias(Utilitario.obtenerDiferenciaDias(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroSabados(Utilitario.obtenerCantidadSabados(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroDomingos(Utilitario.obtenerCantidadDomingos(source.getFechaInicio(), source.getFechaFin()));
		destination.setNumeroReprogramaciones(0);
		destination.setIdProgramacionOriginal(programacionSource.getId());
		destination.setNumeroReprogramaciones(programacionSource.getNumeroReprogramaciones() == null ? 1 :  (programacionSource.getNumeroReprogramaciones() + 1));
		destination.setVacacionesAdelantadas(false);
		destination.setInterrupcion(false);
		destination.setAnulacion(false);
		destination.setDiasGozados(0);
		destination.setDiasPendientesGozar(destination.getNumeroDias());
		destination.setDiasAnulados(0);
		destination.setDiasReprogramados(0);
		return destination;
	}
	
	public static ResponseProgramacionVacacion convert(final VacacionProgramacion source, CargaParametros cargaParametros) {
		ResponseProgramacionVacacion destination = new ResponseProgramacionVacacion();
		String[] estado = cargaParametros.getEstadoProgramacionDescripcion(source.getEstado().id);
		destination.setId(source.getId());
		destination.setUsuarioBT(source.getPeriodo().getEmpleado().getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setIdEstado(source.getEstado().id);
		destination.setDescripcionEstado(estado[0]);
		destination.setLeyendaEstado(estado[1]);
		destination.setDias(source.getNumeroDias());
		destination.setPeriodo(source.getPeriodo().getDescripcion());
		destination.setOrden(source.getOrden());
		destination.setAdelantada(source.isVacacionesAdelantadas());
		destination.setInterrupcion(source.isInterrupcion());
		destination.setAnulacion(source.isAnulacion());
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
	
	public static ResponseProgramacionVacacionReprogramar convertReprogramacion(final VacacionProgramacion source, CargaParametros cargaParametros) {
		ResponseProgramacionVacacionReprogramar destination = new ResponseProgramacionVacacionReprogramar();
		destination.setId(source.getId());
		destination.setUsuarioBT(source.getPeriodo().getEmpleado().getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setIdEstado(source.getEstado().id);
		destination.setDescripcionEstado(cargaParametros.getEstadoProgramacionDescripcion(source.getEstado().id)[0]);
		destination.setDias(source.getNumeroDias());
		destination.setPeriodo(source.getPeriodo().getDescripcion());
		destination.setOrden(source.getOrden());
		return destination;
	}
	
	public static ResponseProgramacionVacacionResumen convertResumen(final VacacionProgramacion source, CargaParametros cargaParametros) {
		ResponseProgramacionVacacionResumen destination = new ResponseProgramacionVacacionResumen();
		String[] estado = cargaParametros.getEstadoProgramacionDescripcion(source.getEstado().id);
		destination.setId(source.getId());
		destination.setUsuarioBT(source.getPeriodo().getEmpleado().getUsuarioBT());
		destination.setFechaInicio(source.getFechaInicio());
		destination.setFechaFin(source.getFechaFin());
		destination.setIdEstado(source.getEstado().id);
		destination.setDescripcionEstado(estado[0]);
		destination.setLeyendaEstado(estado[1]);
		destination.setDias(source.getNumeroDias());
		destination.setPeriodo(source.getPeriodo().getDescripcion());
		destination.setOrden(source.getOrden());
		destination.setAdelantada(source.isVacacionesAdelantadas());
		destination.setInterrupcion(source.isInterrupcion());
		destination.setAnulacion(source.isAnulacion());
		destination.setDiasGozados(source.getDiasGozados());
		destination.setDiasPorGozar(source.getDiasPendientesGozar());
		destination.setDiasAnulados(source.getDiasAnulados());
		destination.setDiasReprogramados(source.getDiasReprogramados());
		destination.setNombreEmpleado(source.getPeriodo().getEmpleado().getNombreCompleto());
		return destination;
	}
	
	

	
}
