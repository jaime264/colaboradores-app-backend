package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

import java.time.LocalDate;

public class ProgramacionVacacionesRes {
	
	private long idEmpleado;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaInicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	private LocalDate fechaFin;
	private long codigo;
	private int estado;
	
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
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
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

}
