package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;

public interface EnvioNotificacionNegocio {
	
	/**
	 * Envia notificaciones pendientes
	 */
	void enviarNotificacionesCorreo();
	
	/**
	 * Envia notificaciones pendientes
	 */
	void enviarNotificacionesApp();
	
	void enviarNotificacionCorreo(Notificacion notificacion);
	
	void enviarNotificacionApp(Notificacion notificacion);
	
	void enviarNotificacionesCorreo(List<Notificacion> notificaciones);
	
	void enviarNotificacionesApp(List<Notificacion> notificaciones);

}
