package pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "publicacionApp")
public class Publicacion extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = true)
	private String Descripcion;
	
	@Column(nullable = true)
	private String menu;
	
	@Column(nullable = true)
	private String submenu;
	
	@Column(nullable = true)
	private String categoria;

	@Column(nullable = true)
	private Integer flagAprobacion;

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
	

	@OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
	private List<Comentario> comentarios;

	@OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
	private List<Video> videos;

	@OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
	private List<Imagen> imagenes;

	private String idUsuario;
	
	@Column(nullable = true)
	private Boolean activo;


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


	public Integer getFlagAprobacion() {
		return flagAprobacion;
	}

	public void setFlagAprobacion(Integer flgaprobacion) {
		this.flagAprobacion = flgaprobacion;

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

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}	
	

}
