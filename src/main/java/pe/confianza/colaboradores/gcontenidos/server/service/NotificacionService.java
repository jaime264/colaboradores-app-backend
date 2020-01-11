package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.Heading;
import pe.confianza.colaboradores.gcontenidos.server.bean.Notification;

public interface NotificacionService {

	Notification obtenerBodyNotificacion(Long idPublicacion, List<Heading> headings);
}
