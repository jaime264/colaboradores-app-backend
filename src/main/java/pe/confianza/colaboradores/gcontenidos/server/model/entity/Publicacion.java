package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import pe.confianza.colaboradores.gcontenidos.server.bean.Imagen;
import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.bean.ReaccionPost;
import pe.confianza.colaboradores.gcontenidos.server.bean.Usuario;
import pe.confianza.colaboradores.gcontenidos.server.bean.Video;

@Document(collection= "publicacion")
public class Publicacion implements Serializable{
		
	@Id private ObjectId _id;
	@Indexed(unique = true, sparse = true, name = "post_id_idx")
	private Long id;
	private String titulo;
	private Long idnivel1;
	private String descnivel1;
	private Long idnivel2;
	private String descnivel2;
	private Long idnivel3;
	private String descnivel3;
	private Long fecha;
	private Long fechaInicio;
	private Long fechaFin; 
	private String detalle;
	private Integer flgmultimedia;
	private Integer flgpermanente;
	private List<Imagen> imagenes;
	private List<Video> videos;
	private Integer flgreaccion;
	private List<ReaccionPost> reacciones;
	private List<Usuario> usuarios;
	private LogAuditoria logAuditoria;
	
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getIdnivel1() {
		return idnivel1;
	}

	public void setIdnivel1(Long idnivel1) {
		this.idnivel1 = idnivel1;
	}

	public String getDescnivel1() {
		return descnivel1;
	}

	public void setDescnivel1(String descnivel1) {
		this.descnivel1 = descnivel1;
	}

	public Long getIdnivel2() {
		return idnivel2;
	}

	public void setIdnivel2(Long idnivel2) {
		this.idnivel2 = idnivel2;
	}

	public String getDescnivel2() {
		return descnivel2;
	}

	public void setDescnivel2(String descnivel2) {
		this.descnivel2 = descnivel2;
	}

	public Long getIdnivel3() {
		return idnivel3;
	}

	public void setIdnivel3(Long idnivel3) {
		this.idnivel3 = idnivel3;
	}

	public String getDescnivel3() {
		return descnivel3;
	}

	public void setDescnivel3(String descnivel3) {
		this.descnivel3 = descnivel3;
	}

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	public Long getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Long getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Integer getFlgmultimedia() {
		return flgmultimedia;
	}

	public void setFlgmultimedia(Integer flgmultimedia) {
		this.flgmultimedia = flgmultimedia;
	}

	public Integer getFlgpermanente() {
		return flgpermanente;
	}

	public void setFlgpermanente(Integer flgpermanente) {
		this.flgpermanente = flgpermanente;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
