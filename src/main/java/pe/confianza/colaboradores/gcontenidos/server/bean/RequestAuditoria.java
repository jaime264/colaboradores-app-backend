package pe.confianza.colaboradores.gcontenidos.server.bean;

import javax.validation.constraints.NotNull;

public class RequestAuditoria {
	
	@NotNull(message = "Ingrese usuarioBT que realiza la petición")
	private String usuarioOperacion;

	public String getUsuarioOperacion() {
		return usuarioOperacion;
	}

	public void setUsuarioOperacion(String usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}
	
	

}
