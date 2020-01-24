package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class RequestReaccion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LogAuditoria logAuditoria;

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}
}
