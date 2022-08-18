package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mail {
	
	private String emisor;
	private String receptor;
	private String asunto;
	private String[] receptorCC;
	
	public String[] getReceptorCC() {
		return receptorCC;
	}

	public void setReceptorCC(String[] receptorCC) {
		this.receptorCC = receptorCC;
	}
	private List<MailFile> adjuntos;
	private Map<String, Object> contenido;
	
	public Mail() {
		this.adjuntos = new ArrayList<>();
	}
	
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
	
	public List<MailFile> getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(List<MailFile> adjuntos) {
		this.adjuntos = adjuntos;
	}
	@Override
	public String toString() {
		return "Mail [emisor=" + emisor + ", asunto=" + asunto + "]";
	}	

}
