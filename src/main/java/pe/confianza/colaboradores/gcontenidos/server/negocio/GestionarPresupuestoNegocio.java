package pe.confianza.colaboradores.gcontenidos.server.negocio;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestAuditoriaBase;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestDistribucionConcepto;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPresupuestoConfiguracionConsulta;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestPresupuestoTipoGastoResumen;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePrespuestoConceptoConfiguracion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoGeneralGasto;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponsePresupuestoTipoGastoResumen;

public interface GestionarPresupuestoNegocio {
	
	List<ResponsePresupuestoGeneralGasto> listarPresupuestosGenerales(RequestAuditoriaBase peticion);
	
	
	ResponsePresupuestoTipoGastoResumen detallePresupuestoTipoGastoPorGlgAsignado(RequestPresupuestoTipoGastoResumen peticion);
	
	List<Map<String, Object>> listarTiposDistribucionMonto(RequestAuditoriaBase peticio);
	
	List<Map<String, Object>> listarFrecuenciaDistribucion(RequestAuditoriaBase peticio);
	
	void configurarDistribucionConcepto(String jsonData, MultipartFile excelDistribucion);
	
	ResponsePrespuestoConceptoConfiguracion obtenerConfiguracion(RequestPresupuestoConfiguracionConsulta peticion);
	
	void registrarAuditoria(int status, String mensaje, Object data);
	

}
