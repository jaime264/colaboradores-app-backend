package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

public class Video implements Serializable {
	
	private Long id;
	private String url;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
