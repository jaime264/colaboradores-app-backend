package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.Date;

public class ResponseProgramacionVacacion {

	private long id;
	private String codigoSpring;
	private String usuarioBT;
	private Date fechaInicio;
	private Date fechaFin;
	private int dias;
	private int idEstado;
	private String descripcionEstado;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigoSpring() {
		return codigoSpring;
	}
	public void setCodigoSpring(String codigoSpring) {
		this.codigoSpring = codigoSpring;
	}
	public String getUsuarioBT() {
		return usuarioBT;
	}
	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	
	
}
