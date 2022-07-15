package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestFiltroPuesto {
	
	
	@NotNull
	@Size(min = 3, message = "Debe ingresar mínimo 3 caracteres")
	private String filtro;

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	@Override
	public String toString() {
		return "RequestFiltroPuesto [filtro=" + filtro + "]";
	}
	
	
	

}
