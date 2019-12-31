package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "nivel1")
public class Nivel1 implements Serializable{

	@Id
	private Long _id;
	private String descripcion;
	
	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
    public String toString() {
        return "Nivel1 [id=" + _id + ", descripcion=" + descripcion + "]";
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 2052037685190638957L;
}
