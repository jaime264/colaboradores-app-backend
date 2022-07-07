package pe.confianza.colaboradores.gcontenidos.server;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoria;

public class RequestParametroActualizacion extends RequestAuditoria {
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar un código")
	private String codigo;
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar un nuevoValor")
	private String nuevoValor;

	public String getCodigo() {
		return codigo;
	}

	public String getNuevoValor() {
		return nuevoValor;
	}

	public void setNuevoValor(String nuevoValor) {
		this.nuevoValor = nuevoValor;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	

}
