package pe.confianza.colaboradores.gcontenidos.server.util;

public enum TipoNotificacion {
	
	PUBLICACION_APP("NOT001"),
	COMENTARIOS("NOT002"),
	VACACIONES_APROBADOR("NOT003"), //gestion de vacaciones
	VACACIONES_COLABORADOR("NOT004"), // solicitud de vacaciones
	PAGOS("NOT005"),
	PUBLICACION_APROBADOR("NOT006"),
	COMENTARIOS_GESTION("NOT007");
	
	public String valor;
	
	private TipoNotificacion(String valor) {
		this.valor = valor;
	}

}
