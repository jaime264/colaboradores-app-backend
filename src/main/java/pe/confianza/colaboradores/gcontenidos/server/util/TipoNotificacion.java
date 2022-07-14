package pe.confianza.colaboradores.gcontenidos.server.util;

public enum TipoNotificacion {
	
	PUBLICACION_APP("NOT001"),
	COMENTARIOS("NOT002"),
	VACACIONES_APROBADOR("NOT003"),
	VACACIONES_COLABORADOR("NOT004"),
	PAGOS("NOT005");
	
	public String valor;
	
	private TipoNotificacion(String valor) {
		this.valor = valor;
	}

}
