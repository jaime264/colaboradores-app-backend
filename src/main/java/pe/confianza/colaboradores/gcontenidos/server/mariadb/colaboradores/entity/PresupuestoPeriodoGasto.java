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
@Table(name = "presupuesto_periodo_gasto")
public class PresupuestoPeriodoGasto extends EntidadAuditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_presupuesto_agencia")
	private PresupuestoAgenciaGasto presupuestoAgencia;
	
	private double presupuestoAsignado;
	
	private double presupuestoConsumido;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaInicio;
	
	@Column(columnDefinition = "DATE" )
	private LocalDate fechaFin;
	
	private boolean actual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public PresupuestoAgenciaGasto getPresupuestoAgencia() {
		return presupuestoAgencia;
	}

	public void setPresupuestoAgencia(PresupuestoAgenciaGasto presupuestoAgencia) {
		this.presupuestoAgencia = presupuestoAgencia;
	}

	public double getPresupuestoAsignado() {
		return presupuestoAsignado;
	}

	public void setPresupuestoAsignado(double presupuestoAsignado) {
		this.presupuestoAsignado = presupuestoAsignado;
	}

	public double getPresupuestoConsumido() {
		return presupuestoConsumido;
	}

	public void setPresupuestoConsumido(double presupuestoConsumido) {
		this.presupuestoConsumido = presupuestoConsumido;
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

	public boolean isActual() {
		return actual;
	}

	public void setActual(boolean actual) {
		this.actual = actual;
	}
	
	

}
