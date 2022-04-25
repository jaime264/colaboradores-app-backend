package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Comentario;

public interface ComentarioService {
	
	public List<Comentario> list();
	
	public List<Comentario> listByIdPublicacion(Long idPublicacion);

	public ResponseStatus add(Comentario comentario);
	
	public ResponseStatus update(Comentario comentario);
	
	public ResponseStatus delete(Long idComentario);

}
