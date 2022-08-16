package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.PublicacionOld;

import java.util.List;
import java.util.Optional;

public interface PublicacionService {
	
	public List<PublicacionOld> listPost();
	
	public List<PublicacionOld> listPostUser(String user, Long lastPost, Integer size, Boolean back);
	
	public Optional<PublicacionOld> findByIdPost(Long id);
	
	public Optional<PublicacionOld> findByIdPostUser(Long id, String user);
	
	public ResponseStatus createPost(List<PublicacionOld> post);
	
	public ResponseStatus addReaccion(ParamsReaccion paramsReaccion);

}
