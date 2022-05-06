package pe.confianza.colaboradores.gcontenidos.server.model.entity.mariadb;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@MappedSuperclass
public class EntidadAuditoria {
	
	@Column(nullable = false)
	private String usuarioCrea;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	@Column(nullable = false)
	private LocalDate fechaCrea;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = Constantes.TIME_ZONE)
	@Column(nullable = true)
	private String usuarioModifica;
	
	
	@Column(nullable = true)
	private LocalDate fechaModifica;


	public String getUsuarioCrea() {
		return usuarioCrea;
	}


	public void setUsuarioCrea(String usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}


	public LocalDate getFechaCrea() {
		return fechaCrea;
	}


	public void setFechaCrea(LocalDate fechaCrea) {
		this.fechaCrea = fechaCrea;
	}


	public String getUsuarioModifica() {
		return usuarioModifica;
	}


	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}


	public LocalDate getFechaModifica() {
		return fechaModifica;
	}


	public void setFechaModifica(LocalDate fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	
	

}
