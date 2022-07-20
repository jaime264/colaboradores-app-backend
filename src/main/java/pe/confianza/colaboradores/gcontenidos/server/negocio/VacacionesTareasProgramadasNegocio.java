package pe.confianza.colaboradores.gcontenidos.server.negocio;

public interface VacacionesTareasProgramadasNegocio {
	
	void actualizarEstadoProgramaciones();
	
	void actualizarPeriodos();
	
	void consolidarMetasAnuales(boolean prod);
	
	void registrarNotificacionesAutomaticas();
	
	void registroNotificacionesInicioRegistroProgramacion();
	
	void registroNotificacionesMetaIncompleta();
	
	void registroNotificacionesSinRegistroProgramacion();
	
	void registroNotificacionesJefeColaboradoresSinRegistroProgramacion();
	
	void registroNotificacionJefePendienteAprobacionProgramacion();
	
	void enviarNotificacionesAppPendienteVacaciones();
	
	void enviarNotificacionesCorreoPendienteVacaciones();
	
	void registrarNotificacionesReprogramacionMensual();
	
	void enviarCorreoReporteAprobadorNivelI();
	
}
