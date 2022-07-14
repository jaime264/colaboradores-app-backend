package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDateTime;

public class NotificacionPublicacionMediaDataExtra {
	
	private String usuarioCrea;
	private LocalDateTime fechaCrea;
	private String usuarioModifica;
	private LocalDateTime fechaModifica;
	private String estadoRegistro;
	private String usuarioMigracion;
	private long id;
	private boolean activo;
	private String url;
	
	public String getUsuarioCrea() {
		return usuarioCrea;
	}
	public void setUsuarioCrea(String usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}
	public LocalDateTime getFechaCrea() {
		return fechaCrea;
	}
	public void setFechaCrea(LocalDateTime fechaCrea) {
		this.fechaCrea = fechaCrea;
	}
	public String getUsuarioModifica() {
		return usuarioModifica;
	}
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	public LocalDateTime getFechaModifica() {
		return fechaModifica;
	}
	public void setFechaModifica(LocalDateTime fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	public String getEstadoRegistro() {
		return estadoRegistro;
	}
	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}
	public String getUsuarioMigracion() {
		return usuarioMigracion;
	}
	public void setUsuarioMigracion(String usuarioMigracion) {
		this.usuarioMigracion = usuarioMigracion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
