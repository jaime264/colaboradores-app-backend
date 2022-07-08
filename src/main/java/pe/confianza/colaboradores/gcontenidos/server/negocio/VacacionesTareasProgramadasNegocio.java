package pe.confianza.colaboradores.gcontenidos.server.negocio;

public interface VacacionesTareasProgramadasNegocio {
	
	void actualizarEstadoProgramaciones();
	
	void actualizarPeriodos();
	
	void consolidarMetasAnuales(boolean prod);
	
	void registroNotificacionesInicioRegistroProgramacion();
	
	void registroNotificacionesMetaNoCumplida();
	
	void registroNotificacionesSinRegistroProgramacion();
	
	void registroNotificacionesJefeColaboradoresSinRegistroProgramacion();
	
	void registroNotificacionJefePendienteAprobacionProgramacion();
	
	void enviarNotificacionesAppPendienteVacaciones();
	
	void enviarNotificacionesCorreoPendienteVacaciones();
	
	void registrarNotificacionesReprogramacionMensual();
	
}
