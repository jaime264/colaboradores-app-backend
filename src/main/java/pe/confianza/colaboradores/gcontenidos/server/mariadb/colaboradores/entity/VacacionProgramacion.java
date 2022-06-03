package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;

import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;

@Entity
@Table(name = "vacacion_programacion")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "VacacionProgramacion.programacionContarPorUnidadNegocio",
			procedureName = "proc_vacacion_programacion_unidad_negocio", parameters = {
					@StoredProcedureParameter(mode = ParameterMode.IN, name = "idEmplado", type = Long.class),
					@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaInicioProgramacion", type = String.class),
					@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaFinProgramacion", type = String.class),
					@StoredProcedureParameter(mode = ParameterMode.OUT, name = "cantidad", type = Long.class)
			}),
	@NamedStoredProcedureQuery(name = "VacacionProgramacion.programacionContarPorCorredorYPuesto",
	procedureName = "proc_vacacion_programacion_corredor_puesto", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "idEmplado", type = Long.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcionPuesto", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaInicioProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaFinProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "cantidad", type = Long.class)
	}),
	@NamedStoredProcedureQuery(name = "VacacionProgramacion.programacionContarPorTerrirotioYPuesto",
	procedureName = "proc_vacacion_programacion_territorio_puesto", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "idEmplado", type = Long.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcionPuesto", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaInicioProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaFinProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "cantidad", type = Long.class)
	}),
	@NamedStoredProcedureQuery(name = "VacacionProgramacion.programacionContarPorPuesto",
	procedureName = "proc_vacacion_programacion_puesto", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "idEmplado", type = Long.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcionPuesto", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaInicioProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaFinProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "cantidad", type = Long.class)
	}),
	@NamedStoredProcedureQuery(name = "VacacionProgramacion.programacionContarPorAgencia",
	procedureName = "proc_vacacion_programacion_agencia_puesto", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "idEmplado", type = Long.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "descripcionPuesto", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaInicioProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "strFechaFinProgramacion", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "cantidad", type = Long.class)
	})
})
public class VacacionProgramacion extends EntidadAuditoria implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaInicio;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaFin;
	
	@Column(nullable = false)
	private int numeroDias;
	
	private int orden;
	
	private int idEstado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idPeriodo")
	private PeriodoVacacion periodo;
	
	private Long codigoEmpleado;
	
	private Long secuencia;
	
	private Long numeroPeriodo;
	
	private int numeroSabados;
	
	private int numeroDomingos;
	
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

	public void setPeriodo(PeriodoVacacion periodo) {
		this.periodo = periodo;
	}
	

	public PeriodoVacacion getPeriodo() {
		return periodo;
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

	public int getNumeroDias() {
		return numeroDias;
	}

	public void setNumeroDias(int numeroDias) {
		this.numeroDias = numeroDias;
	}

	public Long getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(Long codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public Long getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}

	public Long getNumeroPeriodo() {
		return numeroPeriodo;
	}

	public void setNumeroPeriodo(Long numeroPeriodo) {
		this.numeroPeriodo = numeroPeriodo;
	}

	public int getNumeroSabados() {
		return numeroSabados;
	}

	public void setNumeroSabados(int numeroSabados) {
		this.numeroSabados = numeroSabados;
	}

	public int getNumeroDomingos() {
		return numeroDomingos;
	}

	public void setNumeroDomingos(int numeroDomingos) {
		this.numeroDomingos = numeroDomingos;
	}
	
	
}
