package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GastosSolicitud extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(nullable = true, name = "id_agencia")
	private Agencia agencia;

	@ManyToOne
	@JoinColumn(nullable = true, name = "id_empleado")
	private Empleado empleado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_gasto_tipo")
	private GastoConceptoTipo gastoConceptoTipo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_gasto_concepto")
	private GastoConcepto gastoConcepto;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_gasto_concepto_detalle")
	private GastoConceptoDetalle gastoConceptoDetalle;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_centro_costo")
	private CentroCosto centroCosto;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_distribucion_concepto_agencia_periodo")
	private GastoPresupuestoDistribucionConceptoAgenciaPeriodo periodo;
	
	private double montoGasto;
	
	private Boolean terminosCondiciones;
	
	private String motivo;
	
	private String estadoGasto;
	
}
