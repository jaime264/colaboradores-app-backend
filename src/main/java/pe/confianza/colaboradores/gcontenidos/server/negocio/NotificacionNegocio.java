package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.RequestListarNotificaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotificacionVista;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTipoNotificacion;


public interface NotificacionNegocio {
	
	List<ResponseTipoNotificacion> consultarTipos();
	
	List<ResponseNotificacion> listarPorTipo(RequestListarNotificaciones request);
	
	ResponseNotificacion actualizarVisto(RequestNotificacionVista request);

}
