package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.util.ArrayList;
import java.util.List;

public class NotificacionPublicacionDataExtra {
	
	private int subTipo;
	private long idPublicacion;
	private String usuarioCrea;
	private String menu;
	private String submenu;
	private String categoria;
	private String descripcion;
	private String observacion;
	private int flagAprobacion;
	private String nombre;
	private String sexo;
	private boolean gestorContenido;
	
	private List<NotificacionPublicacionMediaDataExtra> imagenes;
	private List<NotificacionPublicacionMediaDataExtra> videos;
	
	
	public NotificacionPublicacionDataExtra() {
		this.imagenes = new ArrayList<>();
		this.videos = new ArrayList<>();
	}
	
	
	public int getSubTipo() {
		return subTipo;
	}
	public void setSubTipo(int subTipo) {
		this.subTipo = subTipo;
	}
	public long getIdPublicacion() {
		return idPublicacion;
	}
	public void setIdPublicacion(long idPublicacion) {
		this.idPublicacion = idPublicacion;
	}
	public String getUsuarioCrea() {
		return usuarioCrea;
	}
	public void setUsuarioCrea(String usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
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
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getFlagAprobacion() {
		return flagAprobacion;
	}
	public void setFlagAprobacion(int flagAprobacion) {
		this.flagAprobacion = flagAprobacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public boolean isGestorContenido() {
		return gestorContenido;
	}
	public void setGestorContenido(boolean gestorContenido) {
		this.gestorContenido = gestorContenido;
	}
	public List<NotificacionPublicacionMediaDataExtra> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<NotificacionPublicacionMediaDataExtra> imagenes) {
		this.imagenes = imagenes;
	}
	public List<NotificacionPublicacionMediaDataExtra> getVideos() {
		return videos;
	}
	public void setVideos(List<NotificacionPublicacionMediaDataExtra> videos) {
		this.videos = videos;
	}
	
	
	

}
