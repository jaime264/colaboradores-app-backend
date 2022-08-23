package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseReporteExcepciones;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;

public interface ReportesService {

	List<ReporteColaboradores> obtenerTodos();
	
	Page<ReporteColaboradores> listarColaboradores(RequestListarReportes request);

	List<Map<String, String>> listarFiltrosReporteColaborador(RequestFiltroVacacionesAprobacion reqFiltros);
	
	List<ResponseReporteMeta> listarReporteMeta(RequestReporteMeta request);
	
	List<ResponseReporteMeta> listarReporteColectivos(RequestReporteMeta request);
	
	List<ResponseReporteMeta> listarReporteTerritorios(RequestReporteMeta request);
	
	List<ResponseReporteExcepciones> listarReporteExcepciones(RequestListarReportes req);
	
	String reporteExcepciones(RequestListarReportes req);
	
	String reporteColaboradores(RequestListarReportes req);
	
	String reporteMeta(RequestReporteMeta req);
	
	String reporteMetaVariosFiltro(RequestReporteMeta req);


}
