package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.Min;

public class RequestModificarMetaVacacion extends RequestAuditoria {
	@Min(value = 1, message = "Ingrese un id válido")
	private long idMeta;
	@Min(value = 0, message = "No se acepta valores negativos")
	private double nuevaMeta;
	
	public long getIdMeta() {
		return idMeta;
	}
	public void setIdMeta(long idMeta) {
		this.idMeta = idMeta;
	}
	public double getNuevaMeta() {
		return nuevaMeta;
	}
	public void setNuevaMeta(double nuevaMeta) {
		this.nuevaMeta = nuevaMeta;
	}
	
	

}
