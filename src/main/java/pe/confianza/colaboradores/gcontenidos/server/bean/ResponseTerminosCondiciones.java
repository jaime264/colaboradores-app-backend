package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class ResponseTerminosCondiciones {
	
	private boolean acepta;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA_HORA, timezone = "America/Bogota")
	private LocalDateTime fechaAceptacion;

	public boolean isAcepta() {
		return acepta;
	}

	public void setAcepta(boolean acepta) {
		this.acepta = acepta;
	}

	public LocalDateTime getFechaAceptacion() {
		return fechaAceptacion;
	}

	public void setFechaAceptacion(LocalDateTime fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}
	
	

}
