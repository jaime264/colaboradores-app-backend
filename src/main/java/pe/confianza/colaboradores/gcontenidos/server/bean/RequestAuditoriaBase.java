package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;

public class RequestAuditoriaBase {
	
	@NotNull(message = "Debe tener log de auditoria")
	private LogAuditoria logAuditoria;

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}
	
	

}
