package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.ResponseStatus;

public interface PublicacionService {
	
	public List<Publicacion> listPost();
	
	public List<Publicacion> listPostUser(String user, Long lastPost);
	
	public ResponseStatus createPost(List<Publicacion> post);

}
