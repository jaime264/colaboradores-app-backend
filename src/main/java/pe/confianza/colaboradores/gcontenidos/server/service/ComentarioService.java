package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.OcultarComentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;

public interface ComentarioService {
	
	public List<Comentario> list();
	
	public List<Comentario> listByActivo();
	
	public Comentario getComentario(Long id);
	
	public List<Comentario> listByIdPublicacion(Long idPublicacion);

	public ResponseStatus add(Comentario comentario);
	
	public ResponseStatus update(Comentario comentario);
	
	public ResponseStatus delete(Long idComentario);
	
	public ResponseStatus updateOcultarComentario(OcultarComentario ocultarComentario);
	
	public ResponseStatus deleteOcultarComentario(Long idOcultarComentario);

}
