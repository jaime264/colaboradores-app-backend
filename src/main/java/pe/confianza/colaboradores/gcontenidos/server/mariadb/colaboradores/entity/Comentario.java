package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "comentario")
public class Comentario extends EntidadAuditoria  {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(nullable = true, name = "idPublicacion")
	private Publicacion publicacion;
	
	@Column(nullable = true)
	private String descripcion;
	
	@Column(nullable = true)
	private Boolean flAprobacion;
	
	@Column(nullable = true)
	private Date fecha;
	
	@Column(nullable = true)
	private Date fechaInicio;
	
	@Column(nullable = true)
	private Date fechaFin;
	
	@Column(nullable = true)
	private Integer flgreaccion;
	
	@Column(nullable = true)
	private Integer reacciones;
	
	@Column(nullable = true)
	private Boolean activo;
	
	@OneToMany(mappedBy = "comentario",  fetch = FetchType.LAZY)
	private List<Imagen> imagenes;
	
	@OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY)
	private List<Video> videos;

	@Column(nullable = true)
	private String idUsuario;
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getFlAprobacion() {
		return flAprobacion;
	}

	public void setFlAprobacion(Boolean flAprobacion) {
		this.flAprobacion = flAprobacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

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

	public Integer getFlgreaccion() {
		return flgreaccion;
	}

	public void setFlgreaccion(Integer flgreaccion) {
		this.flgreaccion = flgreaccion;
	}

	public Integer getReacciones() {
		return reacciones;
	}

	public void setReacciones(Integer reacciones) {
		this.reacciones = reacciones;
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
}
