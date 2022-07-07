package pe.confianza.colaboradores.gcontenidos.server.bean;

public class RequestFiltroVacacionesAprobacion {
	
	private String usuarioBt;
	private String codigoNivel1;
	private String codigoNivel2;
	private String filtro;
	
	public String getUsuarioBt() {
		return usuarioBt;
	}
	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	public String getCodigoNivel1() {
		return codigoNivel1;
	}
	public void setCodigoNivel1(String codigoNivel1) {
		this.codigoNivel1 = codigoNivel1;
	}
	public String getCodigoNivel2() {
		return codigoNivel2;
	}
	public void setCodigoNivel2(String codigoNivel2) {
		this.codigoNivel2 = codigoNivel2;
	}

	
}
