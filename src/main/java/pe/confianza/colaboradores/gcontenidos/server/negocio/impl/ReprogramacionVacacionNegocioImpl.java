package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConsultaVacacionesReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramacionTramo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReprogramarVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacionReprogramar;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mapper.VacacionProgramacionMapper;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Agencia;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadNegocio;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionReprogramacionContador;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ReprogramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.UnidadNegocioService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionReprogramacionContadorService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.MesesAnio;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class ReprogramacionVacacionNegocioImpl implements ReprogramacionVacacionNegocio {

	private static Logger logger = LoggerFactory.getLogger(ReprogramacionVacacionNegocioImpl.class);
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private UnidadNegocioService unidadNegocioService;
	
	@Autowired
	private PeriodoVacacionService periodoVacacionService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CargaParametros parametrosConstants;
	
	@Autowired
	private VacacionMetaService vacacionMetaService;
	
	@Autowired
	private ProgramacionVacacionNegocio programacionVacacionNegocio;
	
	@Autowired
	private VacacionReprogramacionContadorService vacacionReprogramacionContadorService;
	
	@Override
	public List<ResponseProgramacionVacacionReprogramar> programacionAnual(RequestConsultaVacacionesReprogramar request) {
		logger.info("[BEGIN] programacionAnuales");
		try {
			List<ResponseProgramacionVacacionReprogramar> programaciones = vacacionProgramacionService.listarProgramacionesPorAnio(cargaParametros.getAnioPresente(), request.getUsuarioBT())
					.stream()
					.filter(p -> p.getIdEstado() == EstadoVacacion.APROBADO.id)
					.map(p -> {
						ResponseProgramacionVacacionReprogramar prog = VacacionProgramacionMapper.convertReprogramacion(p, cargaParametros);
						if(prog.getFechaFin().getMonthValue() == (LocalDate.now().getMonthValue() + 1) && prog.getIdEstado() == EstadoVacacion.APROBADO.id) {
							prog.setReprogramar(true);
						} else {
							prog.setReprogramar(false);
						}
						return prog;
					}).collect(Collectors.toList());
			logger.info("[END] programacionAnuales");
			return programaciones;
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
	public ResponseProgramacionVacacion vacacionesAdelantadas(RequestProgramacionVacacion request) {
		logger.info("[BEGIN] vacacionesAdelantadas");
		try {
			Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioBT().trim());
			if (empleado == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "empleado.no_existe", request.getUsuarioBT()));
			VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(request);
			vacacionProgramacion.setEstado(EstadoVacacion.GENERADO);
			vacacionProgramacion.setVacacionesAdelantadas(true);
			validarEmpleadoNuevo(vacacionProgramacion, empleado);
			if(vacacionProgramacion.getNumeroDias() > cargaParametros.getDiasMaximoVacacionesAdelantadas())
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.vacaciones_adelantadas.limite_error", cargaParametros.getDiasMaximoVacacionesAdelantadas() + ""));
			logger.info("[END] vacacionesAdelantadas");
			VacacionMeta meta = vacacionMetaService.obtenerVacacionPorAnio(parametrosConstants.getMetaVacacionAnio(), empleado.getId());
			vacacionProgramacion.setPeriodo(meta.getPeriodoTrunco());
			vacacionProgramacion.setNumeroPeriodo((long) meta.getPeriodoTrunco().getNumero());
			vacacionProgramacion.calcularDias();
			programacionVacacionNegocio.validarPoliticasRegulatorias(vacacionProgramacion);
			programacionVacacionNegocio.validarPoliticaBolsa(vacacionProgramacion);
			programacionVacacionNegocio.obtenerOrden(vacacionProgramacion, request.getUsuarioOperacion());
			vacacionProgramacion = vacacionProgramacionService.registrar(vacacionProgramacion, request.getUsuarioOperacion());
			actualizarPeriodo(empleado, vacacionProgramacion.getPeriodo().getId(), request.getUsuarioOperacion());
			vacacionProgramacion = vacacionProgramacionService.buscarPorId(vacacionProgramacion.getId());
			return VacacionProgramacionMapper.convert(vacacionProgramacion, parametrosConstants);
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

	@Transactional
	@Override
	public List<ResponseProgramacionVacacion> reprogramarTramo(RequestReprogramarVacacion request) {
		logger.info("[BEGIN] reprogramarTramo");
		try {
			VacacionProgramacion programacion = vacacionProgramacionService.buscarPorId(request.getIdProgramacion());
			if(programacion.getEstado().id != EstadoVacacion.APROBADO.id)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.estado_error", cargaParametros.getEstadoProgramacionDescripcion(EstadoVacacion.APROBADO.id)[0]));
			if(programacion.getFechaFin().getMonthValue() != (LocalDate.now().getMonthValue() + 1))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.mes_error"));
			String usuarioOperacion = request.getUsuarioOperacion().trim();
			programacion.calcularDias();
			validarCantidadReprogramaciones(programacion.getPeriodo().getEmpleado(), usuarioOperacion);
			validarPeriodoReprogramacion();
			validarPermisoReprogramar(programacion, request.getUsuarioOperacion());
			validarDiasReprogramados(programacion, request);
			
			List<VacacionProgramacion> programaciones = request.getTramos().stream().map(t -> {
				VacacionProgramacion prog = VacacionProgramacionMapper.convert(t, programacion);
				prog.setIdEstado(EstadoVacacion.GENERADO.id);
				prog.setPeriodo(programacion.getPeriodo());
				prog.setNumeroPeriodo((long)programacion.getPeriodo().getNumero());
				prog.setUsuarioCrea(request.getUsuarioOperacion());
				prog.setFechaCrea(LocalDateTime.now());
				prog.calcularDias();
				return prog;
			}).collect(Collectors.toList());
			programaciones.forEach(prog -> {
				validarPoliticasRegulatorias(prog, programacion);
				validarPoliticaBolsa(prog, programacion);
				obtenerOrden(prog, programacion, usuarioOperacion);
			});
			if(programacion.getFechaInicio().getYear() != programacion.getFechaFin().getYear()) {
				final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaInicio = LocalDate.parse("01/01" + programacion.getFechaFin().getYear(), formatter);
				VacacionProgramacion progNuevoAnio = new VacacionProgramacion();
				progNuevoAnio.setFechaInicio(fechaInicio);
				progNuevoAnio.setFechaFin(programacion.getFechaFin());
				progNuevoAnio.setIdEstado(EstadoVacacion.GENERADO.id);
				progNuevoAnio.setPeriodo(programacion.getPeriodo());
				progNuevoAnio.setNumeroPeriodo((long)programacion.getPeriodo().getNumero());
				progNuevoAnio.setUsuarioCrea(request.getUsuarioOperacion());
				progNuevoAnio.setFechaCrea(LocalDateTime.now());
				progNuevoAnio.calcularDias();
				progNuevoAnio.setNumeroReprogramaciones(0);
				progNuevoAnio.setIdProgramacionOriginal(programacion.getId());
				progNuevoAnio.setNumeroReprogramaciones(0);
				progNuevoAnio.setVacacionesAdelantadas(false);
				obtenerOrden(progNuevoAnio, programacion, usuarioOperacion);
				programaciones.add(progNuevoAnio);
			}
			programacion.setIdEstado(EstadoVacacion.REPROGRAMADO.id);
			vacacionProgramacionService.actualizar(programacion, usuarioOperacion);
			actualizarCantidadReprogramaciones(programacion.getPeriodo().getEmpleado(), usuarioOperacion);
			List<VacacionProgramacion> programacionesReprogramadas = vacacionProgramacionService.modificar(programaciones, usuarioOperacion);
			List<Long> idsProgRegistradas = programacionesReprogramadas.stream().map(prog -> prog.getId()).collect(Collectors.toList());
			List<Long> idsPeriodosModificados = programacionesReprogramadas.stream().map(prog -> prog.getPeriodo().getId()).distinct().collect(Collectors.toList());
			idsPeriodosModificados.forEach(periodoId -> {
				actualizarPeriodo(programacion.getPeriodo().getEmpleado(), periodoId, usuarioOperacion);
			});
			programacionesReprogramadas = new ArrayList<>();
			for (Long idProgramacion : idsProgRegistradas) {
				programacionesReprogramadas.add(vacacionProgramacionService.buscarPorId(idProgramacion));
			}
			logger.info("[END] reprogramarTramo");
			return programacionesReprogramadas.stream().map(p -> {
				return VacacionProgramacionMapper.convert(p, parametrosConstants);
			}).collect(Collectors.toList());
		} catch (ModelNotFoundException e) {
			logger.error("[ERROR] reprogramarTramo", e);
			throw new ModelNotFoundException(e.getMessage()); 
		} catch (AppException e) {
			logger.error("[ERROR] reprogramarTramo", e);
			throw new AppException(e.getMessage(), e); 
		} catch (Exception e) {
			logger.error("[ERROR] reprogramarTramo", e);
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"), e);
		}
	}

	@Override
	public void validarPeriodoReprogramacion() {
		logger.info("[BEGIN] validarPeriodoReprogramacion");
		LocalDate fechaActual = LocalDate.now();
		if(fechaActual.isBefore(cargaParametros.getFechaInicioReprogramacion()))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.fuera_fecha",  cargaParametros.DIA_INICIO_REPROGRAMACION, cargaParametros.DIA_FIN_REPROGRAMACION));
		if(fechaActual.isAfter(cargaParametros.getFechaFinReprogramacion()))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.fuera_fecha", cargaParametros.DIA_INICIO_REPROGRAMACION, cargaParametros.DIA_FIN_REPROGRAMACION));
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
		logger.info("[BEGIN] validarDiasReprogramados");
		if(programacion.getFechaInicio().getYear() != programacion.getFechaFin().getYear()) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate fechaFinAnio = LocalDate.parse("31/12" + programacion.getFechaInicio().getYear(), formatter);
			int diasProgramadosAnioActual = Utilitario.obtenerDiferenciaDias(programacion.getFechaInicio(), fechaFinAnio);
			int diasReprogramados = 0;
			for (RequestReprogramacionTramo tramo : request.getTramos()) {
				if(tramo.getFechaInicio().isAfter(tramo.getFechaFin()))
					throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.rango_error"));
				if(tramo.getFechaFin().getYear() != programacion.getFechaInicio().getYear())
					throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.error_anio"));
				if(!programacion.getPeriodo().programacionDentroPeriodoGoce(tramo.getFechaInicio(), tramo.getFechaFin()))
					throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.validacion.fuera_limite_goce", programacion.getPeriodo().getDescripcion()));
				diasReprogramados += Utilitario.obtenerDiferenciaDias(tramo.getFechaInicio(), tramo.getFechaFin());
			}
			if(diasProgramadosAnioActual != diasReprogramados)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.dias_error"));
		} else {
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
		}
		logger.info("[END] validarDiasReprogramados");
	}

	@Override
	public void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado) {
		logger.info("[BEGIN] validarEmpleadoNuevo");
		LocalDate fechaParaPedirVacacion = empleado.getFechaIngreso().plusMonths(cargaParametros.getMesesAntiguedadVacacionesAdelantadas());
		if (programacion.getFechaInicio().isBefore(fechaParaPedirVacacion))
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.empleado_nuevo"));
		logger.info("[END] validarEmpleadoNuevo");
	}

	@Override
	public void validarPoliticasRegulatorias(VacacionProgramacion nuevaProgramacion, VacacionProgramacion programacionOriginal) {
		logger.info("[BEGIN] validarPoliticasRegulatorias");
		List<VacacionProgramacion> programaciones = vacacionProgramacionService.listarPorPeriodo(programacionOriginal.getPeriodo().getId());
		programaciones = programaciones.stream().filter(p -> p.getIdEstado() != EstadoVacacion.RECHAZADO.id).collect(Collectors.toList());
		programaciones = programaciones.stream().filter(p -> p.getId() != programacionOriginal.getId()).collect(Collectors.toList());
		//Verificar que fechas no se crucen
		for (VacacionProgramacion vacacionProgramacion : programaciones) {
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), nuevaProgramacion.getFechaInicio()))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_inicio_encontrada"));
			if (Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(),	nuevaProgramacion.getFechaFin()))
				throw new AppException(	Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.fecha_fin_encontrada"));			
		}
		//Ordenar tramos
		programaciones.add(nuevaProgramacion);
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
		double diasPendientePorRegistrar = Utilitario.calcularDiasPendientesPorRegistrarEnRegistroProgramacion(cargaParametros.getTotalVacacionesAnio(), nuevaProgramacion.getPeriodo());
		if (nuevaProgramacion.getNumeroDias() <= diasPendientePorRegistrar) {
			double diasPendientesPorRegistrarFuturos = diasPendientePorRegistrar - nuevaProgramacion.getNumeroDias();
			int sabadosMinimoPorPeriodo = parametrosConstants.getSabadosMinPorPeriodo();
			int domingoMinimoPorPeriodo = parametrosConstants.getDomingosMinPorPeriodo();
			int sabadosPendientes = contadorSabados >= sabadosMinimoPorPeriodo ? 0 : ( sabadosMinimoPorPeriodo - contadorSabados);
			int domingoPendientes = contadorDomingos >= domingoMinimoPorPeriodo ? 0 : ( domingoMinimoPorPeriodo - contadorDomingos);
			if(diasPendientesPorRegistrarFuturos < (sabadosPendientes + domingoPendientes))
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.regularoria.cuatro_sabados.error", nuevaProgramacion.getPeriodo().getDescripcion()));
		}
		logger.info("[END] validarPoliticasRegulatorias");
	}

	@Override
	public void obtenerOrden(VacacionProgramacion nuevaProgramacion, VacacionProgramacion programacionOriginal, String usuarioModifica) {
		logger.info("[BEGIN] obtenerOrdenProgramacion");
		List<VacacionProgramacion> programaciones = vacacionProgramacionService	.listarPorPeriodo(programacionOriginal.getPeriodo().getId());
		programaciones = programaciones.stream().filter(p -> p.getIdEstado() != EstadoVacacion.RECHAZADO.id).collect(Collectors.toList());
		programaciones = programaciones.stream().filter(p -> p.getId() != programacionOriginal.getId()).collect(Collectors.toList());
		if (programaciones.isEmpty()) {
			nuevaProgramacion.setOrden(1);
		} else {
			Comparator<VacacionProgramacion> odenPorFechas = new Comparator<VacacionProgramacion>() {
				@Override
				public int compare(VacacionProgramacion prog1, VacacionProgramacion prog2) {
					return prog1.getFechaInicio().compareTo(prog2.getFechaInicio());
				}
			};
			programaciones.add(nuevaProgramacion);
			programaciones.sort(odenPorFechas);
			for (int i = 0; i < programaciones.size(); i++) {
				programaciones.get(i).setCodigoEmpleado(programacionOriginal.getPeriodo().getEmpleado().getCodigo());
				programaciones.get(i).setOrden(i + 1);
				if (programaciones.get(i).getId() != null) {
					vacacionProgramacionService.actualizar(programaciones.get(i), usuarioModifica);
				} else {
					nuevaProgramacion = programaciones.get(i);
				}
			}
		}
		logger.info("[END] obtenerOrdenProgramacion");
		
	}

	@Override
	public void validarPoliticaBolsa(VacacionProgramacion nuevaProgramacion, VacacionProgramacion programacionOriginal) {
		logger.info("[BEGIN] validarPoliticaBolsa");
		Empleado empleado = programacionOriginal.getPeriodo().getEmpleado();
		if (empleado.getPuesto().getClasificacion().equalsIgnoreCase("CO"))
			validarPoliticaBolsaComercial(nuevaProgramacion, programacionOriginal);
		if (empleado.getPuesto().getClasificacion().equalsIgnoreCase("ST")) {
			validarPoliticaBolsaOperaciones(nuevaProgramacion, programacionOriginal);
			validarPoliticaBolsaRecuperaciones(nuevaProgramacion, programacionOriginal);
		}
		logger.info("[BEGIN] validarPoliticaBolsa");
		
	}

	@Override
	public void validarPoliticaBolsaComercial(VacacionProgramacion nuevaProgramacion, VacacionProgramacion programacionOriginal) {
		logger.info("[BEGIN] validarPoliticaBolsaComercial");
		Empleado empleado = programacionOriginal.getPeriodo().getEmpleado();
		String puesto = empleado.getPuesto().getDescripcion().trim();
		if (puesto.contains(Constantes.ASESOR_NEGOCIO)) {
			if (empleado.getCodigoUnidadNegocio() == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.validacion.sin_unidad_negocio", empleado.getUsuarioBT()));
			UnidadNegocio unidadNegocio = unidadNegocioService.obtenerUnidadNegocioPorCodigo(empleado.getCodigoUnidadNegocio());
			if (unidadNegocio == null)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.validacion.unidad_negocio_error", empleado.getCodigoUnidadNegocio() + ""));
			int totalEmpleados = empleadoService.obtenerCantidadEmpleadosPorPuestoYUnidadNegocio(empleado.getCodigoUnidadNegocio(), Constantes.ASESOR_NEGOCIO);
			double limite = totalEmpleados * 0.12;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorUnidadNegocioEmpleado(empleado.getId(), Constantes.ASESOR_NEGOCIO, nuevaProgramacion.getFechaInicio(), nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.asesor_negocio_individual.limite_error", 12 + ""));
		}
		if (puesto.contains(Constantes.ASESOR_NEGOCIO_GRUPAL)) {
			if (nuevaProgramacion.getFechaInicio().getMonthValue() == 12 || nuevaProgramacion.getFechaFin().getMonthValue() == 12)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.asesor_negocio_grupal.mes_error", MesesAnio.buscarPorValor(12).descripcion));
		}
		if (puesto.contains(Constantes.ADMINISTRADOR_NEGOCIO)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorCorredorEmpleadoPuesto(
					empleado.getId(), Constantes.ADMINISTRADOR_NEGOCIO, nuevaProgramacion.getFechaInicio(),
					nuevaProgramacion.getFechaFin(), programacionOriginal.getIdProgramacionOriginal());
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.administrador_negocio.limite_error", limite));
		}
		if (puesto.contains(Constantes.GERENTE_CORREDOR)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorTerritorioEmpleadoPuesto(
					empleado.getId(), Constantes.GERENTE_CORREDOR, nuevaProgramacion.getFechaInicio(),
					nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,	"vacaciones.politica.bolsa.comercial.gerente_corredor.limite_error", limite));
		}
		logger.info("[END] validarPoliticaBolsaComercial");
		
	}

	@Override
	public void validarPoliticaBolsaRecuperaciones(VacacionProgramacion nuevaProgramacion, VacacionProgramacion programacionOriginal) {
		logger.info("[BEGIN] validarPoliticaBolsaRecuperaciones");
		Empleado empleado = programacionOriginal.getPeriodo().getEmpleado();
		String puesto = empleado.getPuesto().getDescripcion().trim();
		if (puesto.contains(Constantes.ANALISTA_COBRANZA)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorCorredorEmpleadoPuesto(
					empleado.getId(), Constantes.ANALISTA_COBRANZA, nuevaProgramacion.getFechaInicio(),
					nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.recuperaciones.analista_cobranza.limite_error", limite));
		}
		if (puesto.contains(Constantes.ANALISTA_RECUPERACIONES)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorEmpleadoPuesto(
					empleado.getId(), Constantes.ANALISTA_RECUPERACIONES, nuevaProgramacion.getFechaInicio(),
					nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.recuperaciones.analista_recuperaciones.limite_error", limite));
		}
		if (puesto.contains(Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA)) {
			int limite = 1;
			long cantidadProgramaciones = vacacionProgramacionService.contarProgramacionPorEmpleadoPuesto(
					empleado.getId(), Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA, nuevaProgramacion.getFechaInicio(),
					nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.recuperaciones.analista_recuperaciones.limite_error", limite));
		}
		logger.info("[END] validarPoliticaBolsaRecuperaciones");
	}

	@Override
	public void validarPoliticaBolsaOperaciones(VacacionProgramacion nuevaProgramacion,	VacacionProgramacion programacionOriginal) {
		logger.info("[BEGIN] validarPoliticaBolsaOperaciones");
		Empleado empleado = programacionOriginal.getPeriodo().getEmpleado();
		String puesto = empleado.getPuesto().getDescripcion().trim();
		int limite = 1;
		if (puesto.contains(Constantes.ASESOR_SERVICIO) || puesto.contains(Constantes.ASESOR_PLATAFORMA) || puesto.contains(Constantes.SUPERVISOR_OFICINA)) {
			long cantidadProgramacionesAsesorServicio = vacacionProgramacionService.contarProgramacionPorEmpleadoAgencia(
					empleado.getId(), Constantes.ASESOR_SERVICIO,
					nuevaProgramacion.getFechaInicio(), nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			long cantidadProgramacionesAsesorPlataforma = vacacionProgramacionService.contarProgramacionPorEmpleadoAgencia(
					empleado.getId(), Constantes.ASESOR_PLATAFORMA,
					nuevaProgramacion.getFechaInicio(), nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			long cantidadProgramacionesSupervidorOficina = vacacionProgramacionService.contarProgramacionPorEmpleadoAgencia(
					empleado.getId(), Constantes.SUPERVISOR_OFICINA,
					nuevaProgramacion.getFechaInicio(), nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			long cantidadProgramaciones = cantidadProgramacionesAsesorServicio + cantidadProgramacionesAsesorPlataforma	+ cantidadProgramacionesSupervidorOficina;
			cantidadProgramaciones++;
			if (cantidadProgramaciones > limite)
				throw new AppException(Utilitario.obtenerMensaje(messageSource,
						"vacaciones.politica.bolsa.operaciones.agencia.limite_error", limite));
			long cantidadEmpeadosRedOperaciones = empleadoService.obtenerCantidadEmpleadosRedOperaciones();
			double limiteRedOperaciones = cantidadEmpeadosRedOperaciones * 0.12;
			long cantidadProgramacionesRedComercial = vacacionProgramacionService.contarProgramacionPorEmpleadoRedOperaciones(empleado.getId(), 
					nuevaProgramacion.getFechaInicio(), nuevaProgramacion.getFechaFin(), programacionOriginal.getId());
			cantidadProgramacionesRedComercial++;
			if(cantidadProgramacionesRedComercial > limiteRedOperaciones)
				throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.politica.bolsa.operaciones.limite_red", 12));
		}
		Agencia agencia = empleado.getAgencia();
		if (agencia.getDescripcion().contains("EOB")) { // AGENCIA TAMBO - TAMBO_PLUS

		}
		logger.info("[END] validarPoliticaBolsaOperaciones");
	}
	
	@Override
	public void actualizarPeriodo(Empleado empleado, long idPeriodo, String usuarioOperacion) {
		logger.info("[BEGIN] actualizarPeriodo {}", idPeriodo);
		periodoVacacionService.consolidarResumenDias(idPeriodo, usuarioOperacion);
		periodoVacacionService.actualizarPeriodo(empleado, idPeriodo, usuarioOperacion);
		logger.info("[END] actualizarPeriodo");
	}

	@Override
	public void validarCantidadReprogramaciones(Empleado empleado, String usuarioOperacion) {
		logger.info("[BEGIN] validarCantidadReprogramaciones");
		LocalDate fechaActual = LocalDate.now();
		int reprogramacionesMaxima = cargaParametros.getCantidadMaximaReprogramacionAnio();
		VacacionReprogramacionContador contador = null;
		Optional<VacacionReprogramacionContador> opt = vacacionReprogramacionContadorService.obtenerPorEmpleadoAndAnio(empleado, fechaActual.getYear());
		if(opt.isPresent()) {
			contador = opt.get();
		} else {
			contador = vacacionReprogramacionContadorService.registrar(empleado.getId(), fechaActual.getYear(), usuarioOperacion);
		}
		if(contador == null)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "app.error.generico"));
		if(contador.getReprogramaciones() == reprogramacionesMaxima)
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "vacaciones.reprogramacion.limite_anio", reprogramacionesMaxima));
		logger.info("[END] validarCantidadReprogramaciones");
	}

	@Override
	public void actualizarCantidadReprogramaciones(Empleado empleado, String usuarioOperacion) {
		logger.info("[BEGIN] actualizarCantidadReprogramaciones");
		LocalDate fechaActual = LocalDate.now();
		vacacionReprogramacionContadorService.actualizarContador(empleado.getId(), fechaActual.getYear(), usuarioOperacion);
		logger.info("[END] actualizarCantidadReprogramaciones");
	}
	
	

}
