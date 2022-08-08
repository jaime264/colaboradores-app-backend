package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class ResponseAccesoReporte {
	
	private long id;
	private ReponseReporteTipo tipoReporte;
	private ResponsePuesto puesto;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaEnvio;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ReponseReporteTipo getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(ReponseReporteTipo tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public ResponsePuesto getPuesto() {
		return puesto;
	}
	public void setPuesto(ResponsePuesto puesto) {
		this.puesto = puesto;
	}
	public LocalDate getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(LocalDate fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
	

}
