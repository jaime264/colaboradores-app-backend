package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.time.LocalDate;

public class VacacionPeriodo {


	private Long idProgramacion;
	private String periodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Integer idEstado;

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

	public Long getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(Long idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

}
