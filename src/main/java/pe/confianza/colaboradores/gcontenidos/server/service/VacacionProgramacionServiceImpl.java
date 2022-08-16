package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.ModelNotFoundException;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.DivisionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.EmpleadoDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.PeriodoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Division;
import pe.confianza.colaboradores.gcontenidos.server.negocio.EnvioNotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoMigracion;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class VacacionProgramacionServiceImpl implements VacacionProgramacionService {

	private static Logger logger = LoggerFactory.getLogger(VacacionProgramacionServiceImpl.class);

	@Autowired
	private VacacionProgramacionDao vacacionProgramacionDao;

	@Autowired
	private EmpleadoDao empleadoDao;

	@Autowired
	private CargaParametros cargaParametros;

	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	private EnvioNotificacionNegocio envioNotificacionNegocio;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DivisionDao divisionDao;

	@Override
	public List<VacacionProgramacion> actualizarEstadoProgramaciones() {
		logger.info("[BEGIN] actualizarEstadoProgramaciones");
		List<Long> idsProgramaciones = new ArrayList<>();
		String programacionesActualizadas = vacacionProgramacionDao.actualizarEstadoProgramaciones().trim();
		String[] programacionesArray = programacionesActualizadas.split(",");
		for (String prog : programacionesArray) {
			if (!prog.isEmpty()) {
				try {
					idsProgramaciones.add(Long.parseLong(prog));
				} catch (Exception e) {
					logger.error("[ERROR] actualizarEstadoProgramaciones", e);
				}
			}
		}
		List<VacacionProgramacion> programaciones = new ArrayList<>();
		for (Long id : idsProgramaciones) {
			try {
				programaciones.add(buscarPorId(id));
			} catch (Exception e) {
				logger.error("[ERROR] actualizarEstadoProgramaciones", e);
			}
		}
		logger.info("[END] actualizarEstadoProgramaciones");
		return programaciones;
	}

	@Override
	public List<VacacionProgramacion> listarPorPeriodoYEstado(PeriodoVacacion periodo, EstadoVacacion estado) {
		logger.info("[BEGIN] listarPorPeriodoYEstado");
		List<VacacionProgramacion> programaciones = vacacionProgramacionDao.findByPeriodoAndEstado(periodo.getId(),
				estado.id);
		programaciones = programaciones == null ? new ArrayList<>() : programaciones;
		logger.info("[END] listarPorPeriodoYEstado");
		return programaciones;
	}

	@Override
	public List<VacacionProgramacion> registrar(List<VacacionProgramacion> programaciones, String usuarioOperacion) {
		programaciones.forEach(programacion -> {
			programacion.setUsuarioCrea(usuarioOperacion);
			programacion.setFechaCrea(LocalDateTime.now());
			programacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
			programacion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		});
		return vacacionProgramacionDao.saveAll(programaciones);
	}

	@Override
	public List<VacacionProgramacion> modificar(List<VacacionProgramacion> programaciones, String usuarioOperacion) {
		programaciones.forEach(programacion -> {
			programacion.setUsuarioModifica(usuarioOperacion);
			programacion.setFechaModifica(LocalDateTime.now());
			programacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
			if (EstadoMigracion.EXPORTADO.valor.equals(programacion.getEstadoMigracion())
					|| EstadoMigracion.IMPORTADO.valor.equals(programacion.getEstadoMigracion())) {
				programacion.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
			}
		});
		return vacacionProgramacionDao.saveAll(programaciones);
	}

	@Override
	public VacacionProgramacion registrar(VacacionProgramacion programacion, String usuarioOperacion) {
		programacion.setUsuarioCrea(usuarioOperacion);
		programacion.setFechaCrea(LocalDateTime.now());
		programacion.setEstadoRegistro(EstadoRegistro.ACTIVO.valor);
		programacion.setEstadoMigracion(EstadoMigracion.NUEVO.valor);
		return vacacionProgramacionDao.saveAndFlush(programacion);
	}

	@Override
	public VacacionProgramacion actualizar(VacacionProgramacion programacion, String usuarioOperacion) {
		programacion.setUsuarioModifica(usuarioOperacion);
		programacion.setFechaModifica(LocalDateTime.now());
		if (EstadoMigracion.EXPORTADO.valor.equals(programacion.getEstadoMigracion())
				|| EstadoMigracion.IMPORTADO.valor.equals(programacion.getEstadoMigracion())) {
			programacion.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
		}
		return vacacionProgramacionDao.saveAndFlush(programacion);
	}

	@Override
	public void eliminar(long idProgramacion, String usuarioOperacion) {
		Optional<VacacionProgramacion> optProgramacion = vacacionProgramacionDao.findById(idProgramacion);
		if (!optProgramacion.isPresent())
			throw new ModelNotFoundException("No existe la programación con id " + idProgramacion);
		VacacionProgramacion progamacion = optProgramacion.get();
		progamacion.setEstadoRegistro(EstadoRegistro.INACTIVO.valor);
		progamacion.setIdEstado(4);
		if (EstadoMigracion.IMPORTADO.valor.equals(progamacion.getEstadoMigracion()))
			progamacion.setEstadoMigracion(EstadoMigracion.MODIFICADO.valor);
		actualizar(progamacion, usuarioOperacion);
	}

	@Override
	public VacacionProgramacion buscarPorId(long idProgramacion) {
		Optional<VacacionProgramacion> optProgramacion = vacacionProgramacionDao.findById(idProgramacion);
		if (!optProgramacion.isPresent())
			throw new ModelNotFoundException("No existe la programación con id " + idProgramacion);
		return optProgramacion.get();
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBTYPeriodo(String usuarioBT, String periodo) {
		logger.info("[BEGIN] buscarPorUsuarioBTYPeriodo");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByUsuarioBTAndPeriodo(usuarioBT,
				periodo);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBTYPeriodo");
		return lstProgramacion;
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBTYEstado(String usuarioBT, EstadoVacacion estado) {
		logger.info("[BEGIN] buscarPorUsuarioBTYEstado");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByUsuarioBTAndIdEstado(usuarioBT,
				estado.id);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBTYEstado");
		return lstProgramacion;
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBTYPeriodoYEstado(String usuarioBT, String periodo,
			EstadoVacacion estado) {
		logger.info("[BEGIN] buscarPorUsuarioBTYPeriodoYEstado");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao
				.findByUsuarioBTAndPeriodoAndEstado(usuarioBT, periodo, estado.id);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBTYPeriodoYEstado");
		return lstProgramacion;
	}

	@Override
	public List<VacacionProgramacion> buscarPorUsuarioBT(String usuarioBT) {
		logger.info("[BEGIN] buscarPorUsuarioBT");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByUsuarioBT(usuarioBT);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] buscarPorUsuarioBT");
		return lstProgramacion;
	}

	@Override
	@Transactional
	public void aprobarVacacionPeriodos(List<VacacionPeriodo> vacacionPeriodos) {
		// TODO Auto-generated method stub

		LocalDate fechaMaxima = cargaParametros.getFechaMaximaAprobacionProgramaciones();
		LocalDate hoy = LocalDate.now();
		if (fechaMaxima.compareTo(hoy) <= 0) {
			throw new AppException("La fecha máxima de aprobación es " + fechaMaxima.getDayOfMonth() + "/"
					+ fechaMaxima.getMonth() + "/" + fechaMaxima.getYear());
		}

		try {
			vacacionPeriodos.forEach(v -> {
				vacacionProgramacionDao.aprobarVacacionByPeriodo(v.getIdEstado(), v.getIdProgramacion());

				Optional<VacacionProgramacion> vp = vacacionProgramacionDao.findById(v.getIdProgramacion());
				boolean esReprogramacion = vp.get().getIdProgramacionOriginal() == null ? false : true;
				boolean esAdelantada = vp.get().isVacacionesAdelantadas();
				List<Empleado> emp = empleadoDao.findByCodigo(vp.get().getCodigoEmpleado());
				Optional<NotificacionTipo> tipoNot = notificacionService
						.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_COLABORADOR.valor);
				Optional<Empleado> empAprobador = empleadoDao.findOneByUsuarioBT(v.getAprobadorBt());
				String[] emails = { "vacaciones@confianza.pe", empAprobador.get().getEmail() };

				if (v.getIdEstado() == 3) {
					Notificacion notificacion = null;
					if (esReprogramacion) {
						StringBuilder mensaje = new StringBuilder();
						mensaje.append(Utilitario
								.obtenerMensaje(messageSource, "vacaciones.notificacion.reprogramacion.aceptada"))
								.append(" Del ")
								.append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA,
										vp.get().getFechaInicio()))
								.append(" al ")
								.append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, vp.get().getFechaFin()))
								.append(" - ").append(vp.get().getNumeroDias()).append(" Dias - vacaciones vigentes");
						notificacion = notificacionService.registrar("VACACIÓN APROBADA", mensaje.toString(),
								v.getIdEstado().toString(), tipoNot.get(), emp.get(0), emp.get(0).getUsuarioBT());

						notificacionService.enviarNotificacionCorreo(notificacion, emails);

					} else {
						if (esAdelantada) {
							StringBuilder mensaje = new StringBuilder();
							mensaje.append(Utilitario.obtenerMensaje(messageSource,
									"vacaciones.notificacion.reprogramacion.adelantada.aceptada")).append(" Del ")
									.append(Utilitario
											.fechaToStringPer(Constantes.FORMATO_FECHA, vp.get().getFechaInicio()))
									.append(" al ")
									.append(Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA,
											vp.get().getFechaFin()))
									.append(" - ").append(vp.get().getNumeroDias())
									.append(" Dias - vacaciones adelantadas");
							notificacion = notificacionService.registrar("VACACIÓN APROBADA", mensaje.toString(),
									v.getIdEstado().toString(), tipoNot.get(), emp.get(0), emp.get(0).getUsuarioBT());
						} else {
							notificacion = notificacionService.registrar("VACACIÓN APROBADA",
									"Vacacion con fecha inicio "
											+ Utilitario.fechaToStringPer(
													Constantes.FORMATO_FECHA, vp.get().getFechaInicio())
											+ " y fecha fin "
											+ Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA,
													vp.get().getFechaFin())
											+ " fue aprobada",
									v.getIdEstado().toString(), tipoNot.get(), emp.get(0), emp.get(0).getUsuarioBT());
						}

					}

					if (notificacion != null) {

						notificacionService.enviarNotificacionApp(notificacion);
						notificacionService.enviarNotificacionCorreo(notificacion, emails);

					}

				} else if (v.getIdEstado() == 4) {
					Notificacion notificacion = notificacionService.registrar("VACACIÓN RECHAZADA",
							"Vacación con fecha inicio "
									+ Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, vp.get().getFechaInicio())
									+ " y fecha fin "
									+ Utilitario.fechaToStringPer(Constantes.FORMATO_FECHA, vp.get().getFechaFin())
									+ " fue rechazada",
							v.getIdEstado().toString(), tipoNot.get(), emp.get(0), emp.get(0).getUsuarioBT());

					notificacionService.enviarNotificacionApp(notificacion);
					notificacionService.enviarNotificacionCorreo(notificacion);

				}

			});
		} catch (Exception e2) {
			logger.error("[ERROR] aprobarVacacionPeriodos", e2);
			throw new AppException("Error al aprobar vacaciones");
		}

	}

	@Override
	public VacacionProgramacion obtenerUltimaProgramacion(long idPeriodo) {
		logger.info("[BEGIN] obtenerUltimaProgramacion");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByIdPeriodo(idPeriodo);
		if (lstProgramacion == null)
			return null;
		if (lstProgramacion.isEmpty())
			return null;
		logger.info("[END] obtenerUltimaProgramacion");
		return lstProgramacion.get(0);
	}

	@Override
	public List<VacacionProgramacion> listarPorPeriodo(long idPeriodo) {
		logger.info("[BEGIN] listarPorPeriodo");
		List<VacacionProgramacion> lstProgramacion = vacacionProgramacionDao.findByIdPeriodo(idPeriodo);
		lstProgramacion = lstProgramacion == null ? new ArrayList<>() : lstProgramacion;
		logger.info("[END] listarPorPeriodo");
		return lstProgramacion;
	}

	@Override
	public int obtenerSumaDiasPorPeriodoYEstado(long idPeriodo, EstadoVacacion estado) {
		logger.info("[BEGIN] obtenerSumaDiasPorPeriodoYEstado {} - {}",
				new Object[] { idPeriodo, EstadoVacacion.REGISTRADO });
		int diasPorPeriodoEstado = vacacionProgramacionDao.obtenerSumaDiasPorIdPeriodoYEstado(idPeriodo, estado.id);
		logger.info("[BEGIN] obtenerSumaDiasPorPeriodoYEstado");
		return diasPorPeriodoEstado;
	}

	@Override

	public long contarProgramacionPorUnidadNegocioEmpleado(Long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar) {
		logger.info("[BEGIN] contarProgramacionPorUnidadNegocioEmpleado {}", idEmpleado);
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorUnidadNegocioEmpleado(idEmpleado, descripcionPuesto,
				strFechaInicio, strFechaFin, idProgReprogramar);

	}

	@Override
	public long contarProgramacionPorCorredorEmpleadoPuesto(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar) {
		logger.info("[BEGIN] contarProgramacionPorCorredorEmpleadoPuesto {} - {}",
				new Object[] { idEmpleado, descripcionPuesto });
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorCorredorEmpleadoPuesto(idEmpleado, descripcionPuesto,
				strFechaInicio, strFechaFin, idProgReprogramar);
	}

	@Override
	public long contarProgramacionPorTerritorioEmpleadoPuesto(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar) {
		logger.info("[BEGIN] contarProgramacionPorTerritorioEmpleadoPuesto {} - {}",
				new Object[] { idEmpleado, descripcionPuesto });
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorTerritorioEmpleadoPuesto(idEmpleado, descripcionPuesto,
				strFechaInicio, strFechaFin, idProgReprogramar);
	}

	@Override
	public long contarProgramacionPorEmpleadoPuesto(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar) {
		logger.info("[BEGIN] contarProgramacionPorEmpleadoPuesto {} - {}",
				new Object[] { idEmpleado, descripcionPuesto });
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorEmpleadoPuesto(idEmpleado, descripcionPuesto,
				strFechaInicio, strFechaFin, idProgReprogramar);
	}

	@Override
	public long contarProgramacionPorEmpleadoAgencia(long idEmpleado, String descripcionPuesto,
			LocalDate fechaIncioProgramacion, LocalDate fechaFinProgramacion, Long idProgReprogramar) {
		logger.info("[BEGIN] contarProgramacionPorEmpleadoAgencia {} - {}",
				new Object[] { idEmpleado, descripcionPuesto });
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorEmpleadoAgencia(idEmpleado, descripcionPuesto,
				strFechaInicio, strFechaFin, idProgReprogramar);
	}

	@Override
	public long contarProgramacionPorEmpleadoRedOperaciones(long idEmpleado, LocalDate fechaIncioProgramacion,
			LocalDate fechaFinProgramacion, Long idProgReprogramar) {
		logger.info("[BEGIN] contarProgramacionPorEmpleadoAgencia {}", new Object[] { idEmpleado });
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String strFechaInicio = fechaIncioProgramacion.format(formatter);
		String strFechaFin = fechaFinProgramacion.format(formatter);
		return vacacionProgramacionDao.contarProgramacionPorEmpleadoRedOperaciones(idEmpleado, strFechaInicio,
				strFechaFin, idProgReprogramar);
	}

	@Override
	public List<EmplVacPerRes> listEmpleadoByprogramacion(RequestProgramacionEmpleado reqPrograEmp) {

		List<EmplVacPerRes> listEmpByProgramacion = listEmpleadoProgramacionFilter(reqPrograEmp);

		List<EmplVacPerRes> listEmp = new ArrayList<>();

		switch (reqPrograEmp.getTipoFiltro().toUpperCase().trim()) {
		case "NOMBRE":
			listEmp = listEmpByProgramacion.stream().filter(e -> {
				boolean encontrado = false;
				for (String filtro : reqPrograEmp.getFiltro()) {
					if (filtro.equalsIgnoreCase(e.getNombres()))
						encontrado = true;
				}
				return encontrado;
			}).collect(Collectors.toList());
			break;
		case "CARGO":
			listEmp = listEmpByProgramacion.stream().filter(e -> {
				boolean encontrado = false;
				for (String filtro : reqPrograEmp.getFiltro()) {
					if (filtro.equalsIgnoreCase(e.getPuesto()))
						encontrado = true;
				}
				return encontrado;
			}).collect(Collectors.toList());
			break;
		case "AGENCIA":
			listEmp = listEmpByProgramacion.stream().filter(e -> {
				boolean encontrado = false;
				for (String filtro : reqPrograEmp.getFiltro()) {
					if (filtro.equalsIgnoreCase(e.getAgencia()))
						encontrado = true;
				}
				return encontrado;
			}).collect(Collectors.toList());
			break;
		case "TERRITORIO":
			listEmp = listEmpByProgramacion.stream().filter(e -> {
				boolean encontrado = false;
				for (String filtro : reqPrograEmp.getFiltro()) {
					if (filtro.equalsIgnoreCase(e.getTerritorio()))
						encontrado = true;
				}
				return encontrado;
			}).collect(Collectors.toList());
			break;
		case "CORREDOR":
			listEmp = listEmpByProgramacion.stream().filter(e -> {
				boolean encontrado = false;
				for (String filtro : reqPrograEmp.getFiltro()) {
					if (filtro.equalsIgnoreCase(e.getCorredor()))
						encontrado = true;
				}
				return encontrado;
			}).collect(Collectors.toList());
			break;
		case "TRAMO":
			listEmp = listEmpByFecha(listEmpByProgramacion, reqPrograEmp);
			break;
		case "DIVISION":
			listEmp = listEmpByProgramacion.stream().filter(e -> {
				boolean encontrado = false;
				if (e.getDivision() != null) {
					for (String filtro : reqPrograEmp.getFiltro()) {
						if (filtro.equalsIgnoreCase(e.getDivision()))
							encontrado = true;
					}

				}
				return encontrado;
			}).collect(Collectors.toList());
			break;
		default:
			listEmp = listEmpByProgramacion;
			break;
		}

		return listEmp;
	}

	private List<EmplVacPerRes> listEmpByFecha(List<EmplVacPerRes> listEmpByProgramacion,
			RequestProgramacionEmpleado reqPrograEmp) {

		List<EmplVacPerRes> listEmp = new ArrayList<>();

		listEmpByProgramacion.stream().forEach(e -> {

			if (e.getFechaInicio().isAfter(reqPrograEmp.getFechaInicio())
					&& e.getFechaInicio().isBefore(reqPrograEmp.getFechaFin())) {
				listEmp.add(e);
			} else if (e.getFechaFin().isAfter(reqPrograEmp.getFechaInicio())
					&& e.getFechaFin().isBefore(reqPrograEmp.getFechaFin())) {
				listEmp.add(e);
			}
		});

		return listEmp;
	}

	@Override
	public List<EmplVacPerRes> listEmpleadoProgramacionFilter(RequestProgramacionEmpleado reqPrograEmp) {

		List<EmplVacPerRes> listEmp = new ArrayList<EmplVacPerRes>();

		Optional<Empleado> emAprobador = empleadoDao.findOneByUsuarioBT(reqPrograEmp.getUsuarioBt());

		List<Empleado> emsByAprobador = empleadoDao.findByCodigoAprobador(emAprobador.get().getCodigo());

		if (!CollectionUtils.isEmpty(emsByAprobador)) {
			for (Empleado e : emsByAprobador) {

				List<VacacionProgramacion> lVp = empleadoDao.findPeriodosByEmpleado(e.getId());
				for (VacacionProgramacion v : lVp) {

//					Empleado empleado = empleadoService
//							.getEmpleadoCorredorTerritorioBt(v.getPeriodo().getEmpleado().getUsuarioBT());

					EmplVacPerRes emp = new EmplVacPerRes();
					emp.setNombres(e.getNombreCompleto());
					emp.setIdEmpleado(e.getId());
					emp.setPuesto(e.getPuesto().getDescripcion());
					emp.setUsuarioBt(e.getUsuarioBT());

					emp.setIdProgramacion(v.getId());
					emp.setFechaInicio(v.getFechaInicio());
					emp.setFechaFin(v.getFechaFin());
					emp.setIdEstado(v.getIdEstado());
					emp.setPeriodo(v.getPeriodo().getDescripcion());
					emp.setAgencia(v.getPeriodo().getEmpleado().getAgencia().getDescripcion());
					String[] estadoProg = cargaParametros.getEstadoProgramacionDescripcion(v.getIdEstado());
					emp.setDescripcionEstado(estadoProg[0]);
					emp.setLeyendaEstado(estadoProg[1]);
					emp.setTerritorio(e.getAgencia().getCorredor().getTerritorio().getDescripcion());
					emp.setCorredor(e.getAgencia().getCorredor().getDescripcion());
					emp.setAdelantada(v.isVacacionesAdelantadas());

					List<Division> divisiones = divisionDao.listDivisionByCodigoSpring(e.getCodigoGerenteDivision());
					emp.setDivision(divisiones.get(0).getDescripcion());

					listEmp.add(emp);
				}
			}
		}
		return listEmp;
	}

	@Override
	public List<Map<String, String>> listFilstrosVacacion(RequestProgramacionEmpleado reqFiltros) {
		List<EmplVacPerRes> listEmpByProgramacion = listEmpleadoProgramacionFilter(reqFiltros);
		List<Map<String, String>> datos = new ArrayList<>();

		switch (reqFiltros.getTipoFiltro().toUpperCase().trim()) {
		case "NOMBRE":
			datos = listafiltrosvacacion(listEmpByProgramacion, reqFiltros.getTipoFiltro());
			break;
		case "CARGO":
			datos = listafiltrosvacacion(listEmpByProgramacion, reqFiltros.getTipoFiltro());
			break;
		case "AGENCIA":
			datos = listafiltrosvacacion(listEmpByProgramacion, reqFiltros.getTipoFiltro());
			break;
		case "TERRITORIO":
			datos = listafiltrosvacacion(listEmpByProgramacion, reqFiltros.getTipoFiltro());
			break;
		case "CORREDOR":
			datos = listafiltrosvacacion(listEmpByProgramacion, reqFiltros.getTipoFiltro());
			break;
		case "DIVISION":
			datos = listafiltrosvacacion(listEmpByProgramacion, reqFiltros.getTipoFiltro());
			break;
		default:
			break;
		}
		return datos;
	}

	private List<Map<String, String>> listafiltrosvacacion(List<EmplVacPerRes> listEmpByProgramacion, String filtro) {

		List<Map<String, String>> datos = new ArrayList<>();
		List<String> valores = new ArrayList<>();

		for (EmplVacPerRes e : listEmpByProgramacion) {
			switch (filtro.toUpperCase()) {
			case "NOMBRE":
				valores.add(e.getNombres());
				break;
			case "CARGO":
				valores.add(e.getPuesto());
				break;
			case "AGENCIA":
				valores.add(e.getAgencia());
				break;
			case "TERRITORIO":
				valores.add(e.getTerritorio());
				break;
			case "CORREDOR":
				valores.add(e.getCorredor());
				break;
			case "DIVISION":
				valores.add(e.getDivision());
				break;
			default:
				break;
			}
		}

		valores = valores.stream().distinct().collect(Collectors.toList());

		for (Integer i = 0; i <= valores.size() - 1; i++) {
			Map<String, String> nombres = new HashMap<>();

			nombres.put("id", (i).toString());
			nombres.put("descripcion", valores.get(i));

			datos.add(nombres);
		}

		return datos;
	}

	@Override
	public List<Map<String, String>> listFilstrosVacacionAprobacion(RequestFiltroVacacionesAprobacion reqFiltros) {
		// TODO Auto-generated method stub
		List<Map<String, String>> datos = new ArrayList<>();
		switch (reqFiltros.getFiltro().toUpperCase().trim()) {
		case "NOMBRE":
			datos = empleadoDao.findNombreByCodigoN1(reqFiltros.getCodigo(), reqFiltros.getCodigo());
			break;
		case "CARGO":
			datos = empleadoDao.findPuestoByCodigoN1(reqFiltros.getCodigo(), reqFiltros.getCodigo());
			break;
		case "AGENCIA":
			datos = empleadoDao.findAgenciaByCodigoN1(reqFiltros.getCodigo(), reqFiltros.getCodigo());
			break;
		case "TERRITORIO":
			datos = empleadoDao.findTerritorioByCodigoN1(reqFiltros.getCodigo(), reqFiltros.getCodigo());
			break;
		case "CORREDOR":
			datos = empleadoDao.findCorredorByCodigoN1(reqFiltros.getCodigo(), reqFiltros.getCodigo());
			break;
		case "DIVISION":
			datos = empleadoDao.findDivisionByCodigoN1(reqFiltros.getCodigo(), reqFiltros.getCodigo());
		default:
			break;
		}
		return datos;
	}

	@Override
	public List<VacacionProgramacion> listarProgramacionesPorAnio(int anio, String usuarioBT) {
		logger.info("[BEGIN] listarProgramacionesPorAnio {}", anio);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaInicio = LocalDate.parse("01/01/" + anio, formatter);
		LocalDate fechaFin = LocalDate.parse("31/12/" + anio, formatter);
		List<VacacionProgramacion> programaciones = vacacionProgramacionDao.findBetweenDates(fechaInicio, fechaFin,
				usuarioBT);
		programaciones = programaciones == null ? new ArrayList<>() : programaciones;
		logger.info("[END] listarProgramacionesPorAnio");
		return programaciones;
	}

	@Override
	public Map<Empleado, List<VacacionProgramacion>> listarProgramacionesPorAnioYAprobadorNivelI(int anio,
			long codigoAprobador) {
		logger.info("[BEGIN] listarProgramacionesPorAnioYAprobadorNivelI {} {}", anio, codigoAprobador);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaInicio = LocalDate.parse("01/01/" + anio, formatter);
		LocalDate fechaFin = LocalDate.parse("31/12/" + anio, formatter);
		List<VacacionProgramacion> programaciones = vacacionProgramacionDao
				.findBetweenDatesAndAprobadorNivelI(fechaInicio, fechaFin, codigoAprobador);
		programaciones = programaciones == null ? new ArrayList<>() : programaciones;
		Map<Empleado, List<VacacionProgramacion>> empleadosProg = new HashMap<>();
		for (VacacionProgramacion prog : programaciones) {
			if (empleadosProg.get(prog.getPeriodo().getEmpleado()) == null)
				empleadosProg.put(prog.getPeriodo().getEmpleado(), new ArrayList<>());
			empleadosProg.get(prog.getPeriodo().getEmpleado()).add(prog);
		}
		logger.info("[END] listarProgramacionesPorAnioYAprobadorNivelI");
		return empleadosProg;
	}

	@Override
	public Map<Empleado, List<VacacionProgramacion>> listarProgramacionesPorAnioYAprobadorNivelII(int anio,
			long codigoAprobador) {
		logger.info("[BEGIN] listarProgramacionesPorAnioYAprobadorNivelII {} {}", anio, codigoAprobador);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaInicio = LocalDate.parse("01/01/" + anio, formatter);
		LocalDate fechaFin = LocalDate.parse("31/12/" + anio, formatter);
		List<VacacionProgramacion> programaciones = vacacionProgramacionDao
				.findBetweenDatesAndAprobadorNivelII(fechaInicio, fechaFin, codigoAprobador);
		programaciones = programaciones == null ? new ArrayList<>() : programaciones;
		Map<Empleado, List<VacacionProgramacion>> empleadosProg = new HashMap<>();
		for (VacacionProgramacion prog : programaciones) {
			if (empleadosProg.get(prog.getPeriodo().getEmpleado()) == null)
				empleadosProg.put(prog.getPeriodo().getEmpleado(), new ArrayList<>());
			empleadosProg.get(prog.getPeriodo().getEmpleado()).add(prog);
		}
		logger.info("[END] listarProgramacionesPorAnioYAprobadorNivelII");
		return empleadosProg;
	}

	@Override
	public Page<VacacionProgramacion> listarProgramacionesPorInterrumpirYAnular(String nombre, Pageable pageable) {
		if (nombre.isEmpty())
			return vacacionProgramacionDao.listarProgramacionesPorInterrumpirYAnular(pageable);
		return vacacionProgramacionDao.listarProgramacionesPorInterrumpirYAnular(nombre, pageable);
	}

}
