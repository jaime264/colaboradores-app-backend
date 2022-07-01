package pe.confianza.colaboradores.gcontenidos.server.util;

public enum EstadoVacacion {
	
	REGISTRADO(1,  "PARAMVAC006"),
	GENERADO(2,  "PARAMVAC007"),
	APROBADO(3,  "PARAMVAC008"),
	RECHAZADO(4,  "PARAMVAC009"),
	GOZANDO(5,  "PARAMVAC010"),
	GOZADO(6,  "PARAMVAC011");
	
	public int id;
	public String codigoParametro;
	
	private EstadoVacacion(int id, String codigoParametro) {
		this.id = id;
		this.codigoParametro = codigoParametro;
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
