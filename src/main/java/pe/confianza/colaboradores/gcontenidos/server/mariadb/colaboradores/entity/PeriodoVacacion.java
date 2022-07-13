package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "vacacion_periodo")
@NamedStoredProcedureQuery(name = "PeriodoVacacion.consolidarResumenDias",
	procedureName = "proc_vacacion_periodo_actualizar_dias", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "periodoId", type = Long.class)
	}
		)
public class PeriodoVacacion extends EntidadAuditoria{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Integer numero;
	
	private String descripcion;
	
	private int anio;
	
	private int mes;
	
	private Double derecho;
	
	private Double diasPendientesGozar;
	
	private Double diasRegistradosGozar;
	
	private Double diasGeneradosGozar;
	
	private Double diasAprobadosGozar;
	
	private Double diasGozados;
	
	private Double diasIndemnizables;
	
	private boolean completado;
	
	private String fuente;
	
	private long codigoEmpleado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idEmpleado")
	private Empleado empleado;
	
	private LocalDate fechaInicioPeriodo;
	
	private LocalDate fechaFinPeriodo;
	
	private LocalDate fechaLimiteIndemnizacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public Double getDerecho() {
		return derecho;
	}

	public void setDerecho(Double derecho) {
		this.derecho = derecho;
	}

	

	public Double getDiasPendientesGozar() {
		return diasPendientesGozar;
	}

	public void setDiasPendientesGozar(Double diasPendientesGozar) {
		this.diasPendientesGozar = diasPendientesGozar;
	}

	public Double getDiasRegistradosGozar() {
		return diasRegistradosGozar;
	}

	public void setDiasRegistradosGozar(Double diasRegistradosGozar) {
		this.diasRegistradosGozar = diasRegistradosGozar;
	}

	public Double getDiasGeneradosGozar() {
		return diasGeneradosGozar;
	}

	public void setDiasGeneradosGozar(Double diasGeneradosGozar) {
		this.diasGeneradosGozar = diasGeneradosGozar;
	}

	public Double getDiasAprobadosGozar() {
		return diasAprobadosGozar;
	}

	public void setDiasAprobadosGozar(Double diasAprobadosGozar) {
		this.diasAprobadosGozar = diasAprobadosGozar;
	}

	public Double getDiasGozados() {
		return diasGozados;
	}

	public void setDiasGozados(Double diasGozados) {
		this.diasGozados = diasGozados;
	}

	public Double getDiasIndemnizables() {
		return diasIndemnizables;
	}

	public void setDiasIndemnizables(Double diasIndemnizables) {
		this.diasIndemnizables = diasIndemnizables;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public long getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(long codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public LocalDate getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}

	public void setFechaInicioPeriodo(LocalDate fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}

	public LocalDate getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}

	public void setFechaFinPeriodo(LocalDate fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

	public LocalDate getFechaLimiteIndemnizacion() {
		return fechaLimiteIndemnizacion;
	}

	public void setFechaLimiteIndemnizacion(LocalDate fechaLimiteIndemnizacion) {
		this.fechaLimiteIndemnizacion = fechaLimiteIndemnizacion;
	}

	@Override
	public String toString() {
		return "PeriodoVacacion [id=" + id + ", anio=" + anio + ", codigoEmpleado=" + codigoEmpleado + "]";
	}
	
	public boolean programacionDentroPeriodoGoce(VacacionProgramacion programacion) {
		LocalDate fechaInicioPeriodo = this.fechaInicioPeriodo.minusDays(1);
		LocalDate fechaMaximaGoce = this.fechaLimiteIndemnizacion.plusDays(1);
		boolean respuesta = true;;
		if(fechaInicioPeriodo.isAfter(programacion.getFechaInicio()))
			respuesta = false;
		if(fechaMaximaGoce.isBefore(programacion.getFechaFin()))
			respuesta = false;
		return respuesta;
		
	}
	
	public boolean programacionDentroPeriodoGoce(LocalDate fechaInicio, LocalDate fechaFin) {
		LocalDate fechaInicioPeriodo = this.fechaInicioPeriodo.minusDays(1);
		LocalDate fechaMaximaGoce = this.fechaLimiteIndemnizacion.plusDays(1);
		boolean respuesta = true;
		if(fechaInicioPeriodo.isAfter(fechaInicio))
			respuesta = false;
		if(fechaMaximaGoce.isBefore(fechaFin))
			respuesta = false;
		return respuesta;
	}

	
	
	

}
