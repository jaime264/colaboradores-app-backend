package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class ParamsReaccion implements Serializable {
	
	private Long idPublicacion;
	private String usuario;
	private Integer idReaccion;
	private Integer activo;
	private LogAuditoria logAuditoria;

	public Long getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(Long idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getIdReaccion() {
		return idReaccion;
	}

	public void setIdReaccion(Integer idReaccion) {
		this.idReaccion = idReaccion;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
