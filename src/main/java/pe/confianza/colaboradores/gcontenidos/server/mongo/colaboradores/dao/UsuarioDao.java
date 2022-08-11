package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Usuario;

@Repository
public interface UsuarioDao extends MongoRepository<Usuario, Long> {

	Usuario findUsuarioByUsuarioBTAndAplicacion(String usuarioBT, String aplicacion);
}
