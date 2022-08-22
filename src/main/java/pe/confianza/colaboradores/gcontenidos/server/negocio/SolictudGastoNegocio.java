package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCentroCostos;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoEmpleado;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestTipoGasto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.CentroCosto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConcepto;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastoConceptoTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.GastosSolicitud;

public interface SolictudGastoNegocio {
	
	void registrarAuditoria(int status, String mensaje, Object data);
	
	List<GastoConceptoTipo> listarTipoGasto(RequestTipoGasto peticion);

	List<GastoConcepto> listarConceptoByTipoGasto(RequestConcepto reqConcepto);
	
	List<CentroCosto> listarCentrosCostoByAgencia(RequestCentroCostos peticion);
		
	GastosSolicitud registrarGastoEmpleado(RequestGastoEmpleado gasto);
}
