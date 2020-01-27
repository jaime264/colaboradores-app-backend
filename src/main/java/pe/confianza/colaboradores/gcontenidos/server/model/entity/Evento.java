package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "eventos")
public class Evento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	private ObjectId _id;
	private Long id;
	private String titulo;
	private String detalle;
	private Long registro;
	private Long fechaEvento;
	private Long fechaInicio;
	private Long fechaFin;
	
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
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Long getRegistro() {
		return registro;
	}
	public void setRegistro(Long registro) {
		this.registro = registro;
	}
	public Long getFechaEvento() {
		return fechaEvento;
	}
	public void setFechaEvento(Long fechaEvento) {
		this.fechaEvento = fechaEvento;
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

}
