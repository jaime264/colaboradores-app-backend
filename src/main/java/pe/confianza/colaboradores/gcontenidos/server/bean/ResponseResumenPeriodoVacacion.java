package pe.confianza.colaboradores.gcontenidos.server.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

import java.time.LocalDate;

public class ResponseResumenPeriodoVacacion {
	
	private double dias;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaLimite;
	
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
	
	

}
