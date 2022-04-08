package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@Document(collection= "vacaciones")
public class Vacacion implements Serializable{

	@Id	private ObjectId _id;
	private Integer id;
	private String codigoSpring;
	private String usuarioBT;
	private double cantDiasVencidos;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaDiasVencidos;
	private double cantDiasTruncos;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaDiasTruncos;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaCorte;
	private LogAuditoria logAuditoria;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaIngreso;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private Date fechaFinContrato;
	private double metaAnualVacaciones;
	
	
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(Date fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public double getMetaAnualVacaciones() {
		return metaAnualVacaciones;
	}

	public void setMetaAnualVacaciones(double metaAnualVacaciones) {
		this.metaAnualVacaciones = metaAnualVacaciones;
	}

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

	public Date getFechaDiasVencidos() {
		return fechaDiasVencidos;
	}

	public void setFechaDiasVencidos(Date fechaDiasVencidos) {
		this.fechaDiasVencidos = fechaDiasVencidos;
	}

	public double getCantDiasTruncos() {
		return cantDiasTruncos;
	}

	public void setCantDiasTruncos(double cantDiasTruncos) {
		this.cantDiasTruncos = cantDiasTruncos;
	}

	public Date getFechaDiasTruncos() {
		return fechaDiasTruncos;
	}

	public void setFechaDiasTruncos(Date fechaDiasTruncos) {
		this.fechaDiasTruncos = fechaDiasTruncos;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	
	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}


	private static final long serialVersionUID = 1L;	

}
