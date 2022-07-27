package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class ResponseProgramacionVacacion {

	private long id;
	private String usuarioBT;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaInicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaFin;
	private int dias;
	private int idEstado;
	private String descripcionEstado;
	private String leyendaEstado;
	private String periodo;
	private int orden;
	private boolean adelantada;
	private boolean interrupcion;
	private boolean anulacion;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsuarioBT() {
		return usuarioBT;
	}
	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
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
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public boolean isAdelantada() {
		return adelantada;
	}
	public void setAdelantada(boolean adelantada) {
		this.adelantada = adelantada;
	}
	public String getLeyendaEstado() {
		return leyendaEstado;
	}
	public void setLeyendaEstado(String leyendaEstado) {
		this.leyendaEstado = leyendaEstado;
	}
	public boolean isInterrupcion() {
		return interrupcion;
	}
	public void setInterrupcion(boolean interrupcion) {
		this.interrupcion = interrupcion;
	}
	public boolean isAnulacion() {
		return anulacion;
	}
	public void setAnulacion(boolean anulacion) {
		this.anulacion = anulacion;
	}
	
	
}
