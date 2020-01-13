package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.bean.Notification;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotification;

public interface NotificacionService {

	Notification obtenerBodyNotificacion(Long idPublicacion, RequestNotification requestNotification);
}
