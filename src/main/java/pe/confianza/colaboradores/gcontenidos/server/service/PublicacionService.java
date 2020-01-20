package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Publicacion;

public interface PublicacionService {
	
	public List<Publicacion> listPost();
	
	public List<Publicacion> listPostUser(String user, Long lastPost, Integer size, Boolean back);
	
	public Optional<Publicacion> findByIdPost(Long id);
	
	public Optional<Publicacion> findByIdPostUser(Long id, String user);
	
	public ResponseStatus createPost(List<Publicacion> post);
	
	public ResponseStatus addReaccion(ParamsReaccion paramsReaccion);

}
