package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGastoPresupuestoAnualDetalle;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestalAnual;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseGastoPresupuestoAnualDetalle;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoAnualDistribucionConcepto;

public interface GestionarPresupuestoNegocio {
	
	List<ResponseGastoPresupuestalAnual> listarPresupuestosAnuales(RequestAuditoriaBase peticion);
	
	ResponseGastoPresupuestoAnualDetalle detalleDistribucionPresupuestoAnualPorGlgAsignado(RequestGastoPresupuestoAnualDetalle peticion);
	
	List<Map<String, Object>> listarTiposDistribucionMonto(RequestAuditoriaBase peticio);
	
	List<Map<String, Object>> listarFrecuenciaDistribucion(RequestAuditoriaBase peticio);
	
	void configurarDistribucionConcepto(RequestDistribucionConcepto peticion, MultipartFile excelDistribucion);
	
	void registrarAuditoria(int status, String mensaje, Object data);
	

}
