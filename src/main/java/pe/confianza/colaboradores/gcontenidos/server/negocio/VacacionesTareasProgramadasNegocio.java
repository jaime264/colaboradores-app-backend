package pe.confianza.colaboradores.gcontenidos.server.negocio;

public interface VacacionesTareasProgramadasNegocio {
	
	void actualizarEstadoProgramaciones();
	
	void actualizarPeriodos();
	
	void consolidarMetasAnuales();
	
	void registroNotificacionesInicioRegistroProgramacion();
	
	void registroNotificacionesMetaNoCumplida();
	
	void registroNotificacionesSinRegistroProgramacion();
	
	void registroNotificacionesJefeColaboradoresSinRegistroProgramacion();
	
	void registroNotificacionJefePendienteAprobacionProgramacion();
	
	void enviarNotificacionesAppPendienteVacaciones();
	
	void enviarNotificacionesCorreoPendienteVacaciones();
	
}
