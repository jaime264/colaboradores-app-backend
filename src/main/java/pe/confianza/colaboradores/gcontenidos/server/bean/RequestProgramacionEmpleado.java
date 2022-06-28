package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestProgramacionEmpleado {

	private String usuarioBt;

	private String tipoFiltro;

	private String[] filtro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaFin;

	public String getUsuarioBt() {
		return usuarioBt;
	}

	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String[] getFiltro() {
		return filtro;
	}

	public void setFiltro(String[] filtro) {
		this.filtro = filtro;
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

}
