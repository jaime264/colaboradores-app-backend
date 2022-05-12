package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb.Publicacion;

public interface PublicacionAppService {

	public List<Publicacion> list();

	public ResponseStatus add(Publicacion publicacion);
	
	public Publicacion publicacionById(Long id);

	public ResponseStatus update(Publicacion publicacion);

	public ResponseStatus delete(Long idPublicacion);

}
