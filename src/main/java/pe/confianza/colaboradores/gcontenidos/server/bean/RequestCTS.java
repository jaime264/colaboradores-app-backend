package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class RequestCTS implements Serializable{
	private int anio;
	private Long idEmpleado;
	private int secuencia;
	
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630900223863185786L;
}
