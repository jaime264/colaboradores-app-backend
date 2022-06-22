package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestReprogramacionAprobador {

	private Long idProgramacion;
	private String usuarioBt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaIni;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaFin;
	
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
	public LocalDate getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(LocalDate fechaIni) {
		this.fechaIni = fechaIni;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
