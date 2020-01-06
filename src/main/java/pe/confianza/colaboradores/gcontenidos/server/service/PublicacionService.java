package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.ResponseStatus;

public interface PublicacionService {
	
	public List<Publicacion> listPost();
	
	public List<Publicacion> listPostUser(String user, Long lastPost);
	
	public Optional<Publicacion> findById(Long id);
	
	public ResponseStatus createPost(List<Publicacion> post);
	
	public ResponseStatus addReaccion(ParamsReaccion paramsReaccion);

}
