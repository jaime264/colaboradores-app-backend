package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;

public interface ReportesService {

	List<ReporteColaboradores> obtenerTodos();
	
	Page<ReporteColaboradores> listarColaboradores(RequestListarReportes request);

	List<Map<String, String>> liistarFiltrosReporteColaborador(RequestFiltroVacacionesAprobacion reqFiltros);
}
