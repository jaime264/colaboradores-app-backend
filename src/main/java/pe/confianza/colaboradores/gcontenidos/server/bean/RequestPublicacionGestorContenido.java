package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

public class RequestPublicacionGestorContenido {
	
	private String categoria;
	private String descripcion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA_HORA, timezone = Constantes.TIME_ZONE)
	private LocalDateTime fechaInicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA_HORA, timezone = Constantes.TIME_ZONE)
	private LocalDateTime fechaFin;
	private boolean flagPermanente;
	private boolean flagReacion;
	private String menu;
	private String submenu;
	private String usuarioBt;
	
	private List<RequestImagenRegistro> imagenes;
	private List<RequestVideoRegistro> videos;
	private List<String> usuarios;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public boolean isFlagPermanente() {
		return flagPermanente;
	}
	public void setFlagPermanente(boolean flagPermanente) {
		this.flagPermanente = flagPermanente;
	}
	public boolean isFlagReacion() {
		return flagReacion;
	}
	public void setFlagReacion(boolean flagReacion) {
		this.flagReacion = flagReacion;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getSubmenu() {
		return submenu;
	}
	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}
	public String getUsuarioBt() {
		return usuarioBt;
	}
	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}
	public List<RequestImagenRegistro> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<RequestImagenRegistro> imagenes) {
		this.imagenes = imagenes;
	}
	public List<RequestVideoRegistro> getVideos() {
		return videos;
	}
	public void setVideos(List<RequestVideoRegistro> videos) {
		this.videos = videos;
	}
	public List<String> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}
	
	

}
