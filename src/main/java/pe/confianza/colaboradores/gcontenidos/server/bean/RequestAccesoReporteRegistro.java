package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestAccesoReporteRegistro {
	
	private long idPuesto;
	
	@NotNull(message = "Debe tener código de reporte")
	private String codigoReporte;
	
	@NotNull(message = "Ingrese una fecha correcta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaEnvio;
	
	@NotNull(message = "Debe tener log de auditoria")
	private LogAuditoria logAuditoria;
	
	public long getIdPuesto() {
		return idPuesto;
	}
	public void setIdPuesto(long idPuesto) {
		this.idPuesto = idPuesto;
	}
	public String getCodigoReporte() {
		return codigoReporte;
	}
	public void setCodigoReporte(String codigoReporte) {
		this.codigoReporte = codigoReporte;
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
