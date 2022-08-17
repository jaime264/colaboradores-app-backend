package pe.confianza.colaboradores.gcontenidos.server.negocio;

public interface SolictudGastoNegocio {
	
	void registrarAuditoria(int status, String mensaje, Object data);

}
