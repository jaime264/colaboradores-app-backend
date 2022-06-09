package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


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
	private Boolean flagAprobacion;
	
	@Column(nullable = true)
	private Boolean activo;
	
	@OneToMany(mappedBy = "comentario",  fetch = FetchType.LAZY)
	private List<Imagen> imagenes;
	
	@OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY)
	private List<Video> videos;
	
	@OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY)
	private List<OcultarComentario> comentariosOcultos;

	@Column(nullable = true)
	private String usuarioBt;
	
	@Transient
	private long publicacionId;

	@JsonInclude
	@Transient
	private String sexo;
	
	@JsonInclude
	@Transient
	private String nombre;	

	public List<OcultarComentario> getComentariosOcultos() {
		return comentariosOcultos;
	}

	public void setComentariosOcultos(List<OcultarComentario> comentariosOcultos) {
		this.comentariosOcultos = comentariosOcultos;
	}

	public Boolean getFlagAprobacion() {
		return flagAprobacion;
	}

	public void setFlagAprobacion(Boolean flagAprobacion) {
		this.flagAprobacion = flagAprobacion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getPublicacionId() {
		return publicacionId;
	}

	public void setPublicacionId(long publicacionId) {
		this.publicacionId = publicacionId;
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

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public List<Video> getVideos() {
		return videos;
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

	public String getUsuarioBt() {
		return usuarioBt;
	}

	public void setUsuarioBt(String usuarioBt) {
		this.usuarioBt = usuarioBt;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	
	
}
