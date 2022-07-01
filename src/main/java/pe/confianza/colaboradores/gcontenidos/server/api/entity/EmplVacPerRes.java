package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmplVacPerRes {

	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String puesto;
	private Long idEmpleado;
	private String usuarioBt;
	private Long idProgramacion;
	private String periodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Integer idEstado;
	
	@JsonIgnore
	private String agencia;
	@JsonIgnore
	private String territorio;
	@JsonIgnore
	private String corredor;
	@JsonIgnore
	private String area;

	
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getTerritorio() {
		return territorio;
	}
	public void setTerritorio(String territorio) {
		this.territorio = territorio;
	}
	public String getCorredor() {
		return corredor;
	}
	public void setCorredor(String corredor) {
		this.corredor = corredor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getUsuarioBt() {
		return usuarioBt;
	}
	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}
	public Long getIdProgramacion() {
		return idProgramacion;
	}
	public void setIdProgramacion(Long idProgramacion) {
		this.idProgramacion = idProgramacion;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	

}
