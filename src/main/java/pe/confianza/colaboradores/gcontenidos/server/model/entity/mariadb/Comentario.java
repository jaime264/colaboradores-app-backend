package pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;


@Entity
@Table(name = "comentario")
public class Comentario {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(nullable = true, name = "idPublciacion")
	private Publicacion publicacion;
	
	private String descripcion;
	private Boolean flAprobacion;
	private Date fecha;
	private Date fechaInicio;
	private Date fechaFin; 
	private Integer flgreaccion;
	private Integer reacciones;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "idImagen")
	private List<Imagen> imagenes;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "idVideo")
	private List<Video> videos;

	private String idUsuario;
	
	private LogAuditoria logAuditoria;

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

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	
	
	
	
}
