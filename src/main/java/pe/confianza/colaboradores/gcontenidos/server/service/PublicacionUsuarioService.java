package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.PublicacionUsuario;

public interface PublicacionUsuarioService {
	
	public void createRelation(List<PublicacionUsuario> relacion);
	
	public List<PublicacionUsuario> findAllReaction(Long idPublicacion, String idUsuario);
	
	public PublicacionUsuario findByRelation(Long idPublicacion, String idUsuario);

}
