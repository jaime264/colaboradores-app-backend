package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.PublicacionAppService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
@ApiOperation("Products API")
public class PublicacionAppController {
	
private static Logger logger = LoggerFactory.getLogger(PublicacionController.class);
	
	@Autowired
	private PublicacionAppService publicacionAppService;
	
	@Autowired
	private AuditoriaService auditoriaService;
	
	@PostMapping("/publicacionApp/list")
	public ResponseEntity<List<Publicacion>> show() {
		List<Publicacion> lstPublicaciones = publicacionAppService.list();		
	
		return new ResponseEntity<List<Publicacion>>(lstPublicaciones, HttpStatus.OK);
	}
	
	@PostMapping("/publicacionApp/{id}")
	public ResponseEntity<Publicacion> listByIdPublicacion(@PathVariable long id) {
		
		Publicacion pub = publicacionAppService.publicacionById(id);
		
		return new ResponseEntity<Publicacion>(pub, HttpStatus.OK);
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
	
	@PostMapping("/publicacionApp/delete/{id}")
	public ResponseEntity<?> deletePost(@PathVariable long id) {
		
		ResponseStatus response = publicacionAppService.delete(id);
		
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}

}
