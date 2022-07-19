package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadOperativa;

public class EmplVacPerRes {

	private String nombres;
	private String puesto;
	private Long idEmpleado;
	private String usuarioBt;
	private Long idProgramacion;
	private String periodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Integer idEstado;
	private String descripcionEstado;

	@JsonIgnore
	private String agencia;
	@JsonIgnore
	private List<Territorio> territorio;
	@JsonIgnore
	private List<Corredor> corredor;
	@JsonIgnore
	private List<UnidadOperativa> area;
	
	private boolean adelantada;

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
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

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public List<Territorio> getTerritorio() {
		return territorio;
	}

	public void setTerritorio(List<Territorio> territorio) {
		this.territorio = territorio;
	}

	public List<Corredor> getCorredor() {
		return corredor;
	}

	public void setCorredor(List<Corredor> corredor) {
		this.corredor = corredor;
	}

	public List<UnidadOperativa> getArea() {
		return area;
	}

	public void setArea(List<UnidadOperativa> area) {
		this.area = area;
	}

	public boolean isAdelantada() {
		return adelantada;
	}

	public void setAdelantada(boolean adelantada) {
		this.adelantada = adelantada;
	}
	
	

}
