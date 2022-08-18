package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gasto_presupuesto_concepto_agencia")
public class GastoPresupuestoDistribucionConceptoAgencia extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_agencia")
	private Agencia agencia;
	
	@Column(nullable = false)
	private double presupuesto;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_distribucion_concepto")
	private GastoPresupuestoDistribucionConcepto distribucionConcepto;

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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public GastoPresupuestoDistribucionConcepto getDistribucionConcepto() {
		return distribucionConcepto;
	}

	public void setDistribucionConcepto(GastoPresupuestoDistribucionConcepto distribucionConcepto) {
		this.distribucionConcepto = distribucionConcepto;
	}	

}
