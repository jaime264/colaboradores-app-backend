package pe.confianza.colaboradores.gcontenidos.server.util;

public enum PerfilApp {

	EMPLEADO_GENERAL("P001"),
	EMPLEADO_APROBADOR_PUBLICACIONES("P002"),
	EMPLEADO_REGISTRO_EXCEPCION_VACACIONES("P003"),
	EMPLEADO_APROBADOR_VACACIONES("P004");
	
	public String codigo;
	
	private PerfilApp(String codigo) {
		this.codigo = codigo;
	}
}
