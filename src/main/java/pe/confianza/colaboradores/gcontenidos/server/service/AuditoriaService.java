package pe.confianza.colaboradores.gcontenidos.server.service;

import org.bson.BsonDocument;

public interface AuditoriaService {
	
	void createAuditoria(String idAplicacion, String idProceso, Integer status, BsonDocument data);

}
