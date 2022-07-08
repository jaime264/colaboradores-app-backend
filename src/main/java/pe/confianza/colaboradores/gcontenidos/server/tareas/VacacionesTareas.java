package pe.confianza.colaboradores.gcontenidos.server.tareas;

import java.time.LocalDateTime;

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
	
	@Scheduled(cron = "${vacaciones.programacion.actualizaciones}")
	public void actualizacionesVacaciones() {
		actualizarEstadoProgramaciones();
		actualizarPeridos();
		calcularMetaAnual();
		registrarNotificacionesVacaciones();
	}
	
	@Scheduled(cron = "0 0/5 * * * ?") // Verificacion cada 5 minutos
	public void enviarNotificacionesVacaciones() {
		LOGGER.info("[BEGIN] enviarNotificacionesVacaciones " + LocalDateTime.now());
		vacacionesTareasProgramadasService.enviarNotificacionesAppPendienteVacaciones();
		vacacionesTareasProgramadasService.enviarNotificacionesCorreoPendienteVacaciones();
		LOGGER.info("[END] enviarNotificacionesVacaciones " + LocalDateTime.now());
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
	

	private void registrarNotificacionesVacaciones() {
		LOGGER.info("[BEGIN] registrarNotificacionesVacaciones " + LocalDateTime.now());
		vacacionesTareasProgramadasService.registroNotificacionesInicioRegistroProgramacion();
		vacacionesTareasProgramadasService.registroNotificacionesMetaNoCumplida();
		vacacionesTareasProgramadasService.registroNotificacionesSinRegistroProgramacion();
		vacacionesTareasProgramadasService.registroNotificacionesJefeColaboradoresSinRegistroProgramacion();
		vacacionesTareasProgramadasService.registroNotificacionJefePendienteAprobacionProgramacion();
		vacacionesTareasProgramadasService.registrarNotificacionesReprogramacionMensual();
		LOGGER.info("[END] registrarNotificacionesVacaciones " + LocalDateTime.now());
	}
	
}