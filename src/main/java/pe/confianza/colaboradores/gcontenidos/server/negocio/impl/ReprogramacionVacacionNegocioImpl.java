package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConsultaVacacionesReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramacionTramo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramarVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ReprogramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ReprogramacionVacacionNegocioImpl implements ReprogramacionVacacionNegocio {

	private static Logger logger = LoggerFactory.getLogger(ReprogramacionVacacionNegocioImpl.class);
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<ResponseProgramacionVacacionReprogramar> programacionAnual(RequestConsultaVacacionesReprogramar request) {
		logger.info("[BEGIN] programacionAnuales");
		try {
			List<ResponseProgramacionVacacionReprogramar> programaciones = vacacionProgramacionService.listarProgramacionesPorAnio(cargaParametros.getAnioPresente(), request.getUsuarioBT())
					.stream()
					.filter(p -> p.getIdEstado() == EstadoVacacion.APROBADO.id)
					.map(p -> {
						ResponseProgramacionVacacionReprogramar prog = VacacionProgramacionMapper.convertReprogramacion(p, cargaParametros);
						if(prog.getFechaFin().getMonthValue() == LocalDate.now().getMonthValue() && prog.getIdEstado() == EstadoVacacion.APROBADO.id) {
							prog.setReprogramar(true);
						} else {
							prog.setReprogramar(false);
						}
						return prog;
					}).collect(Collectors.toList());		
			logger.info("[END] programacionAnuales");
			return programaciones;
		} catch (AppException e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
		
	}

	@Override
	public List<ResponseProgramacionVacacion> reprogramarTramo(RequestReprogramarVacacion request) {
		try {
			validarPeriodoReprogramacion();
			VacacionProgramacion programacion = vacacionProgramacionService.buscarPorId(request.getIdProgramacion());
			if(programacion.getEstado().id != EstadoVacacion.APROBADO.id)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.estado_error", cargaParametros.getEstadoProgramacionDescripcion(EstadoVacacion.APROBADO.id)));
			if(programacion.getFechaFin().getMonthValue() != LocalDate.now().getMonthValue())
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.mes_error"));
			validarPermisoReprogramar(programacion, request.getUsuarioOperacion());
			validarDiasReprogramados(programacion, request);
			return null;
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new AppException(e.getMessage(), e); 
		} catch (Exception e) {
			logger.error("[ERROR] programacionAnual", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public void validarPeriodoReprogramacion() {
		logger.info("[BEGIN] validarPeriodoReprogramacion");
		LocalDate fechaActual = LocalDate.now();
		if(fechaActual.isBefore(cargaParametros.getFechaInicioReprogramacion()))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.fuera_fecha", new String[] { cargaParametros.DIA_INICIO_REPROGRAMACION, cargaParametros.DIA_FIN_REPROGRAMACION}));
		if(fechaActual.isAfter(cargaParametros.getFechaFinReprogramacion()))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.fuera_fecha", new String[] { cargaParametros.DIA_INICIO_REPROGRAMACION, cargaParametros.DIA_FIN_REPROGRAMACION}));
		logger.info("[END] validarPeriodoReprogramacion");
	}

	@Override
	public void validarPermisoReprogramar(VacacionProgramacion programacion, String usuarioBT) {
		logger.info("[BEGIN] validarPermisoReprogramar");
		if(!programacion.getPeriodo().getEmpleado().getUsuarioBT().equals(usuarioBT))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.sin_permiso", usuarioBT)); 
		logger.info("[END] validarPermisoReprogramar");
		
	}

	@Override
	public void validarDiasReprogramados(VacacionProgramacion programacion,	RequestReprogramarVacacion request) {
		int diasProgramados = programacion.getNumeroDias();
		int diasReprogramados = 0;
		for (RequestReprogramacionTramo tramo : request.getTramos()) {
			diasReprogramados += Utilitario.obtenerDiferenciaDias(tramo.getFechaInicio(), tramo.getFechaFin());
		}
		if(diasProgramados != diasReprogramados)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.dias_error"));
		
	}

}
