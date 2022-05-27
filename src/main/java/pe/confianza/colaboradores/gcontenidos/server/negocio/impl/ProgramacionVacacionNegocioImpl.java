package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
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
	
	@Override
	public ResponseProgramacionVacacion registro(RequestProgramacionVacacion programacion) {
		LOGGER.info("[BEGIN] registro: {} - {} - {}", new Object[] {programacion.getUsuarioBT(), programacion.getFechaInicio(), programacion.getFechaFin()});
		validarFechaRegistro(programacion.getFechaInicio());
		Empleado empleado = empleadoService.actualizarInformacionEmpleado(programacion.getUsuarioBT().trim());
		if(empleado == null)
			throw new AppException("No existe el usuario " + programacion.getUsuarioBT());
		String usuarioOperacion = programacion.getUsuarioOperacion().trim();
		VacacionProgramacion vacacionProgramacion = VacacionProgramacionMapper.convert(programacion);
		vacacionProgramacion.setEstado(EstadoVacacion.REGISTRADO);
		
		validarEmpleadoNuevo(vacacionProgramacion, empleado);
		validarRangoFechas(vacacionProgramacion);
		obtenerPeriodo(empleado, vacacionProgramacion);
		validarTramoVacaciones(vacacionProgramacion);
		obtenerOrden(vacacionProgramacion, usuarioOperacion);
		
		vacacionProgramacion = vacacionProgramacionService.registrar(vacacionProgramacion, usuarioOperacion);
		actualizarPeriodo(empleado, usuarioOperacion);
		LOGGER.info("[END] registroProgramacion");
		return VacacionProgramacionMapper.convert(vacacionProgramacion);
	}
	
	@Override
	public void cancelar(RequestCancelarProgramacionVacacion cancelacion) {
		LOGGER.info("[BEGIN] cancelar");
		Empleado empleado = empleadoService.buscarPorUsuarioBT(cancelacion.getUsuarioOperacion().trim());
		if(empleado == null)
			throw new ModelNotFoundException("No existe el usuario " + cancelacion.getUsuarioOperacion());
		VacacionProgramacion programacion = vacacionProgramacionService.buscarPorId(cancelacion.getIdProgramacion());
		if(programacion.getPeriodo().getEmpleado().getId() != empleado.getId())
			throw new AppException("El usuario no tiene permisos para eliminar esta programación");
		if(programacion.getIdEstado() != EstadoVacacion.REGISTRADO.id)
			throw new AppException("La programación no se encuentra en estado " + EstadoVacacion.REGISTRADO.descripcion);
		vacacionProgramacionService.eliminar(cancelacion.getIdProgramacion());
		actualizarPeriodo(empleado, cancelacion.getUsuarioOperacion());
		LOGGER.info("[END] cancelar");
	}
	
	@Override
	public List<ResponseProgramacionVacacion> generar(RequestGenerarProgramacionVacacion request) {
		LOGGER.info("[BEGIN] generar");
		Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioOperacion().trim());
		if(empleado == null)
			throw new ModelNotFoundException("No existe el usuario " + request.getUsuarioOperacion());
		List<VacacionProgramacion> programacionesGenerar = vacacionProgramacionService.buscarPorUsuarioBTYEstado(request.getUsuarioOperacion().trim(), EstadoVacacion.REGISTRADO);
		List<ResponseProgramacionVacacion> response = new ArrayList<>();
		for (VacacionProgramacion programacion : programacionesGenerar) {
			programacion.setEstado(EstadoVacacion.GENERADO);
			programacion = vacacionProgramacionService.actualizar(programacion, request.getUsuarioOperacion());
			response.add(VacacionProgramacionMapper.convert(programacion));
		}
		actualizarPeriodo(empleado, request.getUsuarioOperacion());
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
			throw new ModelNotFoundException("No existe el usuario " + request.getUsuarioBT());
		
		LocalDate fechaConsulta = LocalDate.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String valorFechaFin = parametrosConstants.FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES;
		if(valorFechaFin.isEmpty())
			throw new AppException("No existe el parámetro para fin de registro de programación de vacaciones");
		LocalDate fechaCorte = LocalDate.parse(valorFechaFin + "/" + fechaConsulta.getYear(), formatter);
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
		List<PeriodoVacacion> lstPeriodos = periodoVacacionService.consultar(empleado);
		lstPeriodos.sort(Comparator.comparing(PeriodoVacacion::getAnio).reversed());
		if(lstPeriodos.size() > 0) { // PERIODO TRUNCO
			PeriodoVacacion periodo = lstPeriodos.get(0);
			VacacionProgramacion ultimaProgramacion = vacacionProgramacionService.obtenerUltimaProgramacion(periodo.getId());
			periodoTrunco = new ResponseResumenPeriodoVacacion();
			periodoTrunco.setDescripcion(periodo.getDescripcion());
			double derecho = Utilitario.calcularDerechoVacaciones(empleado.getFechaIngreso(), fechaCorte);
			periodoTrunco.setDias(derecho - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar());
			LocalDate fechaLimite = empleado.getFechaIngreso().plusYears(periodo.getAnio() - empleado.getFechaIngreso().getYear() + 1).plusDays(-1);
			periodoTrunco.setFechaLimite(fechaLimite);
			periodoTrunco.setUltimoTramo(ultimaProgramacion == null ? 0  : ultimaProgramacion.getOrden());
		}
		if(lstPeriodos.size() > 1) { // PERIODO VENCIDO
			PeriodoVacacion periodo = lstPeriodos.get(1);
			VacacionProgramacion ultimaProgramacion = vacacionProgramacionService.obtenerUltimaProgramacion(periodo.getId());
			periodoVencido = new ResponseResumenPeriodoVacacion();
			periodoVencido.setDescripcion(periodo.getDescripcion());
			periodoVencido.setDias(periodo.getDerecho() - periodo.getDiasGozados() - periodo.getDiasRegistradosGozar());
			LocalDate fechaLimite = empleado.getFechaIngreso().plusYears(periodo.getAnio() - empleado.getFechaIngreso().getYear() + 1 ).plusDays(-1);
			periodoVencido.setFechaLimite(fechaLimite);
			periodoVencido.setUltimoTramo(ultimaProgramacion == null ? 0  : ultimaProgramacion.getOrden());
		}
		response.setPeriodoTrunco(periodoTrunco);
		response.setPeriodoVencido(periodoVencido);
		response.setMeta(Utilitario.calcularMetaVacaciones(empleado.getFechaIngreso(), periodoVencido == null ? 0 : periodoVencido.getDias()));
		LOGGER.info("[BEGIN] consultar");
		return response;
	}


	@Override
	public void validarFechaRegistro(LocalDate fechaInicioVacacion) {
		LOGGER.info("[BEGIN] validarFechaRegistro");
		LocalDate ahora = LocalDate.now();
		if(fechaInicioVacacion.isBefore(ahora))
			throw new AppException("Fecha de inicio no puede ser una fecha anterior a la de hoy");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String valorFechaInicio = parametrosConstants.FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES;
		if(valorFechaInicio.isEmpty())
			throw new AppException("No existe el parámetro para inicio de registro de programación de vacaciones");
		String strFechaInicioRegistroProgramacion = valorFechaInicio.trim() + "/" + ahora.getYear();
		if(ahora.isBefore(LocalDate.parse(strFechaInicioRegistroProgramacion, formatter)))
			throw new AppException("No se puede registrar antes de " + strFechaInicioRegistroProgramacion);
		String valorFechaFin = parametrosConstants.FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES;
		if(valorFechaFin.isEmpty())
			throw new AppException("No existe el parámetro para fin de registro de programación de vacaciones");
		String strFechaFinRegistroProgramacion = valorFechaFin.trim() + "/" + ahora.getYear();
		if(ahora.isAfter(LocalDate.parse(strFechaFinRegistroProgramacion, formatter)))
			throw new AppException("No se puede registrar después de " + strFechaFinRegistroProgramacion);
		LOGGER.info("[END] validarFechaRegistro");
		
	}

	@Override
	public void validarEmpleadoNuevo(VacacionProgramacion programacion, Empleado empleado) {
		LOGGER.info("[BEGIN] validarEmpleadoNuevo");
		LocalDate fechaParaPedirVacacion = empleado.getFechaIngreso().plusMonths(6);
		LOGGER.info("Empleado -> fecha ingreso: {}", new Object[] {fechaParaPedirVacacion}  );
		if(programacion.getFechaInicio().isBefore(fechaParaPedirVacacion))
			throw new AppException("Debe tener una antiguedad de 6 meses");
		LOGGER.info("[END] validarEmpleadoNuevo");
	}

	@Override
	public void validarRangoFechas(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarRangoFechas");
		if(programacion.getFechaInicio().isAfter(programacion.getFechaFin()))
			throw new AppException("La fecha de inicio no puede ser mayor a la fecha fin");
		LOGGER.info("[END] validarRangoFechas");
	}

	@Override
	public void obtenerPeriodo(Empleado empleado, VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] obtenerPeriodo");
		PeriodoVacacion periodo = periodoVacacionService.obtenerPeriodo(empleado, programacion);
		if(periodo == null)
			throw new AppException("No tiene perido habilitado");
		double diasLibresPorProgramar = periodo.getDerecho() - periodo.getDiasGozados() - periodo.getDiasAprobadosGozar() - periodo.getDiasRegistradosGozar();
		if(programacion.getNumeroDias() > diasLibresPorProgramar)
			throw new AppException("Debe dividir su programación en dos tramos");
		programacion.setPeriodo(periodo);
		LOGGER.info("[END] obtenerPeriodo");
	}

	@Override
	public void validarTramoVacaciones(VacacionProgramacion programacion) {
		LOGGER.info("[BEGIN] validarTramoVacaciones");
		List<VacacionProgramacion> programacionesRegistradas = vacacionProgramacionService.listarPorPeriodoYEstado(programacion.getPeriodo(), EstadoVacacion.REGISTRADO);
		programacionesRegistradas = Utilitario.ordenarProgramaciones(programacionesRegistradas);
		int diasAcumuladosVacaciones = 0;
		int contadorSabados = 0;
		int contadorDomingos = 0;
		for (VacacionProgramacion vacacionProgramacion : programacionesRegistradas) {
			if(Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), programacion.getFechaInicio())) {
				throw new AppException("Cambie la fecha de inicio");
			}
			if(Utilitario.fechaEntrePeriodo(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin(), programacion.getFechaFin())) {
				throw new AppException("Cambie la fecha de fin");
			}
			diasAcumuladosVacaciones += Utilitario.obtenerDiferenciaDias(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin());
			contadorSabados += Utilitario.obtenerCantidadSabados(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin());
			contadorDomingos += Utilitario.obtenerCantidadDomingos(vacacionProgramacion.getFechaInicio(), vacacionProgramacion.getFechaFin());
		}
		int diasProgramacion = Utilitario.obtenerDiferenciaDias(programacion.getFechaInicio(), programacion.getFechaFin());
		if(diasAcumuladosVacaciones == 0 && diasProgramacion < 7) {
			throw new AppException("Debe tener 7 dias como mínimo");
		}
		if(diasAcumuladosVacaciones == 7 && diasProgramacion < 8) {
			throw new AppException("Debe tener 8 dias como mínimo");
		}
		if(diasAcumuladosVacaciones == 8 && diasProgramacion < 7) {
			throw new AppException("Debe tener 7 dias como mínimo");
		}
		contadorSabados += Utilitario.obtenerCantidadSabados(programacion.getFechaInicio(), programacion.getFechaFin());
		if(contadorSabados > 4) {
			throw new AppException("No debe haber más de 4 sábados");
		}
		contadorDomingos += Utilitario.obtenerCantidadDomingos(programacion.getFechaInicio(), programacion.getFechaFin());
		if(contadorDomingos > 4) {
			throw new AppException("No debe haber más de 4 domingos");
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
	
}
