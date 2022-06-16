package pe.confianza.colaboradores.gcontenidos.server.bean;

public class RequestVideoRegistro {
	

	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "VideoRegistroRequest [url=" + url + "]";
	}
	
	

}
