package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.migracion;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MigracionBase {
	
	@Column(nullable = false)
	private String estado;
	
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaOperacion;
	
	private boolean leidoApp;
	
	private boolean leidoSpring;

	public LocalDateTime getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(LocalDateTime fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public boolean isLeidoApp() {
		return leidoApp;
	}

	public void setLeidoApp(boolean leidoApp) {
		this.leidoApp = leidoApp;
	}

	public boolean isLeidoSpring() {
		return leidoSpring;
	}

	public void setLeidoSpring(boolean leidoSpring) {
		this.leidoSpring = leidoSpring;
	}
	
	

}
