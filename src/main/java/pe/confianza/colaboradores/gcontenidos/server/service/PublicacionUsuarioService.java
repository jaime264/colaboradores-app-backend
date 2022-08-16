package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.PublicacionUsuario;

import java.util.List;

public interface PublicacionUsuarioService {
	
	public void createRelation(List<PublicacionUsuario> relacion);
	
	public List<PublicacionUsuario> findAllReaction(Long idPublicacion, String idUsuario);
	
	public PublicacionUsuario findByRelation(Long idPublicacion, String idUsuario);

}
