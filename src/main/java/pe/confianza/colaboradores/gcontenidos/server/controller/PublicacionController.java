package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsPublicacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.bean.Usuario;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionOld;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.PublicacionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class PublicacionController {
	
	private static Logger logger = LoggerFactory.getLogger(PublicacionController.class);
	
	@Autowired
	private PublicacionService postService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@PostMapping("/publicacion/list")
	public ResponseEntity<?> show() {
		List<PublicacionOld> lstPosts = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		
		try {
			lstPosts = postService.listPost();
		} catch(DataAccessException e) {
			logger.error("Error al consultas las publicaciones: " + e.getMessage());
			String jsonData = gson.toJson("{}");
			auditoriaService.createAuditoria("002", "008", 99,  e.getMessage(), BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstPosts == null) {
			String mensaje = "Publicaciones no existen en la base de datos: null";
			String jsonData = gson.toJson("{}");
			auditoriaService.createAuditoria("002", "008", 99, mensaje, BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} /*else {
			String jsonData = gson.toJson("{}");
			auditoriaService.createAuditoria("002", "008", 0, "OK", BsonDocument.parse(jsonData));
		}*/
		
		return new ResponseEntity<List<PublicacionOld>>(lstPosts, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/user")
	public ResponseEntity<?> listPostsUser(@RequestBody Usuario user) {
		List<PublicacionOld> lstPosts = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		logger.info("Usuario consulta: " + user.getUsuarioBT());
		try {
			lstPosts = postService.listPostUser(user.getUsuarioBT(), user.getUltimaPublicacion(), user.getPaginacion(), user.getHistoria());
		} catch(DataAccessException e) {
			logger.error("Error al consultar las publicaciones: " + e.getMessage());
			String jsonData = gson.toJson(user);
			auditoriaService.createAuditoria("002", "008", 99,  e.getMessage(), BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstPosts == null) {
			String mensaje = "Publicaciones no existen en la base de datos: null";
			logger.error(mensaje);
			String jsonData = gson.toJson(user);
			auditoriaService.createAuditoria("002", "008", 99, mensaje, BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			String jsonData = gson.toJson(user);
			auditoriaService.createAuditoria("002", "008", 0, "OK", BsonDocument.parse(jsonData));
		}
		
		return new ResponseEntity<List<PublicacionOld>>(lstPosts, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/id")
	public ResponseEntity<?> postId(@RequestBody ParamsPublicacion paramsPublicacion) {
		Optional<PublicacionOld> publicacion = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		logger.info("IdPublicación consulta: " + paramsPublicacion.getIdPost());
		try {
			publicacion = postService.findByIdPostUser(paramsPublicacion.getIdPost(), paramsPublicacion.getUser());
		} catch(Exception e) {
			logger.error("Error al consultar publicacion: " + e.getMessage());
			String jsonData = gson.toJson(paramsPublicacion);
			auditoriaService.createAuditoria("002", "008", 99, e.getMessage(), BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al consultar publicación");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(publicacion == null || !publicacion.isPresent()) {
			String mensaje = "Publicacion no existe en la base de datos: null";
			logger.error(mensaje);
			String jsonData = gson.toJson(paramsPublicacion);
			auditoriaService.createAuditoria("002", "008", 99, mensaje, BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			String jsonData = gson.toJson(paramsPublicacion);
			auditoriaService.createAuditoria("002", "008", 0, "OK", BsonDocument.parse(jsonData));

		}
		
		return new ResponseEntity<Optional<PublicacionOld>>(publicacion, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/add")
	public ResponseEntity<?> addPost(@RequestBody PublicacionOld publicacion) {
		ResponseStatus responseStatus = null;
		List<PublicacionOld> posts = new ArrayList<PublicacionOld>();
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		logger.info("IdPublicación registro: " + publicacion.getId());
		try {
			posts.add(publicacion);
			responseStatus = postService.createPost(posts);
			if (responseStatus.getCodeStatus() == 0) {
				String jsonData = gson.toJson(publicacion);
				auditoriaService.createAuditoria("001", "003", 0, "OK", BsonDocument.parse(jsonData));
			} else {
				logger.error(responseStatus.getMsgStatus());
				String jsonData = gson.toJson(publicacion);
				auditoriaService.createAuditoria("001", "003", 99, responseStatus.getMsgStatus(), BsonDocument.parse(jsonData));
			}
		} catch(Exception e) {
			logger.error("Error al registrar publicación: " + e.getMessage());
			String mensaje = "Error al registrar publicación";
			String jsonData = gson.toJson(publicacion);
			auditoriaService.createAuditoria("001", "003", 99, mensaje + ": " + e.getMessage(), BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/reaccion")
	public ResponseEntity<?> addReaccion(@RequestBody ParamsReaccion paramsReaccion) {
		ResponseStatus responseStatus = null;
		Map<String, Object> response = new HashMap<>();
		Gson gson = new Gson();
		logger.info("Reacción IdPublicación: " + paramsReaccion.getIdPublicacion());
		try {
			responseStatus = postService.addReaccion(paramsReaccion);
			if (responseStatus.getCodeStatus() == 0) {
				String jsonData = gson.toJson(paramsReaccion);
				auditoriaService.createAuditoria("002", "004", 0, "OK", BsonDocument.parse(jsonData));
			} else {
				logger.error(responseStatus.getMsgStatus());
				String jsonData = gson.toJson(paramsReaccion);
				auditoriaService.createAuditoria("002", "004", 99, responseStatus.getMsgStatus(), BsonDocument.parse(jsonData));
			}
		} catch(Exception e) {
			String mensaje = "Error al actualizar la reacción";
			logger.error(mensaje);
			String jsonData = gson.toJson(paramsReaccion);
			auditoriaService.createAuditoria("002", "004", 99, mensaje + ": " + e.getMessage(), BsonDocument.parse(jsonData));
			response.put("mensaje", mensaje);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}

}
