package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "reacciones")
public class Reaccion implements Serializable{

	@Id	private ObjectId _id;
	@Indexed(unique = true, sparse = true, name = "reaccion_id_idx")
	private Integer id;
	private String nombre;
	private String imagenActiva;
	private String imagenInactiva;
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagenActiva() {
		return imagenActiva;
	}

	public void setImagenActiva(String imagenActiva) {
		this.imagenActiva = imagenActiva;
	}

	public String getImagenInactiva() {
		return imagenInactiva;
	}

	public void setImagenInactiva(String imagenInactiva) {
		this.imagenInactiva = imagenInactiva;
	}

	private static final long serialVersionUID = 1L;	

}
