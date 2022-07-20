package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponseTipoNotificacion {
	
	private String codigo;
	private String descripcion;
	private String descripcionExtendida;
	private long notificacionesNoVistas;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionExtendida() {
		return descripcionExtendida;
	}
	public void setDescripcionExtendida(String descripcionExtendida) {
		this.descripcionExtendida = descripcionExtendida;
	}
	public long getNotificacionesNoVistas() {
		return notificacionesNoVistas;
	}
	public void setNotificacionesNoVistas(long notificacionesNoVistas) {
		this.notificacionesNoVistas = notificacionesNoVistas;
	}
	
	

}
