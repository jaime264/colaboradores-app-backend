package pe.confianza.colaboradores.gcontenidos.server.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

import java.time.LocalDate;

public class ResponseResumenPeriodoVacacion {
	
	private double dias;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaLimite;
	
	private String descripcion;
	
	private int ultimoTramo;
	
	public double getDias() {
		return dias;
	}
	public void setDias(double dias) {
		this.dias = dias;
	}
	public LocalDate getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(LocalDate fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getUltimoTramo() {
		return ultimoTramo;
	}
	public void setUltimoTramo(int ultimoTramo) {
		this.ultimoTramo = ultimoTramo;
	}
	

}
