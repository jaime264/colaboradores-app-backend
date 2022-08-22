package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gasto_presupuesto_concepto_agencia_periodo")
public class GastoPresupuestoDistribucionConceptoAgenciaPeriodo extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "DATE")
	private LocalDate fechaInicio;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaFin;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_distribucion_agencia")
	private GastoPresupuestoDistribucionConceptoAgencia distribucionAgencia;
	
	private double presupuesto;
	
	private double presupuestoUsado;
	
	private boolean actual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public GastoPresupuestoDistribucionConceptoAgencia getDistribucionAgencia() {
		return distribucionAgencia;
	}

	public void setDistribucionAgencia(GastoPresupuestoDistribucionConceptoAgencia distribucionAgencia) {
		this.distribucionAgencia = distribucionAgencia;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public double getPresupuestoUsado() {
		return presupuestoUsado;
	}

	public void setPresupuestoUsado(double presupuestoUsado) {
		this.presupuestoUsado = presupuestoUsado;
	}

	public boolean isActual() {
		return actual;
	}

	public void setActual(boolean actual) {
		this.actual = actual;
	}
	
	

}
