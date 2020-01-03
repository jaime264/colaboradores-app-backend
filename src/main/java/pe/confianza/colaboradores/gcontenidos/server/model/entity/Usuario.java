package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

public class Usuario implements Serializable{

	private String usuarioBT;
	private Long ultimaPublicacion;
	
	private static final long serialVersionUID = 1L;

	public String getUsuarioBT() {
		return usuarioBT;
	}

	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}

	public Long getUltimaPublicacion() {
		return ultimaPublicacion;
	}

	public void setUltimaPublicacion(Long ultimaPublicacion) {
		this.ultimaPublicacion = ultimaPublicacion;
	}

}
