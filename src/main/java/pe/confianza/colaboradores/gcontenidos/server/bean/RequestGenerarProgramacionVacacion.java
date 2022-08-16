package pe.confianza.colaboradores.gcontenidos.server.bean;

	

import javax.validation.constraints.NotNull;
import java.util.List;

public class RequestGenerarProgramacionVacacion extends RequestAuditoria {
	
	@NotNull(message = "Ingrese el ids de programaciones")
	private List<Long> idProgramaciones;

	public List<Long> getIdProgramaciones() {
		return idProgramaciones;
	}

	public void setIdProgramaciones(List<Long> idProgramaciones) {
		this.idProgramaciones = idProgramaciones;
	}
	
	

}
