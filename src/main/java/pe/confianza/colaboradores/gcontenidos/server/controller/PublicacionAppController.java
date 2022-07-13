package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPublicacionGestorContenido;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Reaccion;
import pe.confianza.colaboradores.gcontenidos.server.service.PublicacionAppService;

@RestController
@RequestMapping("/api")
public class PublicacionAppController {
	
	private static Logger logger = LoggerFactory.getLogger(PublicacionController.class);
	
	@Autowired
	private PublicacionAppService publicacionAppService;
	
	
	@PostMapping("/publicacionApp/list")
	public ResponseEntity<List<Publicacion>> list() {
		List<Publicacion> lstPublicaciones = publicacionAppService.listByActivo();		
	
		return new ResponseEntity<List<Publicacion>>(lstPublicaciones, HttpStatus.OK);
	}
	
	
	@PostMapping("/publicacionApp/get/id")
	public ResponseEntity<Publicacion> listByIdPublicacion(Long idPublicacion) {
		
		Publicacion publicaciones = publicacionAppService.publicacionById(idPublicacion);
		
		return new ResponseEntity<Publicacion>(publicaciones, HttpStatus.OK);
	}

	
	@PostMapping("/publicacionApp/add")
	public ResponseEntity<?> addPost(@RequestBody Publicacion publicacion) {
				
		ResponseStatus response = publicacionAppService.add(publicacion);
		
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/publicacionApp/update")
	public ResponseEntity<?> updatePost(@RequestBody Publicacion publicacion) {
		
		ResponseStatus response = publicacionAppService.update(publicacion);

		
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/publicacionApp/update/aprobacion")
	public ResponseEntity<?> updateStatus(@RequestBody Publicacion publicacion) {
		
		ResponseStatus response = publicacionAppService.updateAprobacion(publicacion);

		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/publicacionApp/update/reaccion")
	public ResponseEntity<?> updateReaccion(@RequestBody Reaccion reaccion) {
		
		ResponseStatus response = publicacionAppService.updateReaccion(reaccion);

		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	

	@PostMapping("/publicacionApp/delete/id")
	public ResponseEntity<?> deletePost(Long idPublicacion) {
		
		ResponseStatus response = publicacionAppService.delete(idPublicacion);

		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/publicacionApp/delete/reaccion/id")
	public ResponseEntity<?> deleteReaccion(Long idReaccion) {
		
		ResponseStatus response = publicacionAppService.deleteReaccion(idReaccion);

		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/publicacionApp/gestorcontenido")
	public ResponseEntity<ResponseStatus> registroPublicacionGestorContenido(@RequestBody @Valid RequestPublicacionGestorContenido request) {
		logger.info("[BEGIN] registroPublicacionGestorContenido");
		return new ResponseEntity<>(publicacionAppService.registro(request), HttpStatus.OK);
	}

}
