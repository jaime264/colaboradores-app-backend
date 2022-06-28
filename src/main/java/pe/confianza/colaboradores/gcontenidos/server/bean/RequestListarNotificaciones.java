package pe.confianza.colaboradores.gcontenidos.server.bean;

import pe.confianza.colaboradores.gcontenidos.server.RequestPaginacion;

public class RequestListarNotificaciones extends RequestPaginacion {
	
	private String usuarioBT;
	private String codigoTipoNotificacion;
	
	public String getUsuarioBT() {
		return usuarioBT;
	}
	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}
	public String getCodigoTipoNotificacion() {
		return codigoTipoNotificacion;
	}
	public void setCodigoTipoNotificacion(String codigoTipoNotificacion) {
		this.codigoTipoNotificacion = codigoTipoNotificacion;
	}
	
	
	

}
