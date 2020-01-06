package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Usuario;
import pe.confianza.colaboradores.gcontenidos.server.service.PublicacionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://200.107.154.52:6020","http://localhost","http://localhost:8100","http://localhost:4200"})
public class PublicacionController {
	
	@Autowired
	private PublicacionService postService;
	
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
		} catch(DataAccessException e) {
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
	
	@PostMapping("/publicacion/add")
	public ResponseEntity<?> addPost(@RequestBody Publicacion publicacion) {
		ResponseStatus responseStatus = null;
		List<Publicacion> posts = new ArrayList<Publicacion>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			posts.add(publicacion);
			responseStatus = postService.createPost(posts); 
		} catch(DataAccessException e) {
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
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatus.OK);
	}

}
