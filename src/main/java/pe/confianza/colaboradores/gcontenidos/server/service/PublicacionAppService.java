package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Optional;

import pe.confianza.colaboradores.gcontenidos.server.bean.ParamsReaccion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Comentario;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionEntity;

public interface PublicacionAppService {

	public List<PublicacionEntity> list();

	public ResponseStatus add(PublicacionEntity publicacion);

	public ResponseStatus update(PublicacionEntity publicacion);

	public ResponseStatus delete(Long idPublicacion);

}
