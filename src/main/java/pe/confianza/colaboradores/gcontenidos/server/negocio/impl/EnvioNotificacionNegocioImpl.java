package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.EnvioNotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;

@Service
public class EnvioNotificacionNegocioImpl implements EnvioNotificacionNegocio {
	
	@Autowired
	private NotificacionService  notificacionService;

	@Override
	public void enviarNotificacionesCorreo() {
		List<Notificacion> notificacionesPendientes = notificacionService.listarNotificacionesNoEnviadasCorreo();
		for (Notificacion notificacion : notificacionesPendientes) {
			enviarNotificacionCorreo(notificacion);
		}
	}

	@Override
	public void enviarNotificacionesApp() {
		List<Notificacion> notificacionesPendientes = notificacionService.listarNotificacionesNoEnviadasApp();
		for (Notificacion notificacion : notificacionesPendientes) {
			enviarNotificacionApp(notificacion);
		}
	}

	@Override
	public void enviarNotificacionesCorreo(List<Notificacion> notificaciones) {
		for (Notificacion notificacion : notificaciones) {
			enviarNotificacionCorreo(notificacion);
		}
		
	}

	@Override
	public void enviarNotificacionesApp(List<Notificacion> notificaciones) {
		for (Notificacion notificacion : notificaciones) {
			enviarNotificacionApp(notificacion);
		}		
	}

	@Override
	public void enviarNotificacionCorreo(Notificacion notificacion) {
		notificacionService.enviarNotificacionCorreo(notificacion);
		
	}

	@Override
	public void enviarNotificacionApp(Notificacion notificacion) {
		notificacionService.enviarNotificacionApp(notificacion);
	}

	

}
