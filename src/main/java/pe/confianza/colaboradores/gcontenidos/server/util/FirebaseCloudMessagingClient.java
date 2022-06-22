package pe.confianza.colaboradores.gcontenidos.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFirebaseMessaging;

public class FirebaseCloudMessagingClient {
	
	private static Logger logger = LoggerFactory.getLogger(FirebaseCloudMessagingClient.class);
	
	public static boolean sendMessageToWeb(RequestFirebaseMessaging request) {
		logger.info("[BEGIN] sendMessage");
		boolean executed = false;
		try {
			
			MulticastMessage message = MulticastMessage.builder()
					.setWebpushConfig(WebpushConfig.builder()
				            .setNotification(new WebpushNotification(
				            		request.getData().getTitle(),
					                request.getData().getBody(),
					                request.getData().getIcon()))
					            .build())
				.putAllData(request.getData().getExtra())
				.addAllTokens(request.getTokens())
			    .build();
		
			BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
			 
			logger.info("Firebase response: " + response.getSuccessCount());
			executed = true;
		} catch (Exception e) {
			logger.error("[ERROR] sendMessage", e);
			executed = false;
		}
		logger.info("[END] sendMessage");
		return executed;

	}
	
	

}
