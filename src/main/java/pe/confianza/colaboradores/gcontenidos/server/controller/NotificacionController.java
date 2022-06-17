package pe.confianza.colaboradores.gcontenidos.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.RequestListarNotificaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFirebaseMessaging;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotificacionVista;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.negocio.NotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;
import pe.confianza.colaboradores.gcontenidos.server.util.FirebaseCloudMessagingClient;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {
	
	@Autowired
	private NotificacionNegocio notificacionNegocio;
	
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
	
	@ApiOperation(notes = "Consulta los tipos de notificaciones", value = "url proxy /notificaciontipolist")
	@PostMapping("/tipos")
	public ResponseEntity<ResponseStatus> listarTipoNotificaciones() {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(notificacionNegocio.consultarTipos());
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Consulta las notificaciones", value = "url proxy /notificacionconsulta")
	@PostMapping("/consultar")
	public ResponseEntity<ResponseStatus> consultar(@RequestBody RequestListarNotificaciones request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(notificacionNegocio.listarPorTipo(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Actualiza una notificacion a vista", value = "url proxy /notificacionvista")
	@PutMapping("/actualizar-visto")
	public ResponseEntity<ResponseStatus> actualizarVisto(@RequestBody RequestNotificacionVista request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(notificacionNegocio.actualizarVisto(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	

}
