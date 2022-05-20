package pe.confianza.colaboradores.gcontenidos.server.api.onesignal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.api.onesignal.dto.RequestNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.Heading;
import pe.confianza.colaboradores.gcontenidos.server.bean.Notification;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao.DispositivoDao;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Dispositivo;

@Component
public class OneSignalCliente {
	
	private static Logger logger = LoggerFactory.getLogger(OneSignalCliente.class);
	
	@Value("${gcontenido.notificacion.url}")
	private String urlNotificacion;
	
	@Value("${clave.basic.auth}")
	private String authorizationKey;
	
	@Value("${app.id}")
	private String appId;
	
	@Autowired 
	private DispositivoDao dispositivoDao;
	
	public void generarNotificacion(RequestNotificacion request) {
		logger.info("[INICIO] generarNotificacion");
		List<Dispositivo> dispositivos = dispositivoDao.findByIdDeviceUser(request.getUsuarios());
		List<String> idsDispositivos = dispositivos.stream().map( dispositivo -> {
			return dispositivo.getIdDispositivo();
		}).collect(Collectors.toList());
		
		Heading titulo = new Heading();
		titulo.setEs(request.getTitulo());
		titulo.setEn(request.getTitulo());
		Heading detalle = new Heading();
		detalle.setEs(request.getDetalle());
		detalle.setEn(request.getDetalle());		
		
		Notification notification = new Notification();
		notification.setApp_id(appId);
		notification.setInclude_player_ids((String[]) idsDispositivos.toArray());
		notification.setHeadings(titulo);
		notification.setContents(detalle);
		
		if(request.getData() != null) {
			notification.setData(request.getData());
		}
		
		try {
			StringBuilder url = new StringBuilder(urlNotificacion);
			URL urlForPostRequest = new URL(url.toString());
			
			HttpURLConnection conection = (HttpURLConnection) urlForPostRequest.openConnection();
			conection.setDoOutput(true);
			conection.setRequestMethod("POST");
			conection.setRequestProperty("Content-Type", "application/json");
			conection.setRequestProperty("Authorization", "Basic " + authorizationKey);
			conection.setRequestProperty("cache-control", "no-cache");
			
			OutputStream os = conection.getOutputStream();

			os.write(new Gson().toJson(notification).getBytes());
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
				logger.info("[RESPONSE] {}", new Object[] {responsePrint.toString()});
			}
		} catch (Exception e) {
			logger.error("[ERROR] generarNotificacion", e);
			throw new AppException("Error en enviar notificación", e);
		}
		

	}

}
