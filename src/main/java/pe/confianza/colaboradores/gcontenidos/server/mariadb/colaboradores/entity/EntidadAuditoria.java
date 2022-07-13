package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@MappedSuperclass
public class EntidadAuditoria {
	
	@Column(nullable = false)
	private String usuarioCrea;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaCrea;
	
	@Column(nullable = true)
	private String usuarioModifica;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	@Column(nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaModifica;
	
	@Column(nullable = true, length = 1)
	private String estadoRegistro;
	
	@Column(nullable = true, length = 1)
	private String estadoMigracion;


	public String getUsuarioCrea() {
		return usuarioCrea;
	}


	public void setUsuarioCrea(String usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}


	public LocalDateTime getFechaCrea() {
		return fechaCrea;
	}


	public void setFechaCrea(LocalDateTime fechaCrea) {
		this.fechaCrea = fechaCrea;
	}


	public String getUsuarioModifica() {
		return usuarioModifica;
	}


	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}


	public LocalDateTime getFechaModifica() {
		return fechaModifica;
	}


	public void setFechaModifica(LocalDateTime fechaModifica) {
		this.fechaModifica = fechaModifica;
	}


	public String getEstadoRegistro() {
		return estadoRegistro;
	}


	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}


	public String getEstadoMigracion() {
		return estadoMigracion;
	}


	public void setEstadoMigracion(String estadoMigracion) {
		this.estadoMigracion = estadoMigracion;
	}
	

}
