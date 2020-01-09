package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.BsonDocument;
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
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.PublicacionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://200.107.154.52:6020","http://localhost","http://localhost:8100","http://localhost:4200"})
public class PublicacionController {
	
	@Autowired
	private PublicacionService postService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@PostMapping("/publicacion/list")
	public ResponseEntity<?> show() {
		List<Publicacion> lstPosts = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			lstPosts = postService.listPost();
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstPosts == null) {
			response.put("mensaje", "Publicaciones no existe en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Publicacion>>(lstPosts, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/user")
	public ResponseEntity<?> listPostsUser(@RequestBody Usuario user) {
		List<Publicacion> lstPosts = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			lstPosts = postService.listPostUser(user.getUsuarioBT(), user.getUltimaPublicacion());
			Gson gson = new Gson();
			String jsonData = gson.toJson(user);
			auditoriaService.createAuditoria("002", "008", 0, BsonDocument.parse(jsonData));
		} catch(DataAccessException e) {
			Gson gson = new Gson();
			String jsonData = gson.toJson(user);
			auditoriaService.createAuditoria("002", "008", 99, BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(lstPosts == null) {
			response.put("mensaje", "Publicaciones no existen en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Publicacion>>(lstPosts, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/id")
	public ResponseEntity<?> postId(@RequestBody ParamsPublicacion paramsPublicacion) {
		Optional<Publicacion> publicacion = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			publicacion = postService.findByIdPostUser(paramsPublicacion.getIdPost(), paramsPublicacion.getUser());
			Gson gson = new Gson();
			String jsonData = gson.toJson(paramsPublicacion);
			auditoriaService.createAuditoria("002", "008", 0, BsonDocument.parse(jsonData));
		} catch(DataAccessException e) {
			Gson gson = new Gson();
			String jsonData = gson.toJson(paramsPublicacion);
			auditoriaService.createAuditoria("002", "008", 99, BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(publicacion == null) {
			response.put("mensaje", "Publicacion no existe en la base de datos!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Optional<Publicacion>>(publicacion, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/add")
	public ResponseEntity<?> addPost(@RequestBody Publicacion publicacion) {
		ResponseStatus responseStatus = null;
		List<Publicacion> posts = new ArrayList<Publicacion>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			posts.add(publicacion);
			responseStatus = postService.createPost(posts);
			Gson gson = new Gson();
			String jsonData = gson.toJson(publicacion);
			auditoriaService.createAuditoria("001", "003", 0, BsonDocument.parse(jsonData));
		} catch(DataAccessException e) {
			Gson gson = new Gson();
			String jsonData = gson.toJson(publicacion);
			auditoriaService.createAuditoria("001", "003", 99, BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al registrar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}
	
	@PostMapping("/publicacion/reaccion")
	public ResponseEntity<?> addReaccion(@RequestBody ParamsReaccion paramsReaccion) {
		ResponseStatus responseStatus = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			responseStatus = postService.addReaccion(paramsReaccion);
			Gson gson = new Gson();
			String jsonData = gson.toJson(paramsReaccion);
			auditoriaService.createAuditoria("002", "004", 0, BsonDocument.parse(jsonData));
		} catch(DataAccessException e) {
			Gson gson = new Gson();
			String jsonData = gson.toJson(paramsReaccion);
			auditoriaService.createAuditoria("002", "004", 99, BsonDocument.parse(jsonData));
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}

}
