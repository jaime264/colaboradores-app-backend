package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

@Document(collection= "vacacion_programaciones")
public class VacacionProgramacion implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id	private ObjectId _id;
	private Long id;
	private String codigoSpring;
	private String usuarioBT;
	private Date fechaInicio;
	private Date fechaFin;
	private int idEstado;
	private String periodo;
	private LogAuditoria logAuditoria;
	
	@Transient
	private EstadoVacacion estado;

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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public LogAuditoria getLogAuditoria() {
		return logAuditoria;
	}

	public void setLogAuditoria(LogAuditoria logAuditoria) {
		this.logAuditoria = logAuditoria;
	}

	public EstadoVacacion getEstado() {
		return EstadoVacacion.getEstado(this.idEstado);
	}

	public void setEstado(EstadoVacacion estado) {
		this.estado = estado;
		this.idEstado = this.estado.id;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
	
	
	
}
