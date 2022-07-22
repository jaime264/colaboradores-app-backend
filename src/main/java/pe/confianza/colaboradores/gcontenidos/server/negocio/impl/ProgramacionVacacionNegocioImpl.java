package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramacionAprobador;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestResumenVacaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseResumenPeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseResumenVacacion;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadNegocio;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.UnidadNegocioService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.MesesAnio;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ProgramacionVacacionNegocioImpl implements ProgramacionVacacionNegocio {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProgramacionVacacionNegocioImpl.class);

	@Autowired
	private CargaParametros parametrosConstants;

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private PeriodoVacacionService periodoVacacionService;

	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;

	@Autowired
	private VacacionMetaService vacacionMetaService;

	@Autowired
	private UnidadNegocioService unidadNegocioService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;

	@Transactional
	@Override
	public List<ResponseProgramacionVacacion> registro(RequestProgramacionVacacion programacion) {
		LOGGER.info("[BEGIN] registro: {} - {} - {}", new Object[] { programacion.getUsuarioBT(), programacion.getFechaInicio(), programacion.getFechaFin() });
		try {
			validarFechaRegistro(programacion.getFechaInicio());
			Empleado empleado = empleadoService.buscarPorUsuarioBT(programacion.getUsuarioBT().trim());
			if (empleado == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", programacion.getUsuarioBT()));
			validarEmpleado(empleado);
			String usuarioOperacion = programacion.getUsuarioOperacion().trim();
			VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(programacion);
			vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);

			validarEmpleadoNuevo(vacacionProgramacion, empleado);
			validarRangoFechas(vacacionProgramacion);

			// --
			List<VacacionProgramacion> programaciones = obtenerPeriodo(empleado, vacacionProgramacion);
			programaciones.forEach(prog -> {
				prog.setEstado(EstadoVacacion.REGISTRADO);
				prog.setCodigoEmpleado(empleado.getCodigo());
				validarPoliticasRegulatorias(prog);
				validarPoliticaBolsa(prog);
				obtenerOrden(prog, usuarioOperacion);
			});
			List<VacacionProgramacion> programacionesRegistradas = vacacionProgramacionService.registrar(programaciones, usuarioOperacion);
			List<Long> idsProgRegistradas = programacionesRegistradas.stream().map(prog -> prog.getId()).collect(Collectors.toList());
			List<Long> idsPeriodosModificados = programacionesRegistradas.stream().map(prog -> prog.getPeriodo().getId()).distinct().collect(Collectors.toList());
			idsPeriodosModificados.forEach(periodoId -> {
				actualizarPeriodo(empleado, periodoId, usuarioOperacion);
			});
			programacionesRegistradas.forEach(p -> {
				actualizarMeta(parametrosConstants.getMetaVacacionAnio(), p, false, usuarioOperacion);
			});

			programacionesRegistradas = new ArrayList<>();
			for (Long idProgramacion : idsProgRegistradas) {
				programacionesRegistradas.add(vacacionProgramacionService.buscarPorId(idProgramacion));
			}
			LOGGER.info("[END] registro");
			return programacionesRegistradas.stream().map(p -> {
				return VacacionProgramacionMapper.convert(p, parametrosConstants);
			}).collect(Collectors.toList());
		} catch (ModelNotFoundException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public VacacionProgramacion reprogramacionAprobador(RequestReprogramacionAprobador reqAprob) {

		RequestProgramacionVacacion reqProgamacionVacacion = new RequestProgramacionVacacion();
		List<ResponseProgramacionVacacion> listResProgramaciconVacacion = new ArrayList<ResponseProgramacionVacacion>();

		Optional<VacacionProgramacion> vacacionProgramacion = vacacionProgramacionDao
				.findById(reqAprob.getIdProgramacion());

		Empleado empleado = empleadoService
				.buscarPorCodigo(vacacionProgramacion.get().getPeriodo().getCodigoEmpleado());
		LocalDate ahora = LocalDate.now();

		if (vacacionProgramacion.isPresent()) {
			if (vacacionProgramacion.get().getNumeroReprogramaciones() == null
					|| vacacionProgramacion.get().getNumeroReprogramaciones() <= 3) {

				vacacionProgramacionService.eliminar(reqAprob.getIdProgramacion(), reqAprob.getUsuarioBt().trim());
				actualizarPeriodo(empleado, vacacionProgramacion.get().getPeriodo().getId(),
						reqAprob.getUsuarioBt().trim());
				actualizarMeta(ahora.getYear() + 1, vacacionProgramacion.get(), true, reqAprob.getUsuarioBt().trim());

				reqProgamacionVacacion.setFechaInicio(reqAprob.getFechaIni());
				reqProgamacionVacacion.setFechaFin(reqAprob.getFechaFin());
				reqProgamacionVacacion.setUsuarioBT(empleado.getUsuarioBT().trim());
				reqProgamacionVacacion.setUsuarioOperacion(empleado.getUsuarioBT().trim());

				listResProgramaciconVacacion = registro(reqProgamacionVacacion);

				Optional<VacacionProgramacion> vacacionPro = vacacionProgramacionDao
						.findById(listResProgramaciconVacacion.get(0).getId());

				vacacionPro.get()
						.setNumeroReprogramaciones(vacacionProgramacion.get().getNumeroReprogramaciones() == null ? 1
								: vacacionProgramacion.get().getNumeroReprogramaciones() + 1);
				vacacionPro.get().setUsuarioModifica(reqAprob.getUsuarioBt());
				vacacionProgramacionDao.save(vacacionPro.get());

				return vacacionPro.get();
			} else {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "Limite de reprogramaciones",
						reqAprob.getIdProgramacion().toString()));
			}
		}

		throw new AppException(Utilitario.obtenerMensaje(messageSource, "Programacion no existe",
				reqAprob.getIdProgramacion().toString()));

	}

	@Override
	public void cancelar(RequestCancelarProgramacionVacacion cancelacion) {
		LOGGER.info("[BEGIN] cancelar");
		try {
			Empleado empleado = empleadoService.buscarPorUsuarioBT(cancelacion.getUsuarioOperacion().trim());
			if (empleado == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe",
						new String[] { cancelacion.getUsuarioOperacion() }));
			LocalDate ahora = LocalDate.now();
			validarPeriodoRegistro(ahora);
			VacacionProgramacion programacion = vacacionProgramacionService.buscarPorId(cancelacion.getIdProgramacion());
			if (programacion.getPeriodo().getEmpleado().getId() != empleado.getId())
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.cancelar.sin_permiso", cancelacion.getUsuarioOperacion()));
			if (programacion.getIdEstado() != EstadoVacacion.REGISTRADO.id)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.cancelar.estado_error", parametrosConstants.getEstadoProgramacionDescripcion(EstadoVacacion.REGISTRADO.id)));
			long idPeriodo = programacion.getPeriodo().getId();
			vacacionProgramacionService.eliminar(cancelacion.getIdProgramacion(), cancelacion.getUsuarioOperacion());
			actualizarPeriodo(empleado, idPeriodo, cancelacion.getUsuarioOperacion());
			actualizarMeta(parametrosConstants.getMetaVacacionAnio(), programacion, true, cancelacion.getUsuarioOperacion().trim());
			LOGGER.info("[END] cancelar");
		} catch (ModelNotFoundException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public List<ResponseProgramacionVacacion> generar(RequestGenerarProgramacionVacacion request) {
		LOGGER.info("[BEGIN] generar");
		try {
			Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioOperacion().trim());
			if (empleado == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", request.getUsuarioOperacion()));
			LocalDate ahora = LocalDate.now();
			validarPeriodoRegistro(ahora);
			List<VacacionProgramacion> programacionesGenerar = vacacionProgramacionService.buscarPorUsuarioBTYEstado(request.getUsuarioOperacion().trim(), EstadoVacacion.REGISTRADO);
			List<ResponseProgramacionVacacion> response = new ArrayList<>();
			for (VacacionProgramacion programacion : programacionesGenerar) {
				programacion.setEstado(EstadoVacacion.GENERADO);
				programacion = vacacionProgramacionService.actualizar(programacion, request.getUsuarioOperacion());
				response.add(VacacionProgramacionMapper.convert(programacion, parametrosConstants));
			}
			actualizarPeriodo(empleado, request.getUsuarioOperacion());
			//consolidarMetaAnual(empleado, parametrosConstants.getMetaVacacionAnio(), request.getUsuarioOperacion());
			// actualizarMeta(empleado, ahora.getYear() + 1, 0,
			// request.getUsuarioOperacion());
			LOGGER.info("[END] generar");
			return response;
		} catch (ModelNotFoundException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public List<ResponseProgramacionVacacion> consultar(RequestListarVacacionProgramacion request) {
		LOGGER.info("[BEGIN] consultar");
		try {
			Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioBT());
			if (empleado == null)
				throw new ModelNotFoundException("No existe el usuario " + request.getUsuarioBT());
			List<VacacionProgramacion> lstProgramacion = new ArrayList<>();
			EstadoVacacion estadoSeleccionado = null;
			if (!StringUtils.isEmpty(request.getIdEstado()))
				estadoSeleccionado = EstadoVacacion.getEstado(request.getIdEstado());

			if (estadoSeleccionado == null && StringUtils.isEmpty(request.getPeriodo()))
				lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBT(request.getUsuarioBT().trim());
			if (estadoSeleccionado != null && !StringUtils.isEmpty(request.getPeriodo()))
				lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBTYPeriodoYEstado(
						request.getUsuarioBT().trim(), request.getPeriodo().trim(), estadoSeleccionado);
			if (estadoSeleccionado == null && !StringUtils.isEmpty(request.getPeriodo()))
				lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBTYPeriodo(request.getUsuarioBT().trim(),
						request.getPeriodo().trim());
			if (estadoSeleccionado != null && StringUtils.isEmpty(request.getPeriodo()))
				lstProgramacion = vacacionProgramacionService.buscarPorUsuarioBTYEstado(request.getUsuarioBT().trim(),
						estadoSeleccionado);
			lstProgramacion.sort(Comparator.comparing(VacacionProgramacion::getFechaInicio));
			LOGGER.info("[END] consultar");
			return lstProgramacion.stream().map(p -> {
				return VacacionProgramacionMapper.convert(p, parametrosConstants);
			}).collect(Collectors.toList());
		} catch (ModelNotFoundException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public ResponseResumenVacacion consultar(RequestResumenVacaciones request) {
		LOGGER.info("[BEGIN] consultar");
		try {
			Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioBT());
			if (empleado == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", request.getUsuarioBT()));

			LocalDate fechaConsulta = LocalDate.now();
			VacacionMeta meta = vacacionMetaService.obtenerVacacionPorAnio(parametrosConstants.getMetaVacacionAnio(), empleado.getId());
			if (meta == null)
				throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", request.getUsuarioBT()));
			LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta();
			ResponseResumenVacacion response = new ResponseResumenVacacion();
			response.setFechaConsulta(fechaConsulta);
			response.setFechaCorte(fechaCorte);
			response.setNombres(empleado.getNombres());
			response.setApellidoPaterno(empleado.getApellidoPaterno());
			response.setApellidoMaterno(empleado.getApellidoMaterno());
			response.setFechaInicioLabores(empleado.getFechaIngreso());
			response.setFechaFinLabores(empleado.getFechaFinContrato());
			response.setCargo(empleado.getPuesto().getDescripcion().trim());
			response.setMeta(meta.getMeta() < 1.0 ? 0.0 : Utilitario.redondearMeta(meta.getMeta()));
			response.setFechaInicioRegistroProgramacion(parametrosConstants.getFechaInicioRegistroProgramacion());
			response.setFechaFinRegistroProgramacion(parametrosConstants.getFechaFinRegistroProgramacion());

			response.setAnio(meta.getAnio());

			ResponseResumenPeriodoVacacion periodoTrunco = null;
			ResponseResumenPeriodoVacacion periodoVencido = null;

			if (meta.getPeriodoVencido() != null) {
				PeriodoVacacion periodo = meta.getPeriodoVencido();
				VacacionProgramacion ultimaProgramacion = vacacionProgramacionService.obtenerUltimaProgramacion(periodo.getId());
				periodoVencido = new ResponseResumenPeriodoVacacion();
				periodoVencido.setDescripcion(periodo.getDescripcion());
				periodoVencido.setDias(Utilitario.redondearMeta(meta.getDiasVencidos()));
				periodoVencido.setFechaLimite(periodo.getFechaLimiteIndemnizacion());
				periodoVencido.setUltimoTramo(ultimaProgramacion == null ? 0 : ultimaProgramacion.getOrden());
			}

			if (meta.getPeriodoTrunco() != null) {
				PeriodoVacacion periodo = meta.getPeriodoTrunco();
				VacacionProgramacion ultimaProgramacion = vacacionProgramacionService
						.obtenerUltimaProgramacion(periodo.getId());
				periodoTrunco = new ResponseResumenPeriodoVacacion();
				periodoTrunco.setDescripcion(periodo.getDescripcion());
				periodoTrunco.setDias(Utilitario.redondearMeta(meta.getDiasTruncos()));
				periodoTrunco.setFechaLimite(periodo.getFechaLimiteIndemnizacion());
				periodoTrunco.setUltimoTramo(ultimaProgramacion == null ? 0 : ultimaProgramacion.getOrden());
			}

			response.setPeriodoTrunco(periodoTrunco);
			response.setPeriodoVencido(periodoVencido);

			if (response.getMeta() == 0.0) {
				response.setSolicitar(true);
			} else {
				response.setSolicitar(false);
			}
			LOGGER.info("[BEGIN] consultar");
			return response;
		} catch (ModelNotFoundException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("[ERROR] registro", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public void validarFechaRegistro(LocalDate fechaInicioVacacion) {
		LOGGER.info("[BEGIN] validarFechaRegistro");
		LocalDate ahora = LocalDate.now();
		if (fechaInicioVacacion.isBefore(ahora))
			throw new AppException(
					Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_inicio_error"));
		validarPeriodoRegistro(ahora);
		LOGGER.info("[END] validarFechaRegistro");

	}

	@Override
	public void validarPeriodoRegistro(LocalDate fechaEvaluar) {
		LOGGER.info("[BEGIN] validarPeriodoRegistro");
		LocalDate fechaInicioRegistroProgramacion = parametrosConstants.getFechaInicioRegistroProgramacion();
		LocalDate fechaFinRegistroProgramacion = parametrosConstants.getFechaFinRegistroProgramacion();
		if (fechaEvaluar.isBefore(fechaInicioRegistroProgramacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource,
					"vacaciones.validacion.inicio_programacion_antes", fechaInicioRegistroProgramacion + ""));
		if (fechaEvaluar.isAfter(fechaFinRegistroProgramacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource,
					"vacaciones.validacion.fin_programacion_despues", fechaFinRegistroProgramacion + ""));
		LOGGER.info("[END] validarPeriodoRegistro");
	}

	@Override
	public void validarEmpleado(Empleado empleado) {
		LOGGER.info("[BEGIN] validarEmpleado");
		if (!EstadoRegistro.ACTIVO.valor.equals(empleado.getEstadoRegistro()))
			throw new AppException(
					Utilitario.obtenerMensaje(messageSource, "emplado.inactivo", empleado.getUsuarioBT()));
		if (empleado.isBloqueoVacaciones())
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.bloqueo_vacaciones",
					empleado.getUsuarioBT()));
		LOGGER.info("[END] validarEmpleado");
	}

	@Override
	public void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado) {
		LOGGER.info("[BEGIN] validarEmpleadoNuevo");
		LocalDate fechaParaPedirVacacion = empleado.getFechaIngreso().plusMonths(parametrosConstants.getMesesAntiguedadVacacionesAdelantadas());
		LOGGER.info("Empleado -> fecha ingreso: {}", new Object[] { fechaParaPedirVacacion });
		if (programacion.getFechaInicio().isBefore(fechaParaPedirVacacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.empleado_nuevo"));
		LOGGER.info("[END] validarEmpleadoNuevo");
	}

	@Override
	public void validarRangoFechas(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarRangoFechas");
		if (programacion.getFechaInicio().isAfter(programacion.getFechaFin()))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.rango_error"));
		LOGGER.info("[END] validarRangoFechas");
	}

	@Override
	public List<VacacionProgramacion> obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] obtenerPeriodo");
		List<VacacionProgramacion> programaciones = new ArrayList<>();
		LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta();
		VacacionMeta meta = vacacionMetaService.obtenerVacacionPorAnio(fechaCorte.getYear() + 1, empleado.getId());
		int diasVencidos = (int) Utilitario.redondearMeta(meta.getDiasVencidos());
		int diasTruncos = (int) Utilitario.redondearMeta(meta.getDiasTruncos());

		int diasVacaciones = diasVencidos + diasTruncos;
		int diasPorRegistrar = programacion.getNumeroDias();
		if(meta.getMeta() <= diasVacaciones) {
			if (diasPorRegistrar > diasVacaciones) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.no_dias_gozar"));
			}
		}
		
		if (diasVencidos > 0) {
			VacacionProgramacion programacionParteI = VacacionProgramacionMapper.clone(programacion);
			if (diasVencidos >= diasPorRegistrar) {
				programacionParteI.setPeriodo(meta.getPeriodoVencido());
				programacionParteI.setNumeroPeriodo((long) meta.getPeriodoVencido().getNumero());
			} else {
				programacionParteI.setFechaFin(Utilitario.agregarDias(programacion.getFechaInicio(), diasVencidos));
				programacionParteI.calcularDias();
				programacionParteI.setPeriodo(meta.getPeriodoVencido());
				programacionParteI.setNumeroPeriodo((long) meta.getPeriodoVencido().getNumero());
			}
			if (!meta.getPeriodoVencido().programacionDentroPeriodoGoce(programacionParteI))
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.validacion.fuera_limite_goce", meta.getPeriodoVencido().getDescripcion()));
			LOGGER.info("programacionParteI: " + programacionParteI.toString());
			programaciones.add(programacionParteI);
		}

		diasVacaciones = diasVacaciones - programaciones.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum();
		diasPorRegistrar = diasPorRegistrar	- programaciones.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum();

		if (diasPorRegistrar > 0) {
			VacacionProgramacion programacionParteII = VacacionProgramacionMapper.clone(programacion);
			if(meta.getMeta() <= diasVacaciones) {
				if (diasTruncos > 0) {
					if (diasTruncos >= diasPorRegistrar) {
						programacionParteII.setFechaInicio(Utilitario.quitarDias(programacion.getFechaFin(), diasPorRegistrar));
						programacionParteII.calcularDias();
						programacionParteII.setPeriodo(meta.getPeriodoTrunco());
						programacionParteII.setNumeroPeriodo((long) meta.getPeriodoTrunco().getNumero());
					} else {
						throw new AppException(
								Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.no_dias_gozar"));
					}
					if (!meta.getPeriodoTrunco().programacionDentroPeriodoGoce(programacionParteII))
						throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fuera_limite_goce", meta.getPeriodoVencido().getDescripcion()));
					LOGGER.info("programacionParteII: " + programacionParteII.toString());
					programaciones.add(programacionParteII);
				}
			} else {
				
				programacionParteII.setFechaInicio(Utilitario.quitarDias(programacion.getFechaFin(), diasPorRegistrar));
				programacionParteII.calcularDias();
				programacionParteII.setPeriodo(meta.getPeriodoTrunco());
				programacionParteII.setNumeroPeriodo((long) meta.getPeriodoTrunco().getNumero());
				programacionParteII.setVacacionesAdelantadas(true);
				LOGGER.info("programacionParteII: " + programacionParteII.toString());
				programaciones.add(programacionParteII);
			}
			
		}
		LOGGER.info("[END] obtenerPeriodo");
		return programaciones;
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
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), programacion.getFechaInicio())) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_inicio_encontrada"));
			}
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(),	programacion.getFechaFin())) {
				throw new AppException(	Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_fin_encontrada"));
			}
			diasAcumuladosVacaciones += vacacionProgramacion.getNumeroDias();
			contadorSabados += vacacionProgramacion.getNumeroSabados();
			contadorDomingos += vacacionProgramacion.getNumeroDomingos();
		}
		int diasProgramacion = programacion.getNumeroDias();
		String mensajeError = "";
		if (diasAcumuladosVacaciones == 0 && diasProgramacion < 7) {
			mensajeError = Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.regulatoria.primera_mitad.error",	 programacion.getPeriodo().getDescripcion());
		}
		if (diasAcumuladosVacaciones == 0 && diasProgramacion > 8 && diasProgramacion < 15) {
			mensajeError = Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regulatoria.primera_mitad.error",	programacion.getPeriodo().getDescripcion());
		}
		if (diasAcumuladosVacaciones == 0 && diasProgramacion > 15) {
			mensajeError = Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.regulatoria.primera_mitad.error",	 programacion.getPeriodo().getDescripcion());
		}
		if (diasAcumuladosVacaciones == 7 && diasProgramacion != 8) {
			mensajeError = Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.regulatoria.primera_mitad.error",	programacion.getPeriodo().getDescripcion());
		}
		if (diasAcumuladosVacaciones == 8 && diasProgramacion != 7) {
			mensajeError = Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.regulatoria.primera_mitad.error", programacion.getPeriodo().getDescripcion());
		}
		if(diasAcumuladosVacaciones < 15) {
			if((diasAcumuladosVacaciones + diasProgramacion) <= 15 && diasProgramacion < 7)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.bloque_error"));
			if((diasAcumuladosVacaciones + diasProgramacion) > 15 && diasProgramacion < 7)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.bloque_error"));
		}
		
		/*if(!mensajeError.equals(""))
			throw new AppException(mensajeError);*/
		
		double diasPendientePorRegistrar = Utilitario.calcularDiasPendientesPorRegistrarEnRegistroProgramacion(parametrosConstants.getTotalVacacionesAnio(), programacion.getPeriodo());
		if (programacion.getNumeroDias() <= diasPendientePorRegistrar) {
			contadorSabados += Utilitario.obtenerCantidadSabados(programacion.getFechaInicio(), programacion.getFechaFin());
			contadorDomingos += Utilitario.obtenerCantidadDomingos(programacion.getFechaInicio(), programacion.getFechaFin());
			double diasPendientesPorRegistrarFuturos = diasPendientePorRegistrar - programacion.getNumeroDias();
			int sabadosPendientes = contadorSabados >= 4 ? 0 : ( 4 - contadorSabados);
			int domingoPendientes = contadorDomingos >= 4 ? 0 : ( 4 - contadorDomingos);
			if(diasPendientesPorRegistrarFuturos < (sabadosPendientes + domingoPendientes)) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regularoria.cuatro_sabados.error",  programacion.getPeriodo().getDescripcion()));
			}
			/*if (contadorSabados < 4) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regularoria.cuatro_sabados.error", new String[] { programacion.getPeriodo().getDescripcion() }));
			}
			
			if (contadorDomingos < 4) {
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.regularoria.cuatro_domingos.error", new String[] { programacion.getPeriodo().getDescripcion() }));
			}*/
		}

		LOGGER.info("[END] validarTramoVacaciones");
	}
	
	@Override
	public void validarPoliticasRegulatorias(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarPoliticasRegulatorias");
		LocalDate fechaCorte = parametrosConstants.getFechaCorteMeta();
		VacacionMeta meta = vacacionMetaService.obtenerVacacionPorAnio(fechaCorte.getYear() + 1, programacion.getPeriodo().getEmpleado().getId());
		List<VacacionProgramacion> programacionesPeriodoVencido = new ArrayList<>();
		List<VacacionProgramacion> programacionesPeriodoTrunco = new ArrayList<>();
		if(meta.getPeriodoVencido() != null)
			programacionesPeriodoVencido = vacacionProgramacionService.listarPorPeriodo(meta.getPeriodoVencido().getId());
		if(meta.getPeriodoTrunco() != null)
			programacionesPeriodoTrunco = vacacionProgramacionService.listarPorPeriodo(meta.getPeriodoTrunco().getId());
		List<VacacionProgramacion> programacionesTodas = new ArrayList<>();
		programacionesTodas.addAll(programacionesPeriodoVencido);
		programacionesTodas.addAll(programacionesPeriodoTrunco);
		programacionesTodas = programacionesTodas.stream().filter(p -> p.getIdEstado() != EstadoVacacion.RECHAZADO.id).collect(Collectors.toList());
		//Verificar que fechas no se crucen
		for (VacacionProgramacion vacacionProgramacion : programacionesTodas) {
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), programacion.getFechaInicio()))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_inicio_encontrada"));
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(),	programacion.getFechaFin()))
				throw new AppException(	Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_fin_encontrada"));			
		}
		//Ordenar tramos
		List<VacacionProgramacion> programaciones = vacacionProgramacionService.listarPorPeriodo(programacion.getPeriodo().getId());
		programaciones = programaciones.stream().filter(p -> p.getIdEstado() != EstadoVacacion.RECHAZADO.id).collect(Collectors.toList());
		programaciones.add(programacion);
		int totalDiasProgramadosPeriodo = programaciones.stream().mapToInt(VacacionProgramacion::getNumeroDias).sum();
		if(totalDiasProgramadosPeriodo > parametrosConstants.getTotalVacacionesAnio())
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.periodo_dias_limite", programacion.getPeriodo().getDescripcion()));
		Comparator<VacacionProgramacion> odenPorFechas = new Comparator<VacacionProgramacion>() {
			@Override
			public int compare(VacacionProgramacion prog1, VacacionProgramacion prog2) {
				return prog1.getFechaInicio().compareTo(prog2.getFechaInicio());
			}
		};
		programaciones.sort(odenPorFechas);
		for (int i = 0; i < programaciones.size(); i++) {
			programaciones.get(i).setOrden(i + 1);
		}
		//Validar tramos
		int contadorSabados = 0;
		int contadorDomingos = 0;
		int diasAcumulados = 0;
		for (VacacionProgramacion vacacionProgramacion : programaciones) {
			int diasProgramacion = vacacionProgramacion.getNumeroDias();
			int mitadVacacionesAnio = parametrosConstants.getMitadTotalVacacionesAnio();
			int diasMinimosTramosPrimeraMitad = parametrosConstants.getDiasMinimoTramosAntesPrimeraMitad();
			if(diasAcumulados < mitadVacacionesAnio) {
				if((diasAcumulados + diasProgramacion) <= mitadVacacionesAnio && diasProgramacion < diasMinimosTramosPrimeraMitad)
					throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.bloque_error"));
				if((diasAcumulados + diasProgramacion) > mitadVacacionesAnio && diasProgramacion < diasMinimosTramosPrimeraMitad)
					throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.bloque_error"));
			}
			diasAcumulados += vacacionProgramacion.getNumeroDias();
			contadorSabados += vacacionProgramacion.getNumeroSabados();
			contadorDomingos += vacacionProgramacion.getNumeroDomingos();
		}
		//Validar cantidad sabados y domingos
		double diasPendientePorRegistrar = Utilitario.calcularDiasPendientesPorRegistrarEnRegistroProgramacion(parametrosConstants.getTotalVacacionesAnio(), programacion.getPeriodo());
		if (programacion.getNumeroDias() <= diasPendientePorRegistrar) {
			double diasPendientesPorRegistrarFuturos = diasPendientePorRegistrar - programacion.getNumeroDias();
			int sabadosMinimoPorPeriodo = parametrosConstants.getSabadosMinPorPeriodo();
			int domingoMinimoPorPeriodo = parametrosConstants.getDomingosMinPorPeriodo();
			int sabadosPendientes = contadorSabados >= sabadosMinimoPorPeriodo ? 0 : ( sabadosMinimoPorPeriodo - contadorSabados);
			int domingoPendientes = contadorDomingos >= domingoMinimoPorPeriodo ? 0 : ( domingoMinimoPorPeriodo - contadorDomingos);
			if(diasPendientesPorRegistrarFuturos < (sabadosPendientes + domingoPendientes))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regularoria.cuatro_sabados.error", programacion.getPeriodo().getDescripcion()));
		}
		LOGGER.info("[END] validarPoliticasRegulatorias");
	}

	@Override
	public void obtenerOrden(VacacionProgramacion programacion, String usuarioModifica) {
		LOGGER.info("[BEGIN] obtenerOrdenProgramacion");
		List<VacacionProgramacion> programaciones = vacacionProgramacionService	.listarPorPeriodo(programacion.getPeriodo().getId());
		programaciones = programaciones.stream().filter(p -> p.getIdEstado() != EstadoVacacion.RECHAZADO.id).collect(Collectors.toList());
		if (programaciones.isEmpty()) {
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
				if (programaciones.get(i).getId() != null) {
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
		periodoVacacionService.consolidarResumenDias(idPeriodo, usuarioOperacion);
		periodoVacacionService.actualizarPeriodo(empleado, idPeriodo, usuarioOperacion);
		LOGGER.info("[END] actualizarPeriodo");
	}

	@Override
	public void actualizarMeta(int anio, VacacionProgramacion programacion, boolean cancelarProgramcion,
			String usuarioOperacion) {
		LOGGER.info("[BEGIN] actualizarMeta {} - {}",
				new Object[] { programacion.getPeriodo().getEmpleado().getUsuarioBT(), anio });
		vacacionMetaService.actualizarMeta(anio, programacion, cancelarProgramcion, usuarioOperacion);
		LOGGER.info("[END] actualizarMeta");
	}

	@Override
	public void consolidarMetaAnual(Empleado empleado, int anio, String usuarioOperacion) {
		LOGGER.info("[BEGIN] consolidarMetaAnual {} - {}", new Object[] { empleado.getUsuarioBT(), anio });
		vacacionMetaService.consolidarMetaAnual(empleado, anio, usuarioOperacion);
		LOGGER.info("[END] consolidarMetaAnual");

	}

	@Override
	public void validarPoliticaBolsa(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarPoliticaBolsa");
		Empleado empleado = programacion.getPeriodo().getEmpleado();
		if (empleado.getPuesto().getClasificacion().equalsIgnoreCase("CO"))
			validarPoliticaBolsaComercial(programacion);
		if (empleado.getPuesto().getClasificacion().equalsIgnoreCase("ST")) {
			validarPoliticaBolsaOperaciones(programacion);
			validarPoliticaBolsaRecuperaciones(programacion);
		}
		LOGGER.info("[END] validarPoliticaBolsa");

	}

	@Override
	public void validarPoliticaBolsaOperaciones(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarPoliticaBolsaOperaciones");
		Empleado empleado = programacion.getPeriodo().getEmpleado();
		String puesto = empleado.getPuesto().getDescripcion().trim();
		int limite = 1;
		if (puesto.contains(Constantes.ASESOR_SERVICIO) || puesto.contains(Constantes.ASESOR_PLATAFORMA) || puesto.contains(Constantes.SUPERVISOR_OFICINA)) {
			long cantidadProgramacionesAsesorServicio = vacacionProgramacionService.contarProgramacionPorEmpleadoAgencia(
					empleado.getId(), Constantes.ASESOR_SERVICIO,
					programacion.getFechaInicio(), programacion.getFechaFin(), null);
			long cantidadProgramacionesAsesorPlataforma = vacacionProgramacionService.contarProgramacionPorEmpleadoAgencia(
					empleado.getId(), Constantes.ASESOR_PLATAFORMA,
					programacion.getFechaInicio(), programacion.getFechaFin(), null);
			long cantidadProgramacionesSupervidorOficina = vacacionProgramacionService.contarProgramacionPorEmpleadoAgencia(
					empleado.getId(), Constantes.SUPERVISOR_OFICINA,
					programacion.getFechaInicio(), programacion.getFechaFin(), null);
			long cantidadProgramaciones = cantidadProgramacionesAsesorServicio + cantidadProgramacionesAsesorPlataforma	+ cantidadProgramacionesSupervidorOficina;
			cantidadProgramaciones++;
			LOGGER.info("{} cantidadProgramaciones {} - limite {}", "OPERACIONES", cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.operaciones.agencia.limite_error", limite));
			long cantidadEmpeadosRedOperaciones = empleadoService.obtenerCantidadEmpleadosRedOperaciones();
			double limiteRedOperaciones = cantidadEmpeadosRedOperaciones * 0.12;
			long cantidadProgramacionesRedComercial = vacacionProgramacionService.contarProgramacionPorEmpleadoRedOperaciones(empleado.getId(), 
					programacion.getFechaInicio(), programacion.getFechaFin(), null);
			cantidadProgramacionesRedComercial++;
			if(cantidadProgramacionesRedComercial > limiteRedOperaciones)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.bolsa.operaciones.limite_red", 12));
			
		}
		/*Agencia agencia = empleado.getAgencia();
		if (agencia.getDescripcion().contains("EOB")) { // AGENCIA TAMBO - TAMBO_PLUS

		}*/
		LOGGER.info("[END] validarPoliticaBolsaOperaciones");
	}

	@Override
	public void validarPoliticaBolsaComercial(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarPoliticaBolsaComercial");
		Empleado empleado = programacion.getPeriodo().getEmpleado();
		String puesto = empleado.getPuesto().getDescripcion().trim();
		if (puesto.contains(Constantes.ASESOR_NEGOCIO)) {
			if (empleado.getCodigoUnidadNegocio() == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.sin_unidad_negocio", empleado.getUsuarioBT()));
			UnidadNegocio unidadNegocio = unidadNegocioService.obtenerUnidadNegocioPorCodigo(empleado.getCodigoUnidadNegocio());
			if (unidadNegocio == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.validacion.unidad_negocio_error", empleado.getCodigoUnidadNegocio() + ""));
			int totalEmpleados = empleadoService.obtenerCantidadEmpleadosPorPuestoYUnidadNegocio(empleado.getCodigoUnidadNegocio(), Constantes.ASESOR_NEGOCIO);
			double limite = totalEmpleados * 0.12;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorUnidadNegocioEmpleado(empleado.getId(), Constantes.ASESOR_NEGOCIO, programacion.getFechaInicio(), programacion.getFechaFin(), null);
			cantidadProgramaciones++;
			LOGGER.info(" {} cantidadProgramaciones {} - limite {}", Constantes.ASESOR_NEGOCIO, cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.asesor_negocio_individual.limite_error", 12 + ""));
		}
		if (puesto.contains(Constantes.ASESOR_NEGOCIO_GRUPAL)) {
			if (programacion.getFechaInicio().getMonthValue() == 12 || programacion.getFechaFin().getMonthValue() == 12)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.asesor_negocio_grupal.mes_error", MesesAnio.buscarPorValor(12).descripcion));
		}
		if (puesto.contains(Constantes.ADMINISTRADOR_NEGOCIO)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorCorredorEmpleadoPuesto(
					empleado.getId(), Constantes.ADMINISTRADOR_NEGOCIO, programacion.getFechaInicio(),
					programacion.getFechaFin(), null);
			cantidadProgramaciones++;
			LOGGER.info("{} cantidadProgramaciones {} - limite {}", Constantes.ADMINISTRADOR_NEGOCIO, cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.administrador_negocio.limite_error", limite));
		}
		if (puesto.contains(Constantes.GERENTE_CORREDOR)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorTerritorioEmpleadoPuesto(
					empleado.getId(), Constantes.GERENTE_CORREDOR, programacion.getFechaInicio(),
					programacion.getFechaFin(), null);
			cantidadProgramaciones++;
			LOGGER.info("{} cantidadProgramaciones {} - limite {}", Constantes.GERENTE_CORREDOR, cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.gerente_corredor.limite_error", limite));
		}
		LOGGER.info("[END] validarPoliticaBolsaComercial");
	}

	@Override
	public void validarPoliticaBolsaRecuperaciones(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarPoliticaBolsaRecuperaciones");
		Empleado empleado = programacion.getPeriodo().getEmpleado();
		String puesto = empleado.getPuesto().getDescripcion().trim();
		if (puesto.contains(Constantes.ANALISTA_COBRANZA)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorCorredorEmpleadoPuesto(
					empleado.getId(), Constantes.ANALISTA_COBRANZA, programacion.getFechaInicio(),
					programacion.getFechaFin(), null);
			cantidadProgramaciones++;
			LOGGER.info("{} cantidadProgramaciones {} - limite {}", Constantes.ANALISTA_COBRANZA, cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.recuperaciones.analista_cobranza.limite_error", limite));
		}
		if (puesto.contains(Constantes.ANALISTA_RECUPERACIONES)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorEmpleadoPuesto(
					empleado.getId(), Constantes.ANALISTA_RECUPERACIONES, programacion.getFechaInicio(),
					programacion.getFechaFin(), null);
			cantidadProgramaciones++;
			LOGGER.info("{} cantidadProgramaciones {} - limite {}", Constantes.ANALISTA_RECUPERACIONES, cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.recuperaciones.analista_recuperaciones.limite_error", limite));
		}
		if (puesto.contains(Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorEmpleadoPuesto(
					empleado.getId(), Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA, programacion.getFechaInicio(),
					programacion.getFechaFin(), null);
			cantidadProgramaciones++;
			LOGGER.info("{} cantidadProgramaciones {} - limite {}", Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA, cantidadProgramaciones, limite);
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.recuperaciones.responsable_dpto_cobranza.limite_error", limite));
		}
		LOGGER.info("[END] validarPoliticaBolsaRecuperaciones");
	}

	@Override
	public void actualizarMeta(long idMeta, double nuevaMeta, String usuarioModifica) {
		LOGGER.info("[BEGIN] actualizarMeta");
		Optional<VacacionMeta> optMeta = vacacionMetaService.obtenerMeta(idMeta);
		if(!optMeta.isPresent())
			throw new ModelNotFoundException(Utilitario.obtenerMensaje(messageSource, "app.error.objeto_no_encontrado"));
		VacacionMeta meta = optMeta.get();
		Empleado empleado = meta.getEmpleado();
		List<VacacionProgramacion> programacionesRegistradasPeriodoVencido = new ArrayList<>();
		List<VacacionProgramacion> programacionesRegistradasPeriodoTrunco = new ArrayList<>();
		long diasProgramados = 0;
		if(meta.getPeriodoVencido() != null) {
			PeriodoVacacion periodoVencido = meta.getPeriodoVencido();
			diasProgramados += periodoVencido.getDiasGeneradosGozar() + periodoVencido.getDiasAprobadosGozar();
			programacionesRegistradasPeriodoVencido = vacacionProgramacionService.listarPorPeriodo(periodoVencido.getId())
					.stream().filter(p -> p.getIdEstado() == EstadoVacacion.REGISTRADO.id)
					.collect(Collectors.toList());
			programacionesRegistradasPeriodoVencido = programacionesRegistradasPeriodoVencido == null ? new ArrayList<>() : programacionesRegistradasPeriodoVencido;
		}
		
		if(meta.getPeriodoTrunco() != null) {
			PeriodoVacacion periodoTrunco = meta.getPeriodoTrunco();
			diasProgramados += periodoTrunco.getDiasGeneradosGozar() + periodoTrunco.getDiasAprobadosGozar();
			programacionesRegistradasPeriodoTrunco = vacacionProgramacionService.listarPorPeriodo(periodoTrunco.getId())
					.stream().filter(p -> p.getIdEstado() == EstadoVacacion.REGISTRADO.id)
					.collect(Collectors.toList());
			programacionesRegistradasPeriodoTrunco = programacionesRegistradasPeriodoTrunco == null ? new ArrayList<>() : programacionesRegistradasPeriodoTrunco;
		}
		if(diasProgramados > 0)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.parametros.meta.empleado_generado"));
		
		for (VacacionProgramacion programacion : programacionesRegistradasPeriodoVencido) {
			vacacionProgramacionService.eliminar(programacion.getId(), usuarioModifica);
			actualizarPeriodo(empleado, usuarioModifica);
			actualizarMeta(parametrosConstants.getMetaVacacionAnio(), programacion, true, usuarioModifica);
		}
		
		for (VacacionProgramacion programacion : programacionesRegistradasPeriodoTrunco) {
			vacacionProgramacionService.eliminar(programacion.getId(), usuarioModifica);
			actualizarPeriodo(empleado, usuarioModifica);
			actualizarMeta(parametrosConstants.getMetaVacacionAnio(), programacion, true, usuarioModifica);
		}
		optMeta = vacacionMetaService.obtenerMeta(idMeta);
		meta = optMeta.get();
		
		meta.setMetaInicial(nuevaMeta);
		meta.setMeta(nuevaMeta);
		
		meta = vacacionMetaService.actualizarMeta(meta, usuarioModifica);
		
		LOGGER.info("[END] actualizarMeta");
	}

}
