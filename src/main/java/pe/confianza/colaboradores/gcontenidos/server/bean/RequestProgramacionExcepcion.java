package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RequestProgramacionExcepcion extends RequestAuditoria {
	
	private long idProgramacion;

	@NotNull(message = "Debe tener mínimo un tramo")
	private List<RequestReprogramacionTramo> tramos;

	public long getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(long idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public List<RequestReprogramacionTramo> getTramos() {
		return tramos;
	}

	public void setTramos(List<RequestReprogramacionTramo> tramos) {
		this.tramos = tramos;
	}
	
	

}
