package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imp_puesto")
public class MigracionPuesto extends MigracionBase{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private long codigo;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = true)
	private String codigoClasificacion;
	
	@Column(nullable = true)
	private String codigoTipoCalificacion;
	
	@Column(nullable = true)
	private String codigoTipoCargo;
	
	@Column(nullable = true)
	private Long codigoPuestoSuperior;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoClasificacion() {
		return codigoClasificacion;
	}

	public void setCodigoClasificacion(String codigoClasificacion) {
		this.codigoClasificacion = codigoClasificacion;
	}

	public String getCodigoTipoCalificacion() {
		return codigoTipoCalificacion;
	}

	public void setCodigoTipoCalificacion(String codigoTipoCalificacion) {
		this.codigoTipoCalificacion = codigoTipoCalificacion;
	}

	public String getCodigoTipoCargo() {
		return codigoTipoCargo;
	}

	public void setCodigoTipoCargo(String codigoTipoCargo) {
		this.codigoTipoCargo = codigoTipoCargo;
	}

	public Long getCodigoPuestoSuperior() {
		return codigoPuestoSuperior;
	}

	public void setCodigoPuestoSuperior(Long codigoPuestoSuperior) {
		this.codigoPuestoSuperior = codigoPuestoSuperior;
	}

	
	
	

}
