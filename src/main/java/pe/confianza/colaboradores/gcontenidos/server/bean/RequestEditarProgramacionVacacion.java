package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestEditarProgramacionVacacion {
	
	@NotNull(message = "Ingrese una fecha correcta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaInicio;
	
	@NotNull(message = "Ingrese una fecha correcta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaFin;
	
	private long idProgramacion;
	
	@NotNull(message = "Debe tener log de auditoria")
	private LogAuditoria logAuditoria;

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

	public long getIdProgramacion() {
		return idProgramacion;
	}

	public void setIdProgramacion(long idProgramacion) {
		this.idProgramacion = idProgramacion;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	} 
	
	

}
