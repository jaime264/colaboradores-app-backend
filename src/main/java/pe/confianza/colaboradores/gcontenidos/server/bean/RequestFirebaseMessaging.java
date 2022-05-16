package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.List;

public class RequestFirebaseMessaging {

	private List<String> tokens;
	private RequestFirebaseMessagingData data;
	
	public List<String> getTokens() {
		return tokens;
	}
	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	public RequestFirebaseMessagingData getData() {
		return data;
	}
	public void setData(RequestFirebaseMessagingData data) {
		this.data = data;
	}
	
	
	
	
}
