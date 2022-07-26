package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionesExcepcion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramacionTramo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramarVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseAcceso;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionResumen;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ExcepcionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.FuncionalidadApp;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ExcepcionVacacionNegocioImpl implements ExcepcionVacacionNegocio {
	
	private static Logger logger = LoggerFactory.getLogger(ExcepcionVacacionNegocioImpl.class);
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public Page<ResponseProgramacionVacacionResumen> resumenProgramaciones(RequestProgramacionesExcepcion filtro) {
		logger.info("[BEGIN] resumenProgramaciones");
		try {
			Pageable paginacion = PageRequest.of(filtro.getNumeroPagina(), filtro.getTamanioPagina());
			if(filtro.getFiltro() == null)
				filtro.setFiltro("");
			Page<VacacionProgramacion> page = vacacionProgramacionService.listarProgramacionesDiferenteRegistrado(filtro.getFiltro(), paginacion);
			logger.info("[END] resumenProgramaciones");
			return page.map(p -> {
				return VacacionProgramacionMapper.convertResumen(p, cargaParametros);
			});
		} catch (Exception e) {
			logger.error("[ERROR] resumenProgramaciones", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	@Override
	public void reprogramar(RequestProgramacionExcepcion reprogramacion) {
		try {
			List<ResponseAcceso> accesos = empleadoService.consultaAccesos(reprogramacion.getUsuarioOperacion());
			boolean tienePermiso = false;
			for (ResponseAcceso acceso : accesos) {
				if(FuncionalidadApp.VACACIONES_EXCEPCIONES.codigo.equals(acceso.getFuncionalidadCodigo()) && acceso.isModificar()) {
					tienePermiso = true;
				}
			}
			if(!tienePermiso)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.sin_permiso", reprogramacion.getUsuarioOperacion()));
			VacacionProgramacion programacionOriginal = vacacionProgramacionService.buscarPorId(reprogramacion.getIdProgramacion());
			
			validarDiasReprogramados(programacionOriginal, reprogramacion);
			
			List<VacacionProgramacion> programaciones = reprogramacion.getTramos().stream().map(t -> {
				VacacionProgramacion prog = VacacionProgramacionMapper.convert(t, programacionOriginal);
				prog.setIdEstado(EstadoVacacion.GENERADO.id);
				prog.setPeriodo(programacionOriginal.getPeriodo());
				prog.setNumeroPeriodo((long)programacionOriginal.getPeriodo().getNumero());
				prog.setUsuarioCrea(reprogramacion.getUsuarioOperacion());
				prog.setFechaCrea(LocalDateTime.now());
				prog.calcularDias();
				return prog;
			}).collect(Collectors.toList());
			
			if(programacionOriginal.getIdEstado() == EstadoVacacion.GOZANDO.id) { //INTERRUPCION DE VACACIONES
				
			} else { // ANULACION Y REPROGRAMACION
				
			}
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] actualizarParametroVacaciones", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			logger.error("[ERROR] actualizarParametroVacaciones", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("[ERROR] actualizarParametroVacaciones", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}
	
	@Override
	public void validarDiasReprogramados(VacacionProgramacion programacion,	RequestProgramacionExcepcion request) {
		logger.info("[BEGIN] validarDiasReprogramados");
		int diasProgramados = programacion.getNumeroDias();
		int diasReprogramados = 0;
		for (RequestReprogramacionTramo tramo : request.getTramos()) {
			if(tramo.getFechaInicio().isAfter(tramo.getFechaFin()))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.rango_error"));
			if(!programacion.getPeriodo().programacionDentroPeriodoGoce(tramo.getFechaInicio(), tramo.getFechaFin()))
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.validacion.fuera_limite_goce", programacion.getPeriodo().getDescripcion()));
			diasReprogramados += Utilitario.obtenerDiferenciaDias(tramo.getFechaInicio(), tramo.getFechaFin());
		}
		if(diasProgramados != diasReprogramados)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.dias_error"));
		logger.info("[END] validarDiasReprogramados");
	}

}
