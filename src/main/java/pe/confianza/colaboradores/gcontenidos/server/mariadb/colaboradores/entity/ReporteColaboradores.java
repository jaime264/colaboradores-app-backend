package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Immutable
@Subselect("SELECT id, "
		+ "codigo, "
		+ "usuariobt, "
		+ "nombres, "
		+ "apellido_materno, "
		+ "apellido_paterno, "
		+ "codigo_nivel1, "
		+ "codigo_nivel2, "
		+ "puesto, "
		+ "colectivo, "
		+ "fecha_ingreso, "
		+ "agencia, "
		+ "corredor, "
		+ "territorio, "
		+ "meta, "
		+ "dias_gozados, "
		+ "dias_aprobados_gozar "
		+ "FROM vacaciones.reporte_seguimiento" + "")
public class ReporteColaboradores {

	@Id
	private Long id;
	
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "usuariobt")
	private String usuarioBt;
	
	@Column(name = "nombres")
	private String nombres;
	
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@Column(name = "codigo_nivel1")
	@JsonIgnore
	private String codigoNivel1;
	
	@Column(name = "codigo_nivel2")
	@JsonIgnore
	private String codigoNivel2;
	
	@Column(name = "puesto")
	private String puesto;
	
	@Column(name = "colectivo")
	private String colectivo;
	
	@Column(name = "fecha_ingreso")
	private LocalDate fechaIngreso;
	
	@Column(name = "agencia")
	private String agencia;
	
	@Column(name = "corredor")
	private String corredor;
	
	@Column(name = "territorio")
	private String territorio;
	
	@Column(name = "meta")
	private int meta;
	
	@Column(name = "dias_gozados")
	private int diasGozados;
	
	@Column(name = "dias_aprobados_gozar")
	private int diasAprobadosGozar;
	
	@Transient
	private String division;
	
	@Transient	
	private int diasProgramados;
	
	@Transient
	private int diasNoProgramados;
	
	@Transient
	private double porcentajeAvance;
	
	public String getNombreCompleto(){
		return this.nombres + " "+ this.apellidoPaterno +" " + this.apellidoMaterno;
	}

}
