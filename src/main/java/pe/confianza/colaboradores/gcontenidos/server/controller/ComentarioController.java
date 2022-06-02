package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.OcultarComentario;
import pe.confianza.colaboradores.gcontenidos.server.service.ComentarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100", "http://localhost:4200", "http://172.20.9.12:7445", "http://172.20.10.13:7445" })
public class ComentarioController {

	private static Logger logger = LoggerFactory.getLogger(PublicacionController.class);

	@Autowired
	private ComentarioService comentarioService;
	
	
	@PostMapping("/comentario/list")
	public ResponseEntity<List<Comentario>> list() {
		List<Comentario> lstComentarios = comentarioService.listByActivo();		
	
		return new ResponseEntity<List<Comentario>>(lstComentarios, HttpStatus.OK);
	}
	
	@PostMapping("/comentario/list/idPublicacion")
	public ResponseEntity<?> listByIdPublicacion(Long idPublicacion) {
		
		List<Comentario> comentarios = comentarioService.listByIdPublicacion(idPublicacion);
		
		return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
	}

	
	@PostMapping("/comentario/add")
	public ResponseEntity<?> addPost(@RequestBody Comentario comentario) {
				
		ResponseStatus response = comentarioService.add(comentario);
		
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/comentario/update")
	public ResponseEntity<?> updatePost(@RequestBody Comentario comentario) {
		
		ResponseStatus response = comentarioService.update(comentario);

		
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/comentario/delete")
	public ResponseEntity<?> deletePost(Long idComentario) {
		
		ResponseStatus response = comentarioService.delete(idComentario);
		
		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/comentario/ocultar")
	public ResponseEntity<?> updateComentarioOcultar(@RequestBody OcultarComentario ocultarComentario) {
		
		ResponseStatus response = comentarioService.updateOcultarComentario(ocultarComentario);

		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
	
	@PostMapping("/comentario/delete-ocultar")
	public ResponseEntity<?> deleteComentarioOcultar(Long idOcultarComentario) {
		
		ResponseStatus response = comentarioService.deleteOcultarComentario(idOcultarComentario);

		return new ResponseEntity<ResponseStatus>(response, HttpStatus.OK);
	}
}
