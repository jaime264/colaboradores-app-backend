package pe.confianza.colaboradores.gcontenidos.server.tareas;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;

@Component
@PropertySource("classpath:tareas-programadas.properties")
public class VacacionesTareas {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionesTareas.class);
	
	@Autowired
	private VacacionesTareasProgramadasNegocio vacacionesTareasProgramadasService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Scheduled(cron = "${vacaciones.programacion.actualizaciones}")
	public void actualizacionesVacaciones() {
		actualizarEstadoProgramaciones();
		actualizarPeridos();
		calcularMetaAnual();
	}
	
	@Scheduled(cron = "0 0/3 * * * ?") // Verificacion cada 50  minutos
	public void enviarNotificacionesVacaciones() {
		LOGGER.info("[BEGIN] enviarNotificacionesVacaciones " + LocalDateTime.now());
		LocalDateTime ahora = LocalDateTime.now();
		int horaEnvioNotificacion = cargaParametros.getHoraEnvioNotificacionVacaciones();
		if(ahora.getHour() == horaEnvioNotificacion) {
			vacacionesTareasProgramadasService.registrarNotificacionesAutomaticas();
			vacacionesTareasProgramadasService.enviarNotificacionesAppPendienteVacaciones();
			vacacionesTareasProgramadasService.enviarNotificacionesCorreoPendienteVacaciones();
		}
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
	
}