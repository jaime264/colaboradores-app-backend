package pe.confianza.colaboradores.gcontenidos.server.util;

public enum EstadoVacacion {
	
	REGISTRADO(1, "Registrado"),
	GENERADO(2, "Generado"),
	APROBADO(3, "Aprobado"),
	RECHAZADO(4, "Rechazado"),
	GOZADO(5, "Gozado");
	
	public int id;
	public String descripcion;
	
	private EstadoVacacion(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	
	public static EstadoVacacion getEstado(int id) {
		for (EstadoVacacion estado : EstadoVacacion.values()) {
			if(estado.id == id) {
				return estado;
			}
		}
		return null;
	}

}
