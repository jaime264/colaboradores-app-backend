package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "vacaciones")
public class Vacacion implements Serializable{

	@Id	private ObjectId _id;
	@Indexed(unique = true, sparse = true, name = "vacacion_id_idx")
	private Integer id;
	private String codigoSpring;
	private String usuarioBT;
	private double cantDiasVencidos;
	private String fechaDiasVencidos;
	private double cantDiasTruncos;
	private String fechaDiasTruncos;
	private String fechaCorte;
	private Auditoria logAuditoria;
	

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

	public String getCodigoSpring() {
		return codigoSpring;
	}

	public void setCodigoSpring(String codigoSpring) {
		this.codigoSpring = codigoSpring;
	}

	public String getUsuarioBT() {
		return usuarioBT;
	}

	public void setUsuarioBT(String usuarioBT) {
		this.usuarioBT = usuarioBT;
	}

	public double getCantDiasVencidos() {
		return cantDiasVencidos;
	}

	public void setCantDiasVencidos(double cantDiasVencidos) {
		this.cantDiasVencidos = cantDiasVencidos;
	}

	public String getFechaDiasVencidos() {
		return fechaDiasVencidos;
	}

	public void setFechaDiasVencidos(String fechaDiasVencidos) {
		this.fechaDiasVencidos = fechaDiasVencidos;
	}

	public double getCantDiasTruncos() {
		return cantDiasTruncos;
	}

	public void setCantDiasTruncos(double cantDiasTruncos) {
		this.cantDiasTruncos = cantDiasTruncos;
	}

	public String getFechaDiasTruncos() {
		return fechaDiasTruncos;
	}

	public void setFechaDiasTruncos(String fechaDiasTruncos) {
		this.fechaDiasTruncos = fechaDiasTruncos;
	}

	public String getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	
	public Auditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(Auditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}




	private static final long serialVersionUID = 1L;	

}
