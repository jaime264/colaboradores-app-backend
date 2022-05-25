package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.Map;

public class Mail {
	
	private String emisor;
	private String receptor;
	private String asunto;
	private Map<String, Object> contenido;
	
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public Map<String, Object> getContenido() {
		return contenido;
	}
	public void setContenido(Map<String, Object> contenido) {
		this.contenido = contenido;
	}
	
	

}
