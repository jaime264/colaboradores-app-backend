package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.RequestParametroActualizacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ReponseReporteTipo;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestModificarMetaVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametro;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseParametroTipo;
import pe.confianza.colaboradores.gcontenidos.server.controller.RequestFiltroEmpleadoMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Parametro;

public interface ParametrosService {
	
	List<Parametro> listParams();
	
	ResponseParametro registrar(RequestParametro request);
	
	Parametro buscarPorCodigo(String codigo);
	
	ResponseParametro buscarPorId(long id);
	
	String buscarValorPorCodigo(String codigo);
	
	List<ResponseParametro> listarParametrosVacacionesPorTipo(String codigoTipo);
	
	ResponseParametro actualizarParametroVacaciones(RequestParametroActualizacion parametro);
	
	List<ResponseParametroTipo> listaParametrovaccionesTipos();
	
	Page<ResponseEmpleadoMeta> listarVacacionMeta(RequestFiltroEmpleadoMeta filtro);
	
	void actualizarMeta(RequestModificarMetaVacacion actualizacion);
	
	List<ReponseReporteTipo> listarTiposReporte();
	
	void registrarAuditoria(int status, String mensaje, Object data);

}
