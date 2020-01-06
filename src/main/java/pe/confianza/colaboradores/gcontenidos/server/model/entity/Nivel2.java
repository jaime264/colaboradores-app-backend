package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "nivel2")
public class Nivel2 implements Serializable{

	@Id private ObjectId _id;
	@Indexed(unique = true, sparse = true, name = "nivel2_id_idx")
	private Long id;
	private String descripcion;
	@DBRef(db = "nivel1", lazy = true) 
	private Nivel1 nivel1;
	
	public Nivel2() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Nivel1 getNivel1() {
		return nivel1;
	}

	public void setNivel1(Nivel1 nivel1) {
		this.nivel1 = nivel1;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303392946858334313L;
}
