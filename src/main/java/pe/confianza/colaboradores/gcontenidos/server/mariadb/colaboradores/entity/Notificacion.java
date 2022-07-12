package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notificacion")
public class Notificacion extends EntidadAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String descripcion;
	
	@Column(nullable = false)
	private String data;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idEmpleado")
	private Empleado empleado;
	
	@Column(nullable = false)
	private boolean visto;
	
	@Column(nullable = false)
	private boolean enviadoCorreo;
	
	@Column(nullable = false)
	private boolean enviadoApp;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "idTipo")
	private NotificacionTipo tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public boolean isVisto() {
		return visto;
	}

	public void setVisto(boolean visto) {
		this.visto = visto;
	}

	public boolean isEnviadoCorreo() {
		return enviadoCorreo;
	}

	public void setEnviadoCorreo(boolean enviadoCorreo) {
		this.enviadoCorreo = enviadoCorreo;
	}

	public boolean isEnviadoApp() {
		return enviadoApp;
	}

	public void setEnviadoApp(boolean enviadoApp) {
		this.enviadoApp = enviadoApp;
	}

	public NotificacionTipo getTipo() {
		return tipo;
	}

	public void setTipo(NotificacionTipo tipo) {
		this.tipo = tipo;
	}

	
	
	
}
