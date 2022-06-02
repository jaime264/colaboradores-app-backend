package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Publicacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Reaccion;

public interface PublicacionAppService {

	public List<Publicacion> list();
	
	public List<Publicacion> listByActivo();

	public ResponseStatus add(Publicacion publicacion);
	
	public Publicacion publicacionById(Long id);

	public ResponseStatus update(Publicacion publicacion);

	public ResponseStatus delete(Long idPublicacion);
	
	public ResponseStatus updateAprobacion(Publicacion publicacion);
	
	public ResponseStatus updateReaccion(Reaccion reaccion);

	public ResponseStatus deleteReaccion(Long idReaccion);
}
