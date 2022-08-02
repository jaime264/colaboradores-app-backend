package pe.confianza.colaboradores.gcontenidos.server.tareas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.VacacionProgramacionDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMeta;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;

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
	ProgramacionVacacionNegocio programacionVacacionNegocio;

	@Autowired
	VacacionProgramacionDao vacacionProgramacionDao;

	@Scheduled(cron = "${vacaciones.programacion.actualizaciones}")
	public void actualizacionesVacaciones() {
		actualizarEstadoProgramaciones();
		actualizarPeridos();
		calcularMetaAnual();
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

	// @Scheduled(cron = "0 0 24 13 12 ?") // 24 horas del 13 de diciembre
	@Transactional(dontRollbackOn = AppException.class)
	public void vacacionesAutomaticas() {

		Empleado empleado = empleadoService.buscarPorUsuarioBT("TRNAT001");
		VacacionMeta vacacionMeta = vacacionMetaService.obtenerVacacionPorAnio(cargaParametros.getAnioPresente() + 1,
				empleado.getId());

		if (vacacionMeta.getMeta() > 0) {
			LocalDate ahora = LocalDate.now();
			int count = 0;
			while (vacacionMeta.getMeta() > 0) {

				LocalDate fechaInicio = ahora.plusDays(count);
				LocalDate fechaFin = vacacionMeta.getMeta() < 8 ? fechaInicio.plusDays((int) vacacionMeta.getMeta() - 1)
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
					count++;

					vacacionMeta = vacacionMetaService.obtenerVacacionPorAnio(cargaParametros.getAnioPresente() + 1,
							empleado.getId());

				}

				catch (Exception e) {
					e.printStackTrace();
					count++;
				}

			}
		}

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