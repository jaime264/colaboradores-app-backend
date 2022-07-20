package pe.confianza.colaboradores.gcontenidos.server.util;

public enum VacacionesSubTipoParametro {
	
	POLITICAS_REGULATORIAS("POLITICAS_REGULATORIAS", "Políticas regulatorias"),
	POLITICAS_BOLSAS("POLITICAS_BOLSAS", "Políticas de bolsas"),
	PROGRAMACION_ANUAL_VACACIONES("PROGRAMACION_ANUAL_VACACIONES", "Programación anual"),
	REPROGRAMACION_MENSUAL_VACACIONES("REPROGRAMACION_MENSUAL_VACACIONES", "Reprogramación mensual"),
	REPORTES("REPORTES", "Reportes"),
	ESTADOS_VACIONES("ESTADOS_VACIONES", "Estados"),
	NOTIFICACIONES("NOTIFICACIONES", "Notificaciones"),
	DIAS_META("DIAS_META", "Días meta");
	
	public String codigo;
	public String descripcion;
	
	private VacacionesSubTipoParametro(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

}
