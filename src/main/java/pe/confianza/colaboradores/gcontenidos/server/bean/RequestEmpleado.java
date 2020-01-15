package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class RequestEmpleado implements Serializable {
	
	private String usuarioBT;
	private Long idEmpleado;
	private Long fechaIni;
	private Long fechaFin;
	private boolean totales;
	
	public String getUsuarioBT() {
		return usuarioBT;
	}
	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	
	public Long getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Long fechaIni) {
		this.fechaIni = fechaIni;
	}

	public boolean isTotales() {
		return totales;
	}
	public void setTotales(boolean totales) {
		this.totales = totales;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
