package pe.confianza.colaboradores.gcontenidos.server.util;

public enum VacacionesSubTipoParametro {
	
	PROGRAMACION_ANUAL_VACACIONES("PROGRAMACION_ANUAL_VACACIONES", "Programaciones anuales"),
	NOTIFICACIONES_VACACIONES("NOTIFICACIONES_VACACIONES", "Notificaciones"),
	REPROGRAMACION_MENSUAL_VACACIONES("REPROGRAMACION_MENSUAL_VACACIONES", "Reprogramaciones mensuales"),
	ESTADOS_VACIONES("ESTADOS_VACIONES", "Estados"),
	APROBACION_VACACIONES("APROBACION_VACACIONES", "Aprobaciones");
	
	public String codigo;
	public String descripcion;
	
	private VacacionesSubTipoParametro(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

}
