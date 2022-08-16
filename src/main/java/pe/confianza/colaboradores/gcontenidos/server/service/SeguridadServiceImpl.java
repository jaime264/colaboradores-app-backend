package pe.confianza.colaboradores.gcontenidos.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;
import pe.confianza.colaboradores.gcontenidos.server.exception.AppException;
import pe.confianza.colaboradores.gcontenidos.server.exception.NotAuthorizedException;
import pe.confianza.colaboradores.gcontenidos.server.util.SecurityUtils;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

@Service
public class SeguridadServiceImpl implements SeguridadService {
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public void validarLogAuditoria(LogAuditoria auditoria) {
		if(auditoria == null)
			throw new NotAuthorizedException(Utilitario.obtenerMensaje(messageSource, "seguridad.usuario.log.error"));
		if(auditoria.getUsuario() == null)
			throw new NotAuthorizedException(Utilitario.obtenerMensaje(messageSource, "seguridad.usuario.log.error"));
		validarSesion(auditoria.getUsuario().trim());
	}

	@Override
	public void validarSesion(String usuarioBT) {
		try {
			SecurityUtils.validateUserSession(usuarioBT);
		} catch (Exception e) {
			if(e.getMessage().equals(SecurityUtils.UNAUTHORIZED))
				throw new NotAuthorizedException(Utilitario.obtenerMensaje(messageSource, "seguridad.usuario.sesion.error", usuarioBT));
			throw new AppException(Utilitario.obtenerMensaje(messageSource, "seguridad.sesion.validar.error"));
		}
		
	}

}
