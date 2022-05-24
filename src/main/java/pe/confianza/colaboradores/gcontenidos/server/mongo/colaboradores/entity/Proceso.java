package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "procesos")
public class Proceso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	private ObjectId _id;
	private String id;
	private String descripcion;
	private Integer vigente;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getVigente() {
		return vigente;
	}
	public void setVigente(Integer vigente) {
		this.vigente = vigente;
	}

}
