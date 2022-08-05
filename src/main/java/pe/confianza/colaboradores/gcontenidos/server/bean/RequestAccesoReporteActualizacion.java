package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestAccesoReporteActualizacion {
	
	private long idAcceso;
	
	@NotNull(message = "Ingrese una fecha correcta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaEnvio;
	
	@NotNull(message = "Debe tener log de auditoria")
	private LogAuditoria logAuditoria;

	public long getIdAcceso() {
		return idAcceso;
	}

	public void setIdAcceso(long idAcceso) {
		this.idAcceso = idAcceso;
	}

	public LocalDate getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDate fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}
	
	

}
