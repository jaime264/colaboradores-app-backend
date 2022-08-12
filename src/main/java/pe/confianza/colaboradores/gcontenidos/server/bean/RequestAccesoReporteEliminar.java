package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;

public class RequestAccesoReporteEliminar {

	private long idAcceso;
	
	@NotNull(message = "Debe tener log de auditoria")
	private LogAuditoria logAuditoria;

	public long getIdAcceso() {
		return idAcceso;
	}

	public void setIdAcceso(long idAcceso) {
		this.idAcceso = idAcceso;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}
	
	
}
