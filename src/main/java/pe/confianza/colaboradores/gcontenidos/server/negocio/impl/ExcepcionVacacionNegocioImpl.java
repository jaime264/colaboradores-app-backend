package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseAcceso;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionResumen;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ExcepcionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.FuncionalidadApp;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ExcepcionVacacionNegocioImpl implements ExcepcionVacacionNegocio {
	
	private static Logger logger = LoggerFactory.getLogger(ExcepcionVacacionNegocioImpl.class);
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private PeriodoVacacionService periodoVacacionService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private NotificacionService notificacionService;

	@Override
	public Page<ResponseProgramacionVacacionResumen> resumenProgramaciones(RequestProgramacionesExcepcion filtro) {
		logger.info("[BEGIN] resumenProgramaciones");
		try {
			Pageable paginacion = PageRequest.of(filtro.getNumeroPagina(), filtro.getTamanioPagina());
			if(filtro.getFiltro() == null)
				filtro.setFiltro("");
			Page<VacacionProgramacion> page = vacacionProgramacionService.listarProgramacionesPorInterrumpirYAnular(filtro.getFiltro(), paginacion);
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
	public ResponseStatus reprogramar(RequestProgramacionExcepcion reprogramacion) {
		try {
			List<ResponseAcceso> accesos = empleadoService.consultaAccesos(reprogramacion.getUsuarioOperacion());
			boolean tienePermiso = false;
			for (ResponseAcceso acceso : accesos) {
				if(FuncionalidadApp.VACACIONES_EXCEPCIONES.codigo.equals(acceso.getFuncionalidadCodigo()) && acceso.isModificar()) {
					tienePermiso = true;
				}
			}
			if(!tienePermiso)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.sin_permiso", reprogramacion.getUsuarioOperacion()));
			VacacionProgramacion programacionOriginal = vacacionProgramacionService.buscarPorId(reprogramacion.getIdProgramacion());
			
			validarDiasReprogramados(programacionOriginal, reprogramacion);
			
			List<VacacionProgramacion> programaciones = reprogramacion.getTramos().stream().map(t -> {
				VacacionProgramacion prog = VacacionProgramacionMapper.convert(t, programacionOriginal);
				prog.setIdEstado(EstadoVacacion.APROBADO.id);
				prog.setPeriodo(programacionOriginal.getPeriodo());
				prog.setNumeroPeriodo((long)programacionOriginal.getPeriodo().getNumero());
				prog.setUsuarioCrea(reprogramacion.getUsuarioOperacion());
				prog.setFechaCrea(LocalDateTime.now());
				prog.calcularDias();
				prog.setIdProgramacionOriginal(programacionOriginal.getId());
				return prog;
			}).collect(Collectors.toList());
			int totalDiasReprogramados = programaciones.stream().map(p -> p.getNumeroDias()).reduce(0, Integer::sum);
			
			String tituloNotificacion = "";
			StringBuilder mensajeNotificacion =  new StringBuilder("Estimado Colaborador, De acuerdo a tu solicitud, se confirma la programación de tus vacaciones de acuerdo al siguiente detalle: ");
			String mensajeRespuesta = "";
			boolean esAdelantada = programacionOriginal.isVacacionesAdelantadas();
			if(programacionOriginal.getIdEstado() == EstadoVacacion.GOZANDO.id) { //INTERRUPCION DE VACACIONES
				programacionOriginal.setInterrupcion(true);
				programacionOriginal.setDiasGozados(programacionOriginal.getNumeroDias() - totalDiasReprogramados);
				programacionOriginal.setDiasPendientesGozar(totalDiasReprogramados);
				tituloNotificacion = "VACACION INTERRUMPIDA";
				mensajeNotificacion.append("\n").append(" DEl ").append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, programacionOriginal.getFechaInicio()))
				.append(" - AL ").append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, programacionOriginal.getFechaFin())).append(" / VACACIONES INTERRUMPIDAS");			
				mensajeRespuesta = Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.interrupcion.ok");
			} else if(programacionOriginal.getIdEstado() == EstadoVacacion.APROBADO.id) { // ANULACION Y REPROGRAMACION
				programacionOriginal.setAnulacion(true);
				programacionOriginal.setDiasAnulados(programacionOriginal.getNumeroDias());
				programacionOriginal.setDiasPendientesGozar(programacionOriginal.getNumeroDias());
				tituloNotificacion = "VACACION ANULADA";
				mensajeNotificacion.append("\n").append(" DEl ").append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, programacionOriginal.getFechaInicio()))
				.append(" - AL ").append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, programacionOriginal.getFechaFin())).append(" / VACACIONES ANULADAS")
				.append("\n");
				mensajeRespuesta = Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.anulacion.ok");
			} else {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.estado_error"));
			}
			programacionOriginal.setDiasReprogramados(totalDiasReprogramados);
			vacacionProgramacionService.actualizar(programacionOriginal, reprogramacion.getUsuarioOperacion());
			List<VacacionProgramacion> programacionesReprogramadas = vacacionProgramacionService.modificar(programaciones, reprogramacion.getUsuarioOperacion());
			List<Long> idsPeriodosModificados = programacionesReprogramadas.stream().map(prog -> prog.getPeriodo().getId()).distinct().collect(Collectors.toList());
			idsPeriodosModificados.forEach(periodoId -> {
				actualizarPeriodo(programacionOriginal.getPeriodo().getEmpleado(), periodoId, reprogramacion.getUsuarioOperacion());
			});
			for (VacacionProgramacion prog : programacionesReprogramadas) {
				mensajeNotificacion.append("\n").append(" DEL ").append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, prog.getFechaInicio())).append(" - AL ")
				.append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, prog.getFechaFin()))
				.append(" / ").append(prog.getNumeroDias()).append(" Días")
				.append(" / ").append(esAdelantada ? "VACACIONES ADELANTADAS" : "VACACIONES REPROGRAMADA")
				.append("\n");
			}
			Optional<NotificacionTipo> tipoNot = notificacionService
					.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_COLABORADOR.valor);
			Notificacion notificacion = notificacionService.registrar(tituloNotificacion, mensajeNotificacion.toString(),
					"", tipoNot.get(), programacionOriginal.getPeriodo().getEmpleado(), 
					reprogramacion.getUsuarioOperacion());
			notificacionService.enviarNotificacionApp(notificacion);
			notificacionService.enviarNotificacionCorreo(notificacion);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(mensajeRespuesta);
			return responseStatus;
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
			if(programacion.getIdEstado() == EstadoVacacion.GOZANDO.id) {
				if(tramo.getFechaInicio().isBefore(LocalDate.now()))
					throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.interrupcion.fecha_inicio_error"));
			}
			diasReprogramados += Utilitario.obtenerDiferenciaDias(tramo.getFechaInicio(), tramo.getFechaFin());
		}
		if(programacion.getIdEstado() == EstadoVacacion.GOZANDO.id) { //INTERRUPCION
			if(diasProgramados < diasReprogramados)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.interrupcion.dias_error"));
		}
		if(programacion.getIdEstado() == EstadoVacacion.APROBADO.id) { //ANULACION
			if(diasProgramados != diasReprogramados)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.excepciones.anulacion.dias_error"));
		}
		
		logger.info("[END] validarDiasReprogramados");
	}
	
	@Override
	public void actualizarPeriodo(Empleado empleado, long idPeriodo, String usuarioOperacion) {
		logger.info("[BEGIN] actualizarPeriodo {}", idPeriodo);
		periodoVacacionService.consolidarResumenDias(idPeriodo, usuarioOperacion);
		periodoVacacionService.actualizarPeriodo(empleado, idPeriodo, usuarioOperacion);
		logger.info("[END] actualizarPeriodo");
	}

}
