package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class ResponseResumenVacacion {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaConsulta;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaInicioLabores;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaFinLabores;
	
	private ResponseResumenPeriodoVacacion periodoVencido;
	
	private ResponseResumenPeriodoVacacion periodoTrunco;
	
	private String nombres;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String cargo;
	
	private double meta;

	public LocalDate getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(LocalDate fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public LocalDate getFechaInicioLabores() {
		return fechaInicioLabores;
	}

	public void setFechaInicioLabores(LocalDate fechaInicioLabores) {
		this.fechaInicioLabores = fechaInicioLabores;
	}

	public LocalDate getFechaFinLabores() {
		return fechaFinLabores;
	}

	public void setFechaFinLabores(LocalDate fechaFinLabores) {
		this.fechaFinLabores = fechaFinLabores;
	}

	public ResponseResumenPeriodoVacacion getPeriodoVencido() {
		return periodoVencido;
	}

	public void setPeriodoVencido(ResponseResumenPeriodoVacacion periodoVencido) {
		this.periodoVencido = periodoVencido;
	}

	public ResponseResumenPeriodoVacacion getPeriodoTrunco() {
		return periodoTrunco;
	}

	public void setPeriodoTrunco(ResponseResumenPeriodoVacacion periodoTrunco) {
		this.periodoTrunco = periodoTrunco;
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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	
	
	

}
