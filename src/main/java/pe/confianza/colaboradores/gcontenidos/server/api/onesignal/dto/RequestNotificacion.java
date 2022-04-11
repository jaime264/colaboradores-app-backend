package pe.confianza.colaboradores.gcontenidos.server.api.onesignal.dto;

import java.util.List;

public class RequestNotificacion {
	
	private List<String> usuarios;
	private String titulo;
	private String detalle;
	private Object data;

	public List<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
