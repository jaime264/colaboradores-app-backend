package pe.confianza.colaboradores.gcontenidos.server.bean.bt;

import java.io.Serializable;

public class RequestVersion implements Serializable{	
	private String aplicativo;
	private String usuario;
	private String version;
	private String imei;
	private String nromovil;
	private AuditoriaBT auditoria;
	
	
	
	public String getAplicativo() {
		return aplicativo;
	}



	public void setAplicativo(String aplicativo) {
		this.aplicativo = aplicativo;
	}



	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getImei() {
		return imei;
	}



	public void setImei(String imei) {
		this.imei = imei;
	}



	public String getNromovil() {
		return nromovil;
	}



	public void setNromovil(String nromovil) {
		this.nromovil = nromovil;
	}



	public AuditoriaBT getAuditoria() {
		return auditoria;
	}



	public void setAuditoria(AuditoriaBT auditoria) {
		this.auditoria = auditoria;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 8313528493556217822L;
}
