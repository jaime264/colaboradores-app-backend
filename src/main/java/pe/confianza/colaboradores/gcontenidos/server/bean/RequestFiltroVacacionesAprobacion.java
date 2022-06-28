package pe.confianza.colaboradores.gcontenidos.server.bean;

public class RequestFiltroVacacionesAprobacion {
	
	private String usuarioBt;
	private String codigo;
	private String filtro;
	
	public String getUsuarioBt() {
		return usuarioBt;
	}
	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
