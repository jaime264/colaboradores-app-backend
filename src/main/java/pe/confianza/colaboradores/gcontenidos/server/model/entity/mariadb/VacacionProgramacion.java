package pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;

import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

@Entity
@Table(name = "vacacion_programacion")
public class VacacionProgramacion extends EntidadAuditoria implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false, name = "idEmpleado")
	private Empleado empleado;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaInicio;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaFin;
	
	private int orden;
	
	private int idEstado;
	
	private String periodo;	
	
	@Transient
	private EstadoVacacion estado;
	
	public EstadoVacacion getEstado() {
		return EstadoVacacion.getEstado(this.idEstado);
	}

	public void setEstado(EstadoVacacion estado) {
		this.estado = estado;
		this.idEstado = this.estado.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
}
