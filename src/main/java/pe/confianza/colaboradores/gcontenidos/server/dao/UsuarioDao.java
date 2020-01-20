package pe.confianza.colaboradores.gcontenidos.server.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pe.confianza.colaboradores.gcontenidos.server.model.entity.Usuario;

@Repository
public interface UsuarioDao extends MongoRepository<Usuario, Long> {

	Usuario findUsuarioByUsuarioBTAndAplicacion(String usuarioBT, String aplicacion);
}
