package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "publicacion_usuario")
public class PublicacionUsuario implements Serializable {

	@Id private ObjectId _id;
	private Long idPublicacion;
	private String idUsuario;
	private Integer idReaccion;
	private Integer idReaccionAnterior;
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Long getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(Long idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdReaccion() {
		return idReaccion;
	}

	public void setIdReaccion(Integer idReaccion) {
		this.idReaccion = idReaccion;
	}

	public Integer getIdReaccionAnterior() {
		return idReaccionAnterior;
	}

	public void setIdReaccionAnterior(Integer idReaccionAnterior) {
		this.idReaccionAnterior = idReaccionAnterior;
	}

	private static final long serialVersionUID = 1L;

}
