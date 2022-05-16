package pe.confianza.colaboradores.gcontenidos.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFirebaseMessaging;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.util.FirebaseCloudMessagingClient;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {
	
	@PostMapping("/web")
	public ResponseEntity<ResponseStatus> notificacionFirebase(@RequestBody RequestFirebaseMessaging request) {
		ResponseStatus responseStatus = new ResponseStatus();
		boolean sendMessage = FirebaseCloudMessagingClient.sendMessageToWeb(request);
		if(sendMessage) {
			responseStatus.setCodeStatus(200);
			responseStatus.setMsgStatus("Correcto");
		} else {
			responseStatus = new ResponseStatus();
			responseStatus.setMsgStatus("Incorrecto");
		}
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

}
