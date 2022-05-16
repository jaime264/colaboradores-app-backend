package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "dispositivo")
public class Dispositivo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	private ObjectId _id;
	private String usuario;
	private String idDispositivo;
	private String idDispositivoFirebase;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getIdDispositivo() {
		return idDispositivo;
	}
	public void setIdDispositivo(String idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public String getIdDispositivoFirebase() {
		return idDispositivoFirebase;
	}
	public void setIdDispositivoFirebase(String idDispositivoFirebase) {
		this.idDispositivoFirebase = idDispositivoFirebase;
	}
	@Override
	public String toString() {
		return "Dispositivo [_id=" + _id + ", usuario=" + usuario + ", idDispositivo=" + idDispositivo
				+ ", idDispositivoFirebase=" + idDispositivoFirebase + "]";
	}
	
	

}
