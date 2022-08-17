package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestalAnual;

public interface GestionarPresupuestoNegocio {
	
	List<ResponseGastoPresupuestalAnual> listarPresupuestosAnuales(RequestAuditoriaBase peticion);
	
	void registrarAuditoria(int status, String mensaje, Object data);

}
