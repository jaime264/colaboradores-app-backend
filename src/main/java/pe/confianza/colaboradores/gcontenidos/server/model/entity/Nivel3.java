package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "nivel3")
public class Nivel3 implements Serializable{	
	
	@Id
	private Long _id;	
	private String descripcion;
	private Nivel2 nivel2;
		
	public Long get_id() {
		return _id;
	}

	public void set_id(Long id) {
		this._id = id;
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
