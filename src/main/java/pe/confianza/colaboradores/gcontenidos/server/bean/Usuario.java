package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class Usuario implements Serializable{

	private String usuarioBT;
	private Long ultimaPublicacion;
	private LogAuditoria logAuditoria;
	
	private static final long serialVersionUID = 1L;

	public String getUsuarioBT() {
		return usuarioBT;
	}

	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}

	public Long getUltimaPublicacion() {
		return ultimaPublicacion;
	}

	public void setUltimaPublicacion(Long ultimaPublicacion) {
		this.ultimaPublicacion = ultimaPublicacion;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

}
