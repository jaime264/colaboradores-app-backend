package pe.confianza.colaboradores.gcontenidos.server.util;

public enum TipoNotificacion {
	
	PUBLICACION_APP("NOT001"),
	PUBLICACION_GESTOR_CONTENIDO("NOT002"),
	VACACIONES("NOT003"),
	PAGOS("NOT004");
	
	public String valor;
	
	private TipoNotificacion(String valor) {
		this.valor = valor;
	}

}
