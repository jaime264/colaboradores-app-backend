package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;

@Entity
@Table(name = "publicacion")
public class PublicacionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String Descripcion;
	private String menu;
	private String submenu;
	private String categoria;

	@Column(nullable = true)
	private Integer flgaprobacion;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime fecha;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaInicio;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaFin;

	private Boolean flagReacion;

	private Integer reacciones;
	private List<Comentario> comentarios;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPublicacion")
	private List<VideoEntity> videos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPublicacion")
	private List<ImagenEntity> imagenes;

	private String idUsuario;
	private LogAuditoria logAuditoria;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
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

	public Integer getFlgaprobacion() {
		return flgaprobacion;
	}

	public void setFlgaprobacion(Integer flgaprobacion) {
		this.flgaprobacion = flgaprobacion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
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

	public Boolean getFlagReacion() {
		return flagReacion;
	}

	public void setFlagReacion(Boolean flagReacion) {
		this.flagReacion = flagReacion;
	}

	public Integer getReacciones() {
		return reacciones;
	}

	public void setReacciones(Integer reacciones) {
		this.reacciones = reacciones;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<VideoEntity> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoEntity> videos) {
		this.videos = videos;
	}

	public List<ImagenEntity> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<ImagenEntity> imagenes) {
		this.imagenes = imagenes;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

}
