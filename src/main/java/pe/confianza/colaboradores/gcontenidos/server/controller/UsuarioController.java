package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestStatus;
import pe.confianza.colaboradores.gcontenidos.server.dao.UsuarioDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Usuario;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445" })
public class UsuarioController {

	
	@Autowired
	private AuditoriaService auditoriaService;	
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@PostMapping("/login/success")
	public ResponseEntity<?> loginSucess(@RequestBody RequestStatus requestStatus) throws IOException {
		Gson gson = new Gson();
		String jsonData = gson.toJson(requestStatus);
		auditoriaService.createAuditoria("001", "001", 0, "OK", BsonDocument.parse(jsonData));
		return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);
	}
	
	@PostMapping("/login/error")
	public ResponseEntity<?> loginError(@RequestBody RequestStatus requestStatus) throws IOException {
		Gson gson = new Gson();
		String jsonData = gson.toJson(requestStatus);
		auditoriaService.createAuditoria("001", "001", 99, "NOT OK", BsonDocument.parse(jsonData));
		return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);

	}
	
	@PostMapping("/logout/success")
	public ResponseEntity<?> logOut(@RequestBody RequestStatus requestStatus) throws IOException {
		Gson gson = new Gson();
		String jsonData = gson.toJson(requestStatus);
		auditoriaService.createAuditoria("001", "012", 0, "OK", BsonDocument.parse(jsonData));
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
