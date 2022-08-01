package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.EmplVacPerRes;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
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
				valor = (double) (c.getDiasGozados() / c.getMeta()) *100;
				
				
			}
			c.setPorcentajeAvance(valor);
			c.setDiasProgramados(c.getDiasAprobadosGozar());
			c.setDiasNoProgramados(c.getMeta() - c.getDiasProgramados());
		});

		return reporteColaboradores;
	}
	
	@Override
	public List<Map<String, String>> liistarFiltrosReporteColaborador(RequestFiltroVacacionesAprobacion reqFiltros) {
		// TODO Auto-generated method stub
		List<ReporteColaboradores> reporteColaboradores = reporteDao.reporteColaboradoresList(reqFiltros.getCodigo());

		List<Map<String, String>> datos = new ArrayList<>();
		List<String> valores = new ArrayList<>();
		
		for (ReporteColaboradores r : reporteColaboradores) {
			switch (reqFiltros.getFiltro().toUpperCase().trim()) {
			case "CODIGO":
				valores.add(r.getCodigo().toString());
				break;
			case "NOMBRE":
				valores.add(r.getNombreCompleto());
				break;
			case "CARGO":
				valores.add(r.getPuesto());
				break;
			case "AGENCIA":
				valores.add(r.getAgencia());
				break;
			case "TERRITORIO":
				valores.add(r.getTerritorio());
				break;
			case "CORREDOR":
				valores.add(r.getCorredor());
				break;
			default:
				break;
			}
		}
		
		valores = valores.stream().distinct().collect(Collectors.toList());

		for (Integer i = 0; i <= valores.size() - 1; i++) {
			Map<String, String> nombres = new HashMap<>();

			nombres.put("id", (i).toString());
			nombres.put("descripcion", valores.get(i));

			datos.add(nombres);
		}
		
		return datos;
	}
}
