package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarNotificaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotificacionVista;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTipoNotificacion;


public interface NotificacionNegocio {
	
	List<ResponseTipoNotificacion> consultarTipos();
	
	Page<ResponseNotificacion> listarPorTipo(RequestListarNotificaciones request);
	
	ResponseNotificacion actualizarVisto(RequestNotificacionVista request);

}
