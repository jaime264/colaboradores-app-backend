package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@MappedSuperclass
public abstract class MigracionBase {
	
	@Column(nullable = false, length = 1)
	private String estado;
	
	@Column(nullable = false, length = 20)
	private String ultimoUsuario;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime ultimaFechaModi;
	
	@Column(nullable = false, length = 1)
	private String estadoProceso;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	@Column(nullable = true, columnDefinition = "TIMESTAMP" )
	private LocalDateTime fechaProceso;
	
	@Column(nullable = true)
	private String usuarioProceso;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(String ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	public LocalDateTime getUltimaFechaModi() {
		return ultimaFechaModi;
	}

	public void setUltimaFechaModi(LocalDateTime ultimaFechaModi) {
		this.ultimaFechaModi = ultimaFechaModi;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	public LocalDateTime getFechaProceso() {
		return fechaProceso;
	}

	public void setFechaProceso(LocalDateTime fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getUsuarioProceso() {
		return usuarioProceso;
	}

	public void setUsuarioProceso(String usuarioProceso) {
		this.usuarioProceso = usuarioProceso;
	}

	

	

	
	
	

}
