package pe.confianza.colaboradores.gcontenidos.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.Data;
import pe.confianza.colaboradores.gcontenidos.server.bean.Heading;
import pe.confianza.colaboradores.gcontenidos.server.bean.Notification;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotification;

@Service
public class NotificacionServiceImpl implements NotificacionService {

	@Value("${app.id}")
	private String appId;

	@Override
	public Notification obtenerBodyNotificacion(Long idPublicacion, RequestNotification requestNotification) {
//		String[] includedSegments = { "Active Users", "Inactive Users" };

		Heading heading = null;
		Heading content = null;

		for (Heading op : requestNotification.getHeadings()) {
			if (heading == null) {
				heading = new Heading();
				heading.setEn(op.getEn());
				heading.setEs(op.getEs());
			} else if (content == null) {
				content = new Heading();
				content.setEn(op.getEn());
				content.setEs(op.getEs());
			}
		}

		Notification notification = new Notification();
		notification.setApp_id(appId);
//		notification.setIncluded_segments(includedSegments);
		notification.setInclude_player_ids(requestNotification.getInclude_player_ids());
		notification.setHeadings(heading);
		notification.setContents(content);
		
		Data data =  new Data();
		data.setId(idPublicacion);
		notification.setData(data);

		return notification;
	}

}
