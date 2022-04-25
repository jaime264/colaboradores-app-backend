package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import pe.confianza.colaboradores.gcontenidos.server.bean.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.bean.ReaccionPost;
import pe.confianza.colaboradores.gcontenidos.server.bean.Video;

@Document(collection= "comentario")
public class Comentario implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	@Id private ObjectId _id;
	private Long id;
	private Long idPublicacion;
	private Date fecha;
	private Date fechaInicio;
	private Date fechaFin; 
	private String detalle;
	private List<Imagen> imagenes;
	private List<Video> videos;
	private Integer flgreaccion;
	private List<ReaccionPost> reacciones;
	private String idUsuario;
	private Boolean flAprobacion;
	private LogAuditoria logAuditoria;
	
	
	
	public Boolean getFlAprobacion() {
		return flAprobacion;
	}
	public void setFlAprobacion(Boolean flAprobacion) {
		this.flAprobacion = flAprobacion;
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPublicacion() {
		return idPublicacion;
	}
	public void setIdPublicacion(Long idPublicacion) {
		this.idPublicacion = idPublicacion;
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
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
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
	public Integer getFlgreaccion() {
		return flgreaccion;
	}
	public void setFlgreaccion(Integer flgreaccion) {
		this.flgreaccion = flgreaccion;
	}
	public List<ReaccionPost> getReacciones() {
		return reacciones;
	}
	public void setReacciones(List<ReaccionPost> reacciones) {
		this.reacciones = reacciones;
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
