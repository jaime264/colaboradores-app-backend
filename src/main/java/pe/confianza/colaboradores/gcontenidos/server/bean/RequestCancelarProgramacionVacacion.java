package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;

public class RequestCancelarProgramacionVacacion extends RequestAuditoria {
	
	@NotNull(message = "Ingrese el id de programación")
	private Long idProgramacion;

	public Long getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Long idProgramacion) {
		this.idProgramacion = idProgramacion;
	}
	
	

}
