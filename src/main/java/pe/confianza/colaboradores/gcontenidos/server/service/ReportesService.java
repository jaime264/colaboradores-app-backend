package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;

public interface ReportesService {

	List<ReporteColaboradores> obtenerTodos();
	
	Page<ReporteColaboradores> listarColaboradores(RequestListarReportes request);
}
