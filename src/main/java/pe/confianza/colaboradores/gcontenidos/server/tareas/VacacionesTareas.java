package pe.confianza.colaboradores.gcontenidos.server.tareas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelI;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelII;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionAprobadorService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;

@Component
@PropertySource("classpath:tareas-programadas.properties")
public class VacacionesTareas {

	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionesTareas.class);

	@Autowired
	private VacacionesTareasProgramadasNegocio vacacionesTareasProgramadasService;

	@Autowired
	private CargaParametros cargaParametros;

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private VacacionMetaService vacacionMetaService;

	@Autowired
	private ProgramacionVacacionNegocio programacionVacacionNegocio;

	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;

	@Autowired
	private VacacionAprobadorService vacacionAprobadorService;

	@Autowired
	private NotificacionService notificacionService;

	@Scheduled(cron = "${vacaciones.programacion.actualizaciones}")
	public void actualizacionesVacaciones() {
		actualizarEstadoProgramaciones();
		actualizarPeridos();
		calcularMetaAnual();
		vacacionesAutomaticas();
		notificacionVacacionesPorAprobar();
	}

	@Scheduled(cron = "0 0/50 * * * ?") // Verificacion cada 50 minutos
	public void enviarNotificacionesVacaciones() {
		LOGGER.info("[BEGIN] enviarNotificacionesVacaciones " + LocalDateTime.now());
		LocalDateTime ahora = LocalDateTime.now();
		int horaEnvioNotificacion = cargaParametros.getHoraEnvioNotificacionVacaciones();
		if (ahora.getHour() == horaEnvioNotificacion) {
			vacacionesTareasProgramadasService.registrarNotificacionesAutomaticas();
			vacacionesTareasProgramadasService.enviarNotificacionesAppPendienteVacaciones();
			vacacionesTareasProgramadasService.enviarNotificacionesCorreoPendienteVacaciones();
		}
		LOGGER.info("[END] enviarNotificacionesVacaciones " + LocalDateTime.now());
	}

	@Transactional(dontRollbackOn = AppException.class)
	public void vacacionesAutomaticas() {
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaGeneracionAutomatica = cargaParametros.getFechaMaximaAprobacionProgramaciones().plusDays(1);
		if (fechaActual.getMonthValue() != fechaGeneracionAutomatica.getMonthValue()
				&& fechaActual.getDayOfMonth() != fechaGeneracionAutomatica.getDayOfMonth())
			throw new AppException("La fecha generación auntomática es " + fechaGeneracionAutomatica);

		// Empleado empleado = empleadoService.buscarPorUsuarioBT("TRNAT001");
		List<Empleado> empleados = empleadoService.listar();
		for (Empleado empleado : empleados) {
			VacacionMeta vacacionMeta = vacacionMetaService
					.obtenerVacacionPorAnio(cargaParametros.getAnioPresente() + 1, empleado.getId());
			if (Objects.isNull(vacacionMeta))
				throw new AppException("Empleado sin meta asignada " + empleado.getId());

			if (vacacionMeta.getMeta() > 0) {
				LocalDate ahora = LocalDate.now();
				int count = 0;
				while (vacacionMeta.getMeta() > 0) {
					LocalDate fechaInicio = ahora.plusDays(count);
					LocalDate fechaFin = vacacionMeta.getMeta() < 8
							? fechaInicio.plusDays((int) vacacionMeta.getMeta() - 1)
							: fechaInicio.plusDays(7);
					;
					RequestProgramacionVacacion programacion = new RequestProgramacionVacacion();
					programacion.setUsuarioBT(empleado.getUsuarioBT());
					programacion.setFechaInicio(fechaInicio);
					programacion.setFechaFin(fechaFin);
					programacion.setUsuarioOperacion("GENERACION_AUTOMATICA");
					try {
						List<ResponseProgramacionVacacion> listProgramacion = programacionVacacionNegocio
								.registroAutomatico(programacion);
						vacacionMeta = vacacionMetaService.obtenerVacacionPorAnio(cargaParametros.getAnioPresente() + 1,
								empleado.getId());
						count++;
					} catch (Exception e) {
						e.printStackTrace();
						count++;
					}
				}
			}
		}

	}

	private void notificacionVacacionesPorAprobar() {

		List<VacacionAprobadorNivelI> listAprobadorNivel = vacacionAprobadorService.listarAprobadoresNivelI();
		List<VacacionAprobadorNivelII> listAprobadorNivelII = vacacionAprobadorService.listarAprobadoresNivelII();

		listAprobadorNivelII.stream().forEach(v -> {
			VacacionAprobadorNivelI aprobNivelI = new VacacionAprobadorNivelI();
			aprobNivelI.setUsuariobt(v.getUsuariobt());
			aprobNivelI.setCodigo(v.getCodigo());
			listAprobadorNivel.add(aprobNivelI);
		});

		listAprobadorNivel.stream().forEach(aprobador -> {
			RequestProgramacionEmpleado req = new RequestProgramacionEmpleado();
			req.setUsuarioBt(aprobador.getUsuariobt());

			List<EmplVacPerRes> listVac = vacacionProgramacionService.listEmpleadoProgramacionFilter(req);

			if (listVac.size() > 0) {
				Empleado emp = empleadoService.buscarPorCodigo(aprobador.getCodigo());
				Optional<NotificacionTipo> tipoNot = notificacionService
						.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_APROBADOR.valor);
				Notificacion notificacion = notificacionService.registrar("Pendiente vacaciones por aprobar",
						"Cuenta con " + listVac.size() + " vacaciones por aprobar", aprobador.getUsuariobt(),
						tipoNot.get(), emp, emp.getUsuarioBT());
				notificacionService.enviarNotificacionApp(notificacion);
				notificacionService.enviarNotificacionCorreo(notificacion);
			}
		});

	}

	private void actualizarEstadoProgramaciones() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDateTime.now());
		vacacionesTareasProgramadasService.actualizarEstadoProgramaciones();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDateTime.now());
	}

	private void actualizarPeridos() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDateTime.now());
		vacacionesTareasProgramadasService.actualizarPeriodos();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDateTime.now());
	}

	private void calcularMetaAnual() {
		LOGGER.info("[BEGIN] calcularMetaAnual " + LocalDateTime.now());
		vacacionesTareasProgramadasService.consolidarMetasAnuales(true);
		LOGGER.info("[END] calcularMetaAnual " + LocalDateTime.now());
	}

}