package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CentroCosto extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String costcenter;

	@Column(nullable = false)
	private String localname;
	
	@Column(nullable = false)
	private String CostCenterClasification;
	
	@Column(nullable = false)
	private String CostCenterGroup;
	
	@Column(nullable = false)
	private String CostCenterSubGroup;
	
	@Column(nullable = false)
	private String CostCenterNext;
	
	@Column(nullable = false)
	private int vendor;
	
	@Column(nullable = false)
	private char status;
	
	@Column(nullable = false)
	private String LastUser;
	
	@Column(nullable = false)
	private LocalDate Lastdate;
	
	@Column(nullable = false)
	private String Sucursal;
	
	@Column(nullable = false)
	private char estado_proceso;
	
	@Column(nullable = false)
	private String fecha_proceso;
	
	@Column(nullable = false)
	private String usuario_proceso;

}
