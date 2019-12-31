package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "nivel2")
public class Nivel2 implements Serializable{

	@Id
	private Long _id;
	private String descripcion;
	private Nivel1 nivel1;
	
	
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
