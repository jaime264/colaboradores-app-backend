package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;
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

import org.springframework.data.annotation.Transient;

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
	private LocalDate fecha;
	
	@Column(nullable = true)
	private LocalDate fechaInicio;
	
	@Column(nullable = true)
	private LocalDate fechaFin;
	
	@Column(nullable = true)
	private Boolean flagReaccion;
	
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
	
	@Transient
	private long publicacionId;
	

	public long getPublicacionId() {
		return publicacionId;
	}

	public void setPublicacionId(long publicacionId) {
		this.publicacionId = publicacionId;
	}

	public Boolean getFlagReaccion() {
		return flagReaccion;
	}

	public void setFlagReaccion(Boolean flagReaccion) {
		this.flagReaccion = flagReaccion;
	}

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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
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
