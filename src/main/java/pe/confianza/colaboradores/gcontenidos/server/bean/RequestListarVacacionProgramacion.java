package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestListarVacacionProgramacion {
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar un usuario correcto")
	private String usuarioBT; 
	
	private String estado;
	
	private String periodo;

	public String getUsuarioBT() {
		return usuarioBT;
	}

	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	

}
