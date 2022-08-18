package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;
import java.util.Map;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;

public interface AgenciaNegocio {
	
	List<Map<String, Object>>  listarTerritorios(RequestAuditoriaBase peticion);
	
	List<Map<String, Object>>  listarCorredores(String codigoTerritorio, RequestAuditoriaBase peticion);
	
	List<Map<String, Object>>  listarAgencias(String codigoCorredor, RequestAuditoriaBase peticion);
	
	void registrarAuditoria(int status, String mensaje, Object data);
}
