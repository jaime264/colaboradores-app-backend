package pe.confianza.colaboradores.gcontenidos.server.bean.bt;

import java.io.Serializable;

public class AuditoriaBT implements Serializable{
	private int aplicativo;
	private String usuario;
	private int dispositivo;
	private String imei;
	private int proceso;
	private Long latitud1;
	private Long longitud1;
	private Long latitud2;
	private Long longitud2;
	
	

	public int getAplicativo() {
		return aplicativo;
	}



	public void setAplicativo(int aplicativo) {
		this.aplicativo = aplicativo;
	}



	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public int getDispositivo() {
		return dispositivo;
	}



	public void setDispositivo(int dispositivo) {
		this.dispositivo = dispositivo;
	}



	public String getImei() {
		return imei;
	}



	public void setImei(String imei) {
		this.imei = imei;
	}



	public int getProceso() {
		return proceso;
	}



	public void setProceso(int proceso) {
		this.proceso = proceso;
	}



	public Long getLatitud1() {
		return latitud1;
	}



	public void setLatitud1(Long latitud1) {
		this.latitud1 = latitud1;
	}



	public Long getLongitud1() {
		return longitud1;
	}



	public void setLongitud1(Long longitud1) {
		this.longitud1 = longitud1;
	}



	public Long getLatitud2() {
		return latitud2;
	}



	public void setLatitud2(Long latitud2) {
		this.latitud2 = latitud2;
	}



	public Long getLongitud2() {
		return longitud2;
	}



	public void setLongitud2(Long longitud2) {
		this.longitud2 = longitud2;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1664890816136163988L;
	
}
