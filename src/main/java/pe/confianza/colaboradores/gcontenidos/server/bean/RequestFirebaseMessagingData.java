package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.Map;

public class RequestFirebaseMessagingData {
	
	private String title;
	private String body;
	private String icon;
	private Map<String, String> extra;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Map<String, String> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}
	
	
	
	
	
	

}
