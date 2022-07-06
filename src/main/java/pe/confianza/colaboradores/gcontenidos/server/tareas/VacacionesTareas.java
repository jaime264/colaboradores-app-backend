package pe.confianza.colaboradores.gcontenidos.server.tareas;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;

@Component
@PropertySource("classpath:tareas-programadas.properties")
public class VacacionesTareas {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionesTareas.class);
	
	@Autowired
	private VacacionesTareasProgramadasNegocio vacacionesTareasProgramadasService;
	
	@Scheduled(cron = "${vacaciones.programacion.actualizacion_estado}")
	public void actualizarEstadoProgramaciones() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDate.now());
		vacacionesTareasProgramadasService.actualizarEstadoProgramaciones();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDate.now());
	}
	
	@Scheduled(cron = "${vacaciones.programacion.actualizacion_periodos}")
	public void actualizarPeridos() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDate.now());
		vacacionesTareasProgramadasService.actualizarPeriodos();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDate.now());
	}
	
	@Scheduled(cron = "${vacaciones.programacion.consolidar_metas_anuales}")
	public void calcularMetaAnual() {
		LOGGER.info("[BEGIN] calcularMetaAnual " + LocalDate.now());
		vacacionesTareasProgramadasService.consolidarMetasAnuales();
		LOGGER.info("[END] calcularMetaAnual " + LocalDate.now());
	}
	
	@Scheduled(cron = "${vacaciones.programacion.registro_notificaciones}")
	public void registrarNotificacionesVacaciones() {
		LOGGER.info("[BEGIN] registrarNotificacionesVacaciones " + LocalDate.now());
		vacacionesTareasProgramadasService.registroNotificacionesInicioRegistroProgramacion();
		vacacionesTareasProgramadasService.registroNotificacionesMetaNoCumplida();
		vacacionesTareasProgramadasService.registroNotificacionesSinRegistroProgramacion();
		vacacionesTareasProgramadasService.registroNotificacionesJefeColaboradoresSinRegistroProgramacion();
		vacacionesTareasProgramadasService.registroNotificacionJefePendienteAprobacionProgramacion();
		LOGGER.info("[END] registrarNotificacionesVacaciones " + LocalDate.now());
	}
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void enviarNotificacionesVacaciones() {
		LOGGER.info("[BEGIN] enviarNotificacionesVacaciones " + LocalDate.now());
		vacacionesTareasProgramadasService.enviarNotificacionesAppPendienteVacaciones();
		vacacionesTareasProgramadasService.enviarNotificacionesCorreoPendienteVacaciones();
		LOGGER.info("[END] enviarNotificacionesVacaciones " + LocalDate.now());
	}
	
}