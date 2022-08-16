package pe.confianza.colaboradores.gcontenidos.server.service;

import pe.confianza.colaboradores.gcontenidos.server.bean.LogAuditoria;

public interface SeguridadService {
	
	void validarLogAuditoria(LogAuditoria auditoria);
	
	void validarSesion(String usuarioBT);

}
