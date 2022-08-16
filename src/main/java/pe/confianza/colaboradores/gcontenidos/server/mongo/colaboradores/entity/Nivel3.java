package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection= "nivel3")
public class Nivel3 implements Serializable{	
	
	@Id	private ObjectId _id;
	private Long id;
	private String descripcion;
	private Nivel2 nivel2;
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Nivel2 getNivel2() {
		return nivel2;
	}

	public void setNivel2(Nivel2 nivel2) {
		this.nivel2 = nivel2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196668524429045216L;

}
