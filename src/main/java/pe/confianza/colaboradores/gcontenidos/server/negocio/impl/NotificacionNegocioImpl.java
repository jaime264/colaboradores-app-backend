package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarNotificaciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestNotificacionVista;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseTipoNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Notificacion;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.negocio.NotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;

@Service
public class NotificacionNegocioImpl implements NotificacionNegocio {
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private EmpleadoService empleadoService;

	@Override
	public List<ResponseTipoNotificacion> consultarTipos() {
		return notificacionService.obtenerTipos().stream().map(t -> {
			ResponseTipoNotificacion response = new ResponseTipoNotificacion();
			response.setCodigo(t.getCodigo());
			response.setDescripcion(t.getDescripcion());
			response.setDescripcionExtendida(t.getDescripcionExtendida());
			return response;
		}).collect(Collectors.toList());
	}

	@Override
	public Page<ResponseNotificacion> listarPorTipo(RequestListarNotificaciones request) {
		Optional<NotificacionTipo> optTipo = notificacionService.obtener(request.getCodigoTipoNotificacion());
		if(!optTipo.isPresent())
			throw new AppException("No existe el tipo de notificacion " + request.getCodigoTipoNotificacion());
		Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioBT());
		if(empleado.getId() == null) 
			throw new AppException("No existe el empleado " + request.getUsuarioBT());
		Page<Notificacion> pgNotificaciones = notificacionService.consultar(empleado, optTipo.get(), request.getNumeroPagina(), request.getTamanioPagina());
		return pgNotificaciones.map(n -> {
			ResponseNotificacion response = new ResponseNotificacion();
			response.setId(n.getId());
			response.setDescripcion(n.getDescripcion());
			response.setTitulo(n.getTitulo());
			response.setFecha(n.getFechaCrea());
			response.setInformacionAdicional(n.getData());
			response.setVisto(n.isVisto());
			return response;
		});
	}

	@Override
	public ResponseNotificacion actualizarVisto(RequestNotificacionVista request) {
		Optional<Notificacion> optNot = notificacionService.obtener(request.getId());
		if(!optNot.isPresent())
			throw new AppException("No existe la notificacion " + request.getId());
		Empleado empleado = empleadoService.buscarPorUsuarioBT(request.getUsuarioOperacion());
		if(empleado.getId() == null) 
			throw new AppException("No existe el empleado " + request.getUsuarioOperacion());
		Notificacion notificacion = optNot.get();
		if(!notificacion.getEmpleado().getUsuarioBT().equals(request.getUsuarioOperacion()))
			throw new AppException("No tiene permisos para actualizar la notificación");
		notificacion.setVisto(true);
		notificacion = notificacionService.actualizar(notificacion, request.getUsuarioOperacion());
		ResponseNotificacion response = new ResponseNotificacion();
		response.setId(notificacion.getId());
		response.setDescripcion(notificacion.getDescripcion());
		response.setTitulo(notificacion.getTitulo());
		response.setFecha(notificacion.getFechaCrea());
		response.setInformacionAdicional(notificacion.getData());
		response.setVisto(notificacion.isVisto());
		return response;
	}
	
	
	
	

}
