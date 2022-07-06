package pe.confianza.colaboradores.gcontenidos.server.util;

public enum EstadoVacacion {
	
	REGISTRADO(1,  "PARAMVAC006", "PARAMVAC015"),
	GENERADO(2,  "PARAMVAC007", "PARAMVAC016"),
	APROBADO(3,  "PARAMVAC008", "PARAMVAC017"),
	RECHAZADO(4,  "PARAMVAC009", "PARAMVAC018"),
	GOZANDO(5,  "PARAMVAC010", "PARAMVAC019"),
	GOZADO(6,  "PARAMVAC011", "PARAMVAC020"),
	REPROGRAMADO(7, "PARAMVAC014", "PARAMVAC021");
	
	public int id;
	public String codigoParametro;
	public String codigoParametroLeyenda;
	
	private EstadoVacacion(int id, String codigoParametro, String codigoParametroLeyenda) {
		this.id = id;
		this.codigoParametro = codigoParametro;
		this.codigoParametroLeyenda = codigoParametroLeyenda;
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
