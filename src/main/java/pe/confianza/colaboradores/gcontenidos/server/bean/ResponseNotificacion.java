package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class ResponseNotificacion {
	
	private long id;
	private String titulo;
	private String descripcion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA_HORA, timezone = "America/Bogota")
	private LocalDateTime fecha;
	private String informacionAdicional;
	private boolean visto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getInformacionAdicional() {
		return informacionAdicional;
	}
	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}
	public boolean isVisto() {
		return visto;
	}
	public void setVisto(boolean visto) {
		this.visto = visto;
	}
	
	

}
