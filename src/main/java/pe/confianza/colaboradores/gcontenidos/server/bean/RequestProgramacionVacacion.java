package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestProgramacionVacacion {
	
	@NotNull(message = "Ingrese una fecha correcta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaInicio;
	
	@NotNull(message = "Ingrese una fecha correcta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaFin;
	
	@NotNull
	@Size(min = 1, message = "Debe ingresar un usuario correcto")
	private String usuarioBT;
	
	@NotNull(message = "Debe ingresar el codigoSpring")
	@Size(min = 1, message = "Debe ingresar un codigoPring correcto")
	private String codigoSpring;
	
	private LogAuditoria logAuditoria;
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getUsuarioBT() {
		return usuarioBT;
	}
	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}
	public String getCodigoSpring() {
		return codigoSpring;
	}
	public void setCodigoSpring(String codigoSpring) {
		this.codigoSpring = codigoSpring;
	}
	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}
	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

}
