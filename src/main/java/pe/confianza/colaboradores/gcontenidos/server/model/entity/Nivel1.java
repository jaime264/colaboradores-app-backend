package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "nivel1")
public class Nivel1 implements Serializable{

	@Id	private ObjectId _id;
	private Long id;
	private String descripcion;
	
	public Nivel1() {
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
	
	@Override
    public String toString() {
        return "Nivel1 [_id= " + _id + ", id=" + id + ", descripcion=" + descripcion + "]";
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 2052037685190638957L;
}
