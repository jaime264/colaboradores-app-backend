package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReportesDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;

@Service
public class ReportesServiceImpl implements ReportesService {

	@Autowired
	ReportesDao reporteDao;
	
	@Override
	public List<ReporteColaboradores> obtenerTodos() {
		// TODO Auto-generated method stub
		List<ReporteColaboradores> reportes = new ArrayList<>();
		try {
			reportes = reporteDao.findAll();
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
		return reportes;
	}

	@Override
	public Page<ReporteColaboradores> listarColaboradores(RequestListarReportes request) {
		// TODO Auto-generated method stub
		Pageable paginacion = PageRequest.of(request.getNumeroPagina(), request.getTamanioPagina());
		
		Page<ReporteColaboradores> reporteColaboradores = reporteDao.reporteColaboradores(request.getCodigoUsuario(), paginacion);
		
		reporteColaboradores.getContent().stream().forEach(c ->{
			Double valor = 0.0;
			if(c.getDiasGozados()>0) {
				valor = (double) (c.getDiasGozados() / c.getMeta());
			}
			c.setPorcentajeAvance(valor);
		});

		return reporteColaboradores;
	}

}
