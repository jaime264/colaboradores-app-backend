package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT "
		+ "id_empleado, nombres, apellido_paterno, apellido_materno, "
		+ "aprobador_nivel1_id, aprobador_nivel1_usuariobt, aprobador_nivel1_nombres, aprobador_nivel1_apellido_paterno, aprobador_nivel1_apellido_materno, "
		+ "aprobador_nivel1_email, "
		+ "id_meta, meta, meta_inicial, anio, "
		+ "periodo_vencido_dias_registrados_gozar, periodo_vencido_dias_generados_gozar, periodo_vencido_dias_aprobados_gozar, "
		+ "periodo_trunco_dias_registrados_gozar, periodo_trunco_dias_generados_gozar, periodo_trunco_dias_aprobados_gozar "
		+ "FROM vacacion_periodo_resumen")
public class VacacionPeriodoResumen {
	
	@Id
	private Long idMeta;
	private Long idEmpleado;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	@Column(name = "aprobador_nivel1_id")
	private Long aprobadorNivel1Id;
	@Column(name = "aprobador_nivel1_usuariobt")
	private String aprobadorNivel1Usuariobt;
	@Column(name = "aprobador_nivel1_nombres")
	private String aprobadorNivel1Nombres;
	@Column(name = "aprobador_nivel1_apellido_paterno")
	private String aprobadorNivel1ApellidoPaterno;
	@Column(name = "aprobador_nivel1_apellido_materno")
	private String aprobadorNivel1ApellidoMaterno;
	@Column(name = "aprobador_nivel1_email")
	private String aprobadorNivel1Email;
	private Double meta;
	private Double metaInicial;
	private Integer anio;
	private Double periodoVencidoDiasRegistradosGozar;
	private Double periodoVencidoDiasGeneradosGozar;
	private Double periodoVencidoDiasAprobadosGozar;
	private Double periodoTruncoDiasRegistradosGozar;
	private Double periodoTruncoDiasGeneradosGozar;
	private Double periodoTruncoDiasAprobadosGozar;
	public Long getIdMeta() {
		return idMeta;
	}
	public void setIdMeta(Long idMeta) {
		this.idMeta = idMeta;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public Long getAprobadorNivel1Id() {
		return aprobadorNivel1Id;
	}
	public void setAprobadorNivel1Id(Long aprobadorNivel1Id) {
		this.aprobadorNivel1Id = aprobadorNivel1Id;
	}
	public String getAprobadorNivel1Usuariobt() {
		return aprobadorNivel1Usuariobt;
	}
	public void setAprobadorNivel1Usuariobt(String aprobadorNivel1Usuariobt) {
		this.aprobadorNivel1Usuariobt = aprobadorNivel1Usuariobt;
	}
	public String getAprobadorNivel1Nombres() {
		return aprobadorNivel1Nombres;
	}
	public void setAprobadorNivel1Nombres(String aprobadorNivel1Nombres) {
		this.aprobadorNivel1Nombres = aprobadorNivel1Nombres;
	}
	public String getAprobadorNivel1ApellidoPaterno() {
		return aprobadorNivel1ApellidoPaterno;
	}
	public void setAprobadorNivel1ApellidoPaterno(String aprobadorNivel1ApellidoPaterno) {
		this.aprobadorNivel1ApellidoPaterno = aprobadorNivel1ApellidoPaterno;
	}
	public String getAprobadorNivel1ApellidoMaterno() {
		return aprobadorNivel1ApellidoMaterno;
	}
	public void setAprobadorNivel1ApellidoMaterno(String aprobadorNivel1ApellidoMaterno) {
		this.aprobadorNivel1ApellidoMaterno = aprobadorNivel1ApellidoMaterno;
	}
	public String getAprobadorNivel1Email() {
		return aprobadorNivel1Email;
	}
	public void setAprobadorNivel1Email(String aprobadorNivel1Email) {
		this.aprobadorNivel1Email = aprobadorNivel1Email;
	}
	public Double getMeta() {
		return meta;
	}
	public void setMeta(Double meta) {
		this.meta = meta;
	}
	public Double getMetaInicial() {
		return metaInicial;
	}
	public void setMetaInicial(Double metaInicial) {
		this.metaInicial = metaInicial;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Double getPeriodoVencidoDiasRegistradosGozar() {
		return periodoVencidoDiasRegistradosGozar;
	}
	public void setPeriodoVencidoDiasRegistradosGozar(Double periodoVencidoDiasRegistradosGozar) {
		this.periodoVencidoDiasRegistradosGozar = periodoVencidoDiasRegistradosGozar;
	}
	public Double getPeriodoVencidoDiasGeneradosGozar() {
		return periodoVencidoDiasGeneradosGozar;
	}
	public void setPeriodoVencidoDiasGeneradosGozar(Double periodoVencidoDiasGeneradosGozar) {
		this.periodoVencidoDiasGeneradosGozar = periodoVencidoDiasGeneradosGozar;
	}
	public Double getPeriodoVencidoDiasAprobadosGozar() {
		return periodoVencidoDiasAprobadosGozar;
	}
	public void setPeriodoVencidoDiasAprobadosGozar(Double periodoVencidoDiasAprobadosGozar) {
		this.periodoVencidoDiasAprobadosGozar = periodoVencidoDiasAprobadosGozar;
	}
	public Double getPeriodoTruncoDiasRegistradosGozar() {
		return periodoTruncoDiasRegistradosGozar;
	}
	public void setPeriodoTruncoDiasRegistradosGozar(Double periodoTruncoDiasRegistradosGozar) {
		this.periodoTruncoDiasRegistradosGozar = periodoTruncoDiasRegistradosGozar;
	}
	public Double getPeriodoTruncoDiasGeneradosGozar() {
		return periodoTruncoDiasGeneradosGozar;
	}
	public void setPeriodoTruncoDiasGeneradosGozar(Double periodoTruncoDiasGeneradosGozar) {
		this.periodoTruncoDiasGeneradosGozar = periodoTruncoDiasGeneradosGozar;
	}
	public Double getPeriodoTruncoDiasAprobadosGozar() {
		return periodoTruncoDiasAprobadosGozar;
	}
	public void setPeriodoTruncoDiasAprobadosGozar(Double periodoTruncoDiasAprobadosGozar) {
		this.periodoTruncoDiasAprobadosGozar = periodoTruncoDiasAprobadosGozar;
	}
	
		

}
