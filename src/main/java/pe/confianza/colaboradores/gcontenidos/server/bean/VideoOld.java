package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class VideoOld implements Serializable {
	
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
