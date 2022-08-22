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
	@JoinColumn(nullable = true, name = "idAgencia")
	private Agencia idAgencia;

	@ManyToOne
	@JoinColumn(nullable = true, name = "idEmpleado")
	private Empleado idEmpleado;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idGastoConceptoTipo")
	private GastoConceptoTipo idGastoConceptoTipo;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_gasto_concepto_detalle")
	private GastoConceptoDetalle gastoConceptoDetalle;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "id_presupuesto_periodo")
	private PresupuestoPeriodoGasto periodo;
	
	private double montoGasto;
	
	private Boolean terminosCondiciones;
	
	private String motivo;
	
	
//	private GastoCentroCosto idGastroCentroCosto;
	
}
