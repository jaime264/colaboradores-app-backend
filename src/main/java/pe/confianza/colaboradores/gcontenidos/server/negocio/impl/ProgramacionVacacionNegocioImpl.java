package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCancelarProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGenerarProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarVacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestResumenVacaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseResumenPeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseResumenVacacion;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametrosConstants;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ProgramacionVacacionNegocioImpl implements ProgramacionVacacionNegocio {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgramacionVacacionNegocioImpl.class);
	
	@Autowired
	private ParametrosConstants parametrosConstants;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private PeriodoVacacionService periodoVacacionService;
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private VacacionMetaService vacacionMetaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public ResponseProgramacionVacacion registro(RequestProgramacionVacacion programacion) {
		LOGGER.info("[BEGIN] registro: {} - {} - {}", new Object[] {programacion.getUsuarioBT(), programacion.getFechaInicio(), programacion.getFechaFin()});
		validarFechaRegistro(programacion.getFechaInicio());
		Empleado empleado = empleadoService.actualizarInformacionEmpleado(programacion.getUsuarioBT().trim());
		if(empleado == null)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", new String[] { programacion.getUsuarioBT()}));
		String usuarioOperacion = programacion.getUsuarioOperacion().trim();
		VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(programacion);
		vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);
		
		validarEmpleadoNuevo(vacacionProgramacion, empleado);
		validarRangoFechas(vacacionProgramacion);
		obtenerPeriodo(empleado, vacacionProgramacion);
		validarTramoVacaciones(vacacionProgramacion);
		obtenerOrden(vacacionProgramacion, usuarioOperacion);
		
		vacacionProgramacion = vacacionProgramacionService.registrar(vacacionProgramacion, usuarioOperacion);
		actualizarPeriodo(empleado,vacacionProgramacion.getPeriodo().getId(),  usuarioOperacion);
		vacacionMetaService.consolidarMetaAnual(empleado, LocalDate.now().getYear() + 1, programacion.getUsuarioOperacion());
		LOGGER.info("[END] registroProgramacion");
		return VacacionProgramacionMapper.convert(vacacionProgramacion);
	}
	
	@Override
	public void cancelar(RequestCancelarProgramacionVacacion cancelacion) {
		LOGGER.info("[BEGIN] cancelar");
		Empleado empleado = empleadoService.buscarPorUsuarioBT(cancelacion.getUsuarioOperacion().trim());
		if(empleado == null)
			throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", new String[] { cancelacion.getUsuarioOperacion() }));
		LocalDate ahora = LocalDate.now();
		validarPeriodoRegistro(ahora);
		VacacionProgramacion programacion = vacacionProgramacionService.buscarPorId(cancelacion.getIdProgramacion());
		if(programacion.getPeriodo().getEmpleado().getId() != empleado.getId())
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.cancelar.sin_permiso", new String[] { cancelacion.getUsuarioOperacion() }));
		if(programacion.getIdEstado() != EstadoVacacion.REGISTRADO.id)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.cancelar.estado_error", new String[] {EstadoVacacion.REGISTRADO.descripcion}));
		long idPeriodo = programacion.getPeriodo().getId();
		vacacionProgramacionService.eliminar(cancelacion.getIdProgramacion());
		actualizarPeriodo(empleado, idPeriodo,  cancelacion.getUsuarioOperacion());
		vacacionMetaService.consolidarMetaAnual(empleado, ahora.getYear() + 1, cancelacion.getUsuarioOperacion().trim());
		LOGGER.info("[END] cancelar");
	}
	
	@Override
	public List<ResponseProgramacionVacacion> generar(RequestGenerarProgramacionVacacion request) {
		LOGGER.info("[BEGIN] generar");
		Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioOperacion().trim());
		if(empleado == null)
			throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", new String[] { request.getUsuarioOperacion() }));
		LocalDate ahora = LocalDate.now();
		validarPeriodoRegistro(ahora);
		List<VacacionProgramacion> programacionesGenerar = vacacionProgramacionService.buscarPorUsuarioBTYEstado(request.getUsuarioOperacion().trim(), EstadoVacacion.REGISTRADO);
		List<ResponseProgramacionVacacion> response = new ArrayList<>();
		for (VacacionProgramacion programacion : programacionesGenerar) {
			programacion.setEstado(EstadoVacacion.GENERADO);
			programacion = vacacionProgramacionService.actualizar(programacion, request.getUsuarioOperacion());
			response.add(VacacionProgramacionMapper.convert(programacion));
		}
		actualizarPeriodo(empleado, request.getUsuarioOperacion());
		vacacionMetaService.consolidarMetaAnual(empleado, ahora.getYear() + 1, request.getUsuarioOperacion());
		LOGGER.info("[END] generar");
		return response;
	}
	
	@Override
	public List<ResponseProgramacionVacacion> consultar(RequestListarVacacionProgramacion request) {
		LOGGER.info("[BEGIN] consultar");
		Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioBT());
		if(empleado == null)
			throw new ModelNotFoundException("No existe el usuario " + request.getUsuarioBT());
		List<VacacionProgramacion> lstProgramacion = new ArrayList<>();
		EstadoVacacion estadoSeleccionado = null;
		if(!StringUtils.isEmpty(request.getEstado()))
			estadoSeleccionado = EstadoVacacion.getEstado(request.getEstado());
		
		if(estadoSeleccionado == null && StringUtils.isEmpty(request.getPeriodo()))
			lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBT(request.getUsuarioBT().trim());
		if(estadoSeleccionado != null && !StringUtils.isEmpty(request.getPeriodo()))
			lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBTYPeriodoYEstado(request.getUsuarioBT().trim(), request.getPeriodo().trim(), estadoSeleccionado);
		if(estadoSeleccionado == null && !StringUtils.isEmpty(request.getPeriodo()))
			lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBTYPeriodo(request.getUsuarioBT().trim(), request.getPeriodo().trim());
		if(estadoSeleccionado != null && StringUtils.isEmpty(request.getPeriodo()))
			lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBTYEstado(request.getUsuarioBT().trim(), estadoSeleccionado);		
		lstProgramacion.sort(Comparator.comparing(VacacionProgramacion::getFechaInicio));
		LOGGER.info("[END] consultar");
		return lstProgramacion.stream().map(p -> {
			return VacacionProgramacionMapper.convert(p);
		}).collect(Collectors.toList());
	}
	
	@Override
	public ResponseResumenVacacion consultar(RequestResumenVacaciones request) {
		LOGGER.info("[BEGIN] consultar");
		Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioBT());
		if(empleado == null)
			throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", new String[] {request.getUsuarioBT()}));
		
		LocalDate fechaConsulta = LocalDate.now();
		VacacionMeta meta = vacacionMetaService.obtenerVacacionPorAnio(fechaConsulta.getYear() + 1, empleado.getId());
		if(meta == null)
			throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", new String[] {request.getUsuarioBT()}));
		LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta(LocalDate.now().getYear() + 1);
		ResponseResumenVacacion response = new ResponseResumenVacacion();
		response.setFechaConsulta(fechaConsulta);
		response.setFechaCorte(fechaCorte);
		response.setNombres(empleado.getNombres());
		response.setApellidoPaterno(empleado.getApellidoPaterno());
		response.setApellidoMaterno(empleado.getApellidoMaterno());
		response.setFechaInicioLabores(empleado.getFechaIngreso());
		response.setFechaFinLabores(empleado.getFechaFinContrato());
		response.setCargo(empleado.getPuesto().getDescripcion().trim());
		
		ResponseResumenPeriodoVacacion periodoTrunco = null;
		ResponseResumenPeriodoVacacion periodoVencido = null;
		
		if(meta.getPeriodoVencido() != null) {
			PeriodoVacacion periodo = meta.getPeriodoVencido();
			VacacionProgramacion ultimaProgramacion = vacacionProgramacionService.obtenerUltimaProgramacion(periodo.getId());
			periodoVencido = new ResponseResumenPeriodoVacacion();
			periodoVencido.setDescripcion(periodo.getDescripcion());
			periodoVencido.setDias(meta.getDiasVencidos());
			periodoVencido.setFechaLimite(periodo.getFechaFinPeriodo());
			periodoVencido.setUltimoTramo(ultimaProgramacion == null ? 0  : ultimaProgramacion.getOrden());
		}
		
		if(meta.getPeriodoTrunco() != null) {
			PeriodoVacacion periodo = meta.getPeriodoTrunco();
			VacacionProgramacion ultimaProgramacion = vacacionProgramacionService.obtenerUltimaProgramacion(periodo.getId());
			periodoTrunco = new ResponseResumenPeriodoVacacion();
			periodoTrunco.setDescripcion(periodo.getDescripcion());
			periodoTrunco.setDias(meta.getDiasTruncos());
			periodoTrunco.setFechaLimite(periodo.getFechaFinPeriodo());
			periodoTrunco.setUltimoTramo(ultimaProgramacion == null ? 0  : ultimaProgramacion.getOrden());
		}
			
		response.setPeriodoTrunco(periodoTrunco);
		response.setPeriodoVencido(periodoVencido);
		response.setMeta(meta.getMeta());
		LOGGER.info("[BEGIN] consultar");
		return response;
	}


	@Override
	public void validarFechaRegistro(LocalDate fechaInicioVacacion) {
		LOGGER.info("[BEGIN] validarFechaRegistro");
		LocalDate ahora = LocalDate.now();
		if(fechaInicioVacacion.isBefore(ahora))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_inicio_error"));
		validarPeriodoRegistro(fechaInicioVacacion);
		LOGGER.info("[END] validarFechaRegistro");
		
	}
	
	@Override
	public void validarPeriodoRegistro(LocalDate fechaEvaluar) {
		LOGGER.info("[BEGIN] validarPeriodoRegistro");
		LocalDate fechaInicioRegistroProgramacion = parametrosConstants.getFechaInicioRegistroProgramacion(fechaEvaluar.getYear());
		LocalDate fechaFinRegistroProgramacion = parametrosConstants.getFechaFinRegistroProgramacion(fechaEvaluar.getYear());
		if(fechaEvaluar.isBefore(fechaInicioRegistroProgramacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.inicio_programacion_antes", fechaInicioRegistroProgramacion + ""));
		if(fechaEvaluar.isAfter(fechaFinRegistroProgramacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fin_programacion_despues", fechaFinRegistroProgramacion+ ""));
		LOGGER.info("[END] validarPeriodoRegistro");
	}

	@Override
	public void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado) {
		LOGGER.info("[BEGIN] validarEmpleadoNuevo");
		LocalDate fechaParaPedirVacacion = empleado.getFechaIngreso().plusMonths(6);
		LOGGER.info("Empleado -> fecha ingreso: {}", new Object[] {fechaParaPedirVacacion}  );
		if(programacion.getFechaInicio().isBefore(fechaParaPedirVacacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.empleado_nuevo"));
		LOGGER.info("[END] validarEmpleadoNuevo");
	}

	@Override
	public void validarRangoFechas(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarRangoFechas");
		if(programacion.getFechaInicio().isAfter(programacion.getFechaFin()))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.rango_error"));
		LOGGER.info("[END] validarRangoFechas");
	}

	@Override
	public void obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] obtenerPeriodo");
		LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta(LocalDate.now().getYear());
		VacacionMeta meta = vacacionMetaService.obtenerVacacionPorAnio(fechaCorte.getYear() + 1, empleado.getId());
		PeriodoVacacion periodoSeleccionado = null;
		if(meta.getDiasVencidos() > 0) {
			periodoSeleccionado = meta.getPeriodoVencido();
			if(meta.getDiasVencidos() < programacion.getNumeroDias())
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.programacion_dividir", new String[] { meta.getDiasVencidos() + "", periodoSeleccionado.getDescripcion() }));
		} else {
			if(meta.getDiasTruncos() > 0) {
				periodoSeleccionado = meta.getPeriodoTrunco();
				if(meta.getDiasTruncos() < programacion.getNumeroDias())
					throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.programacion_dividir", new String[] { meta.getDiasTruncos() + "", periodoSeleccionado.getDescripcion() }));
			} else {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.no_dias_gozar"));
			}
		}
		programacion.setPeriodo(periodoSeleccionado);
		programacion.setNumeroPeriodo((long)periodoSeleccionado.getNumero());
		LOGGER.info("[END] obtenerPeriodo");
	}

	@Override
	public void validarTramoVacaciones(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarTramoVacaciones");
		List<VacacionProgramacion> programaciones = new ArrayList<VacacionProgramacion>();
		List<VacacionProgramacion> programacionesRegistradas = vacacionProgramacionService.listarPorPeriodoYEstado(programacion.getPeriodo(), EstadoVacacion.REGISTRADO);
		List<VacacionProgramacion> promacionesGeneradas = vacacionProgramacionService.listarPorPeriodoYEstado(programacion.getPeriodo(), EstadoVacacion.GENERADO);
		List<VacacionProgramacion> promacionesAprobadas = vacacionProgramacionService.listarPorPeriodoYEstado(programacion.getPeriodo(), EstadoVacacion.APROBADO);
		List<VacacionProgramacion> promacionesGozando = vacacionProgramacionService.listarPorPeriodoYEstado(programacion.getPeriodo(), EstadoVacacion.GOZANDO);
		List<VacacionProgramacion> promacionesGozadas = vacacionProgramacionService.listarPorPeriodoYEstado(programacion.getPeriodo(), EstadoVacacion.GOZADO);
		programaciones.addAll(programacionesRegistradas);
		programaciones.addAll(promacionesGeneradas);
		programaciones.addAll(promacionesAprobadas);
		programaciones.addAll(promacionesGozando);
		programaciones.addAll(promacionesGozadas);
		programaciones = Utilitario.ordenarProgramaciones(programaciones);
		int diasAcumuladosVacaciones = 0;
		int contadorSabados = 0;
		int contadorDomingos = 0;
		for (VacacionProgramacion vacacionProgramacion : programaciones) {
			if(Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), programacion.getFechaInicio())) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_inicio_encontrada"));
			}
			if(Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), programacion.getFechaFin())) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_fin_encontrada"));
			}
			diasAcumuladosVacaciones += vacacionProgramacion.getNumeroDias();
			contadorSabados += vacacionProgramacion.getNumeroSabados();
			contadorDomingos += vacacionProgramacion.getNumeroDomingos();
		}
		int diasProgramacion = programacion.getNumeroDias();
		if(diasAcumuladosVacaciones == 0 && diasProgramacion < 7) {
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regulatoria.primera_mitad.error", new String[] {programacion.getPeriodo().getDescripcion()}));
		}
		if(diasAcumuladosVacaciones == 7 && diasProgramacion < 8) {
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regulatoria.primera_mitad.error", new String[] {programacion.getPeriodo().getDescripcion()}));
		}
		if(diasAcumuladosVacaciones == 8 && diasProgramacion < 7) {
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regulatoria.primera_mitad.error", new String[] {programacion.getPeriodo().getDescripcion()}));
		}
		double diasPendientePorRegistrar = Utilitario.calcularDiasPendientesPorRegistrar(programacion.getPeriodo());
		if(programacion.getNumeroDias() == diasPendientePorRegistrar ) {
			contadorSabados += Utilitario.obtenerCantidadSabados(programacion.getFechaInicio(), programacion.getFechaFin());
			if(contadorSabados != 4) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regularoria.cuatro_sabados.error", new String[] { programacion.getPeriodo().getDescripcion()}));
			}
			contadorDomingos += Utilitario.obtenerCantidadDomingos(programacion.getFechaInicio(), programacion.getFechaFin());
			if(contadorDomingos != 4) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regularoria.cuatro_domingos.error", new String[] { programacion.getPeriodo().getDescripcion()}));
			}
		}
		LOGGER.info("[END] validarTramoVacaciones");
	}

	@Override
	public void obtenerOrden(VacacionProgramacion programacion, String usuarioModifica) {
		LOGGER.info("[BEGIN] obtenerOrdenProgramacion");
		List<VacacionProgramacion> programaciones = vacacionProgramacionService.listarPorPeriodo(programacion.getPeriodo().getId());
		programaciones = programaciones.stream().filter(p -> p.getIdEstado() != EstadoVacacion.RECHAZADO.id).collect(Collectors.toList());
		if(programaciones.isEmpty()) {
			programacion.setOrden(1);
		} else {
			Comparator<VacacionProgramacion> odenPorFechas = new Comparator<VacacionProgramacion>() {
				@Override
				public int compare(VacacionProgramacion prog1, VacacionProgramacion prog2) {
					return prog1.getFechaInicio().compareTo(prog2.getFechaInicio());
				}
			};
			programaciones.add(programacion);
			programaciones.sort(odenPorFechas);
			for (int i = 0; i < programaciones.size(); i++) {
				programaciones.get(i).setCodigoEmpleado(programacion.getPeriodo().getEmpleado().getCodigo());
				programaciones.get(i).setOrden(i + 1);
				if(programaciones.get(i).getId() != null) {
					vacacionProgramacionService.actualizar(programaciones.get(i), usuarioModifica);
				} else {
					programacion = programaciones.get(i);
				}
			}
		}
		LOGGER.info("[END] obtenerOrdenProgramacion");
	}

	@Override
	public void actualizarPeriodo(Empleado empleado, String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarPeriodo");
		periodoVacacionService.actualizarPeriodos(empleado, usuarioOperacion);
		LOGGER.info("[END] actualizarPeriodo");
		
	}

	@Override
	public void actualizarPeriodo(Empleado empleado, long idPeriodo, String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarPeriodo {}", idPeriodo);
		periodoVacacionService.actualizarPeriodo(empleado, idPeriodo, usuarioOperacion);
		LOGGER.info("[END] actualizarPeriodo");
	}

	@Override
	public void validarPoliticaBolsa(VacacionProgramacion programacion) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
