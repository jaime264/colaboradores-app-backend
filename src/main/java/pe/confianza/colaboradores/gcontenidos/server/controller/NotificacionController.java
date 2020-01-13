package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.Notification;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotification;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445" })
public class NotificacionController {

	@Value("${gcontenido.notificacion.url}")
	private String urlNotificacion;
	
	@Value("${clave.basic.auth}")
	private String authorizationKey;

	@Autowired
	private NotificacionService notificacionService;

	@PostMapping("/notificacion/{id}")
	public ResponseEntity<?> notificarPush(@PathVariable("id") Long idPublicacion,
			@RequestBody RequestNotification requestNotification) throws IOException {

		ResponseStatus responseStatus = null;
		Notification notificacion = notificacionService.obtenerBodyNotificacion(idPublicacion, requestNotification);

		System.out.println("Notificación Body: " + new Gson().toJson(notificacion));
		StringBuilder url = new StringBuilder(urlNotificacion);
		URL urlForPostRequest = new URL(url.toString());

		HttpURLConnection conection = (HttpURLConnection) urlForPostRequest.openConnection();
		conection.setDoOutput(true);
		conection.setRequestMethod("POST");
		conection.setRequestProperty("Content-Type", "application/json");
		conection.setRequestProperty("Authorization", "Basic " + authorizationKey);
		conection.setRequestProperty("cache-control", "no-cache");
		
		OutputStream os = conection.getOutputStream();

		os.write(new Gson().toJson(notificacion).getBytes());
		os.flush();
		os.close();

		int responseCode = conection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer responsePrint = new StringBuffer();
			String readLine = "";
			
			while ((readLine = in.readLine()) != null) {
				responsePrint.append(readLine);
			}
			in.close();
			String bodyResponse = responsePrint.toString();
			if (notificacion != null) {
				responseStatus = new ResponseStatus();
				responseStatus.setCodeStatus(200);
				responseStatus.setMsgStatus("Correcto");
				responseStatus.setResultObj(bodyResponse);
			} else {
				responseStatus = new ResponseStatus();
				responseStatus.setMsgStatus("Incorrecto");
			}
		}else {
			responseStatus = new ResponseStatus();
			responseStatus.setMsgStatus("Incorrecto");
		}

		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}
}
