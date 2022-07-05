package pe.confianza.colaboradores.gcontenidos.server.util;

public enum FuncionalidadApp {
	
	PUBLICACIONES("FUN001"),
	BOLETAS("FUN002"),
	CTS("FUN003"),
	VACACIONES_PROGRAMACION("FUN004"),
	VACACIONES_REPROGRAMACION("FUN005"),
	VACACIONES_EXCEPCIONES("FUN006");
	
	public String codigo;
	
	private FuncionalidadApp(String codigo) {
		this.codigo = codigo;
	}

}
