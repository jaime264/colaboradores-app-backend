package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestStatus;
import pe.confianza.colaboradores.gcontenidos.server.bean.bt.RequestVersion;
import pe.confianza.colaboradores.gcontenidos.server.bean.bt.ResponseJsonVersion;
import pe.confianza.colaboradores.gcontenidos.server.dao.UsuarioDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Usuario;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class UsuarioController {


	@Value("${rest.version.bt}")
	private String versionBTUrl;
	
	@Autowired
	private AuditoriaService auditoriaService;	
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@PostMapping("/login/bt")
	public ResponseEntity<?> loginBT(@RequestBody RequestVersion requestVersion) throws IOException {

		HttpPost post = new HttpPost(versionBTUrl);
		post.addHeader("content-type", "application/json");

		Gson gson = new Gson();
		StringEntity params = new StringEntity(gson.toJson(requestVersion));
		post.setEntity(params);

		ResponseJsonVersion responseVersion = null;
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				responseVersion = gson.fromJson(result, ResponseJsonVersion.class);
				String jsonData = gson.toJson(requestVersion);
				auditoriaService.createAuditoria("002", "017", 0, "OK", BsonDocument.parse(jsonData));
			} else {
				String jsonData = gson.toJson(requestVersion);
				auditoriaService.createAuditoria("002", "017", 99, "Error al obtener datos de versión", BsonDocument.parse(jsonData));
			}
		}
		return new ResponseEntity<ResponseJsonVersion>(responseVersion, HttpStatus.OK);
	}
	
	@PostMapping("/login/success")
	public ResponseEntity<?> loginSucess(@RequestBody RequestStatus requestStatus) throws IOException {
		Gson gson = new Gson();
		String jsonData = gson.toJson(requestStatus);
		auditoriaService.createAuditoria("002", "002", 0, "OK", BsonDocument.parse(jsonData));
		return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);
	}
	
	@PostMapping("/login/error")
	public ResponseEntity<?> loginError(@RequestBody RequestStatus requestStatus) throws IOException {
		Gson gson = new Gson();
		String jsonData = gson.toJson(requestStatus);
		auditoriaService.createAuditoria("002", "002", 99, "NOT OK", BsonDocument.parse(jsonData));
		return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);

	}
	
	@PostMapping("/logout/success")
	public ResponseEntity<?> logOut(@RequestBody RequestStatus requestStatus) throws IOException {
		Gson gson = new Gson();
		String jsonData = gson.toJson(requestStatus);
		auditoriaService.createAuditoria("002", "014", 0, "OK", BsonDocument.parse(jsonData));
		return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);

	}
	
	@PostMapping("/login/permission")
	public ResponseEntity<?> permission(@RequestBody  Usuario usuarioLogIn) throws IOException {

		Map<String, Object> response = new HashMap<>();
		Usuario usuarioOut = usuarioDao.findUsuarioByUsuarioBTAndAplicacion(usuarioLogIn.getUsuarioBT(), usuarioLogIn.getAplicacion());

		if(usuarioOut == null) {
			response.put("mensaje", "Usuario no existe en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Usuario>(usuarioOut, HttpStatus.OK);
	}
}
