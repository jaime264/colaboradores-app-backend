package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.EnvioNotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;

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
		Notificacion not = notificacionService.obtenerPorId(notificacion.getId());
		if(not != null) 
			notificacionService.enviarNotificacionCorreo(not);		
	}

	@Override
	public void enviarNotificacionApp(Notificacion notificacion) {
		Notificacion not = notificacionService.obtenerPorId(notificacion.getId());
		if(not != null)
			notificacionService.enviarNotificacionApp(not);
	}

	@Override
	public void enviarNotificacionesCorreo(TipoNotificacion tipoNotificacion) {
		List<Notificacion> notificaciones = notificacionService.listarNotificacionesPorTipoNoEnviadasCorreo(tipoNotificacion.valor);
		for (Notificacion notificacion : notificaciones) {
			enviarNotificacionApp(notificacion);
		}
	}

	@Override
	public void enviarNotificacionesApp(TipoNotificacion tipoNotificacion) {
		List<Notificacion> notificaciones = notificacionService.listarNotificacionesPorTipoNoEnviadasApp(tipoNotificacion.valor);
		for (Notificacion notificacion : notificaciones) {
			enviarNotificacionApp(notificacion);
		}
	}

}
