package pe.confianza.colaboradores.gcontenidos.server.util;

public enum FuncionalidadApp {
	
	PUBLICACIONES("FUN001"),
	BOLETAS("FUN002"),
	CTS("FUN003"),
	VACACIONES_PROGRAMACION("FUN004"),
	VACACIONES_REPROGRAMACION("FUN005"),
	VACACIONES_EXCEPCIONES("FUN006"),
	PARAMETRIA_VACACIONES("FUN007"),
	REPORTE_VACACIONES("FUN008"),
	GASTO_REGISTRO_SOLICITUD("FUN009"),
	GASTO_GESTION_PRESUPUESTO("FUN010");
	
	public String codigo;
	
	private FuncionalidadApp(String codigo) {
		this.codigo = codigo;
	}

}
