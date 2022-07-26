package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imp_agencia")
public class MigracionAgencia extends MigracionBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 4)
	private String codigo;
	
	@Column(nullable = false,  length = 5)
	private String codigoCorredor;
	
	@Column(nullable = false, length = 50)
	private String descripcion;
	
	@Column(nullable = true, length = 4)
	private String codigoAgenciaMatriz;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCorredor() {
		return codigoCorredor;
	}

	public void setCodigoCorredor(String codigoCorredor) {
		this.codigoCorredor = codigoCorredor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoAgenciaMatriz() {
		return codigoAgenciaMatriz;
	}

	public void setCodigoAgenciaMatriz(String codigoAgenciaMatriz) {
		this.codigoAgenciaMatriz = codigoAgenciaMatriz;
	}
	
	
}
