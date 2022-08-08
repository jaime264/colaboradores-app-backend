package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;

import pe.confianza.colaboradores.gcontenidos.server.RequestPaginacion;

public class RequestListarReporteAcceso extends RequestPaginacion {
	
	private Long idPuesto;
	
	@NotNull(message = "Debe tener log de auditoria")
	private LogAuditoria logAuditoria;

	public Long getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(Long idPuesto) {
		this.idPuesto = idPuesto;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}
	
	

}
