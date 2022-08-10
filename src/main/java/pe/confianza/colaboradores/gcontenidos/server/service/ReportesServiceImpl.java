package pe.confianza.colaboradores.gcontenidos.server.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.IReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReporteMetaDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao.ReportesDao;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;
import pe.confianza.colaboradores.gcontenidos.server.negocio.impl.VacacionesTareasProgramadasNegocioImpl;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Row;
import pe.confianza.colaboradores.gcontenidos.server.util.file.read.ColumnType;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.IReport;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.Report;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.ReportFactory;

@Service
public class ReportesServiceImpl implements ReportesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionesTareasProgramadasNegocioImpl.class);

	@Autowired
	ReportesDao reporteDao;

	@Autowired
	ReporteMetaDao reporteMetaDao;

	@Autowired
	private ReportFactory reportFactory;

	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	ReporteAccesoService reporteAccesoService;

	@Override
	public List<ReporteColaboradores> obtenerTodos() {
		// TODO Auto-generated method stub
		List<ReporteColaboradores> reportes = new ArrayList<>();
		try {
			// reportes = reporteDao.findAll();
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return reportes;
	}

	@Override
	public Page<ReporteColaboradores> listarColaboradores(RequestListarReportes request) {

		int empleadoAprob = reporteAccesoService.cantidadEmpleadosAcceso(request.getCodigoUsuario());
		if(empleadoAprob > 0) request.setCodigoUsuario("");
		
		int anio = cargaParametros.getMetaVacacionAnio();
		Pageable paginacion = PageRequest.of(request.getNumeroPagina(), request.getTamanioPagina());
		Page<ReporteColaboradores> reporteColaboradores = null;

		switch (request.getTipoFiltro().trim().toUpperCase()) {
		case "CODIGO":
			reporteColaboradores = reporteDao.reporteColaboradoresCodigo(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "NOMBRE":
			reporteColaboradores = reporteDao.reporteColaboradoresNombre(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "CARGO":
			reporteColaboradores = reporteDao.reporteColaboradoresPuesto(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "AGENCIA":
			reporteColaboradores = reporteDao.reporteColaboradoresAgencia(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "TERRITORIO":
			reporteColaboradores = reporteDao.reporteColaboradoresTerritorio(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "CORREDOR":
			reporteColaboradores = reporteDao.reporteColaboradoresCorredor(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "DIVISION":
			reporteColaboradores = reporteDao.reporteColaboradoresDivision(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "COLECTIVO":
			reporteColaboradores = reporteDao.reporteColaboradoresColectivo(request.getCodigoUsuario(),
					request.getFiltro(), paginacion, anio);
			break;
		case "INGRESO":
			reporteColaboradores = reporteDao.reporteColaboradoresIngreso(request.getCodigoUsuario(),
					request.getFechaInicio(), request.getFechaFin(), paginacion, anio);
			break;
		default:
			reporteColaboradores = reporteDao.reporteColaboradores(request.getCodigoUsuario(), paginacion, anio);
			break;
		}

		reporteColaboradores.getContent().stream().forEach(c -> {
			Double valor = 0.0;
			if (c.getDiasGozados() > 0) {
				valor = (double) (c.getDiasGozados() / c.getMeta()) * 100;

			}
			c.setPorcentajeAvance(valor);
			c.setDiasProgramados(c.getDiasAprobadosGozar());
			c.setDiasNoProgramados(c.getMeta() - c.getDiasProgramados());
		});

		return reporteColaboradores;
	}

	@Override
	public List<Map<String, String>> listarFiltrosReporteColaborador(RequestFiltroVacacionesAprobacion reqFiltros) {
		// TODO Auto-generated method stub
		
		int empleadoAprob = reporteAccesoService.cantidadEmpleadosAcceso(reqFiltros.getCodigo());
		if(empleadoAprob > 0) reqFiltros.setCodigo("");
		
		int anio = cargaParametros.getMetaVacacionAnio();
		List<ReporteColaboradores> reporteColaboradores = reporteDao.reporteColaboradoresList(reqFiltros.getCodigo(), anio);
		List<Map<String, String>> datos = new ArrayList<>();
		List<String> valores = new ArrayList<>();

		for (ReporteColaboradores r : reporteColaboradores) {
			switch (reqFiltros.getFiltro().toUpperCase().trim()) {
			case "CODIGO":
				valores.add(r.getCodigo().toString());
				break;
			case "NOMBRE":
				valores.add(r.getNombreCompleo());
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
			case "DIVISION":
				valores.add(r.getDivision());
				break;
			case "COLECTIVO":
				valores.add(r.getColectivo());
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

	@Override
	public List<ResponseReporteMeta> listarReporteMeta(RequestReporteMeta request) {
		// TODO Auto-generated method stub
		int empleadoAprob = reporteAccesoService.cantidadEmpleadosAcceso(request.getCodigoUsuario());
		if(empleadoAprob > 0) request.setCodigoUsuario("");
		
		List<ResponseReporteMeta> listResponse = new ArrayList<ResponseReporteMeta>();
		int anio = cargaParametros.getMetaVacacionAnio();

		switch (request.getTipoReporte().trim().toUpperCase()) {
		case "TOTALDIASMETA":

			List<IReporteMeta> list = reporteMetaDao.reporteMeta(request.getCodigoUsuario(), anio);

			list.stream().forEach(m -> {
				ResponseReporteMeta reporte = new ResponseReporteMeta();
				reporte.setCategoria("Meta");
				reporte.setMeta(m.getMeta());
				reporte.setDiasGozados(m.getDiasgozados());
				reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
				reporte.setPorcentajeAvance(
						Double.parseDouble(String.format("%.2f", (double) m.getDiasgozados() / m.getMeta() * 100)));
				listResponse.add(reporte);
			});

			break;
		case "TOTALTERRITORIOS":
			List<IReporteMeta> listTerritorios = reporteMetaDao.reporteMetaTerritorio(request.getCodigoUsuario(), anio);

			listTerritorios.stream().forEach(m -> {
				ResponseReporteMeta reporte = new ResponseReporteMeta();
				reporte.setCategoria(m.getCategoria());
				reporte.setMeta(m.getMeta());
				reporte.setDiasGozados(m.getDiasgozados());
				reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
				reporte.setPorcentajeAvance(
						Double.parseDouble(String.format("%.2f", (double) m.getDiasgozados() / m.getMeta() * 100)));
				listResponse.add(reporte);
			});
			break;
		case "TOTALCOLECTIVOS":
			List<IReporteMeta> listColectivos = reporteMetaDao.reporteMetaColectivo(request.getCodigoUsuario(), anio);

			listColectivos.stream().forEach(m -> {
				ResponseReporteMeta reporte = new ResponseReporteMeta();
				reporte.setCategoria(m.getCategoria());
				reporte.setMeta(m.getMeta());
				reporte.setDiasGozados(m.getDiasgozados());
				reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
				reporte.setPorcentajeAvance(
						Double.parseDouble(String.format("%.2f", (double) m.getDiasgozados() / m.getMeta() * 100)));
				listResponse.add(reporte);
			});
			break;

		default:
			break;
		}
		return listResponse;
	}

	@Override
	public List<ResponseReporteMeta> listarReporteColectivos(RequestReporteMeta request) {

		int empleadoAprob = reporteAccesoService.cantidadEmpleadosAcceso(request.getCodigoUsuario());
		if(empleadoAprob > 0) request.setCodigoUsuario("");
		
		List<ResponseReporteMeta> listResponse = new ArrayList<ResponseReporteMeta>();
		int anio = cargaParametros.getMetaVacacionAnio();
		List<IReporteMeta> listColectivoDivisiones = reporteMetaDao.reporteMetaColectivoDivision(request.getCodigoUsuario(), anio, request.getFiltro());
		
		listColectivoDivisiones.stream().forEach(m -> {
			ResponseReporteMeta reporte = new ResponseReporteMeta();
			reporte.setCategoria(m.getCategoria());
			reporte.setMeta(m.getMeta());
			reporte.setDiasGozados(m.getDiasgozados());
			reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
			reporte.setPorcentajeAvance(
					Double.parseDouble(String.format("%.2f", (double) m.getDiasgozados() / m.getMeta() * 100)));
			listResponse.add(reporte);
		});

		return listResponse;
	}

	@Override
	public List<ResponseReporteMeta> listarReporteTerritorios(RequestReporteMeta request) {
		
		int empleadoAprob = reporteAccesoService.cantidadEmpleadosAcceso(request.getCodigoUsuario());
		if(empleadoAprob > 0) request.setCodigoUsuario("");
		
		List<ResponseReporteMeta> listResponse = new ArrayList<ResponseReporteMeta>();
		int anio = cargaParametros.getMetaVacacionAnio();
		List<IReporteMeta> listTerritorioColectivos = reporteMetaDao.reporteMetaTerritorioColectivo(request.getCodigoUsuario(), anio, request.getFiltro());
		
		listTerritorioColectivos.stream().forEach(m -> {
			ResponseReporteMeta reporte = new ResponseReporteMeta();
			reporte.setCategoria(m.getCategoria());
			reporte.setMeta(m.getMeta());
			reporte.setDiasGozados(m.getDiasgozados());
			reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
			reporte.setPorcentajeAvance(
					Double.parseDouble(String.format("%.2f", (double) m.getDiasgozados() / m.getMeta() * 100)));
			listResponse.add(reporte);
		});

		return listResponse;
	}

	@Override
	public ByteArrayInputStream reporteColaboradores(RequestListarReportes req) {

		List<ReporteColaboradores> listReporte = new ArrayList<>();
		Page<ReporteColaboradores> reporteColaboradores = listarColaboradores(req);
		listReporte.addAll(reporteColaboradores.getContent());

		for (int i = 1; i < reporteColaboradores.getTotalPages(); i++) {
			req.setCodigoUsuario("222");
			req.setNumeroPagina(0);
			req.setTamanioPagina(100);
			reporteColaboradores = listarColaboradores(req);
			listReporte.addAll(reporteColaboradores.getContent());

		}

		Report reporte = new Report();
		reporte.setType("XLSX");
		reporte.setTitle("REPORTE COLABORADORES");
		reporte.getCollection().addHeader("Numero", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Codigo", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Nombres y Apellidos", ColumnType.STRING);
		reporte.getCollection().addHeader("Puesto", ColumnType.STRING);
		reporte.getCollection().addHeader("Fecha de Ingreso", ColumnType.LOCALDATE);
		reporte.getCollection().addHeader("División", ColumnType.STRING);
		reporte.getCollection().addHeader("Área", ColumnType.STRING);
		reporte.getCollection().addHeader("Corredor", ColumnType.STRING);
		reporte.getCollection().addHeader("Territorio", ColumnType.STRING);
		reporte.getCollection().addHeader("Agencia", ColumnType.STRING);
		reporte.getCollection().addHeader("Colectivo", ColumnType.STRING);
		reporte.getCollection().addHeader("Meta de ia", ColumnType.STRING);
		reporte.getCollection().addHeader("Colectivo", ColumnType.STRING);
		reporte.getCollection().addHeader("Colectivo", ColumnType.STRING);
		reporte.getCollection().addHeader("Colectivo", ColumnType.STRING);

		int count = 1;
		for (ReporteColaboradores repColaboradores : listReporte) {

			Row row = new Row();
			row.addCell("Numero", count);
			row.addCell("Codigo", repColaboradores.getCodigo());
			row.addCell("Nombres y Apellidos", repColaboradores.getNombreCompleo());
			row.addCell("Puesto", repColaboradores.getPuesto());
			row.addCell("Fecha de Ingreso", repColaboradores.getFechaIngreso());
			row.addCell("División", repColaboradores.getDivision());
			row.addCell("Corredor", repColaboradores.getCorredor());
			row.addCell("Territorio", repColaboradores.getTerritorio());
			row.addCell("Agencia", repColaboradores.getAgencia());
			row.addCell("Colectivo", repColaboradores.getColectivo());
			row.addCell("Meta de días", repColaboradores.getMeta());
			row.addCell("Dias Gozados", repColaboradores.getDiasGozados());
			row.addCell("Porcentaje Avance", repColaboradores.getPorcentajeAvance());
			row.addCell("Dias Programados", repColaboradores.getDiasProgramados());
			reporte.getCollection().setCurrentRow(row);
			reporte.getCollection().addRow();

			count++;
		}

		try {
			IReport<ByteArrayInputStream> excel = reportFactory.createReport(reporte);
			excel.build();
			return excel.getReult();
		} catch (Exception e) {
			LOGGER.error("[ERROR] enviarCorreoReporteAprobadorNivelI", e);
		}
		return null;

	}

	@Override
	public byte[] reporteMeta(RequestReporteMeta req) {

		List<ResponseReporteMeta> listReport = listarReporteMeta(req);

		Report reporte = new Report();
		reporte.setType("XLSX");
		reporte.setTitle("REPORTE META");
		reporte.getCollection().addHeader("Numero", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Meta", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Días gozados", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Porcentaje de avance", ColumnType.DOUBLE);
		reporte.getCollection().addHeader("Días pendientes", ColumnType.INTEGER);

		int count = 1;
		for (ResponseReporteMeta repMeta : listReport) {

			Row row = new Row();
			row.addCell("Numero", count);
			row.addCell("Meta", repMeta.getMeta());
			row.addCell("Días gozados", repMeta.getDiasGozados());
			row.addCell("Porcentaje de avance", repMeta.getPorcentajeAvance());
			row.addCell("Días pendientes", repMeta.getDiasPendientes());

			reporte.getCollection().setCurrentRow(row);
			reporte.getCollection().addRow();

			count++;
		}

		try {
			IReport<ByteArrayInputStream> excel = reportFactory.createReport(reporte);
			excel.build();
			return IOUtils.toByteArray(excel.getReult());
		} catch (Exception e) {
			LOGGER.error("[ERROR] enviarCorreoReporteAprobadorNivelI", e);
		}

		return null;
	}

	@Override
	public byte[] reporteMetaVariosFiltro(RequestReporteMeta req) {

		List<ResponseReporteMeta> list = new ArrayList<>();
		
		switch (req.getTipoReporte()) {
		case "TERRITORIOS":
			list = listarReporteMeta(req);
			break;
		case "COLECTIVOS":
			list = listarReporteMeta(req);
			break;
		case "TERRITORIOSVARIOS":
			list = listarReporteTerritorios(req);
			break;
		case "COLECTIVOSVARIOS":
			list = listarReporteColectivos(req);
			break;

		default:
			break;
		}

		byte[] reportExcel = reporeteMetaVarios(list, req.getTipoReporte() + "" +req.getFiltro());

		return reportExcel;
	}

	private byte[] reporeteMetaVarios(List<ResponseReporteMeta> list ,String Titulo) {

		Report reporte = new Report();
		reporte.setType("XLSX");
		reporte.setTitle("REPORTE " + Titulo);
		reporte.getCollection().addHeader("Numero", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Descripcion", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Meta", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Días gozados", ColumnType.INTEGER);
		reporte.getCollection().addHeader("Porcentaje de avance", ColumnType.DOUBLE);
		reporte.getCollection().addHeader("Días pendientes", ColumnType.INTEGER);

		int count = 1;
		for (ResponseReporteMeta repMeta : list) {

			Row row = new Row();
			row.addCell("Numero", count);
			row.addCell("Descripcion", repMeta.getCategoria());
			row.addCell("Meta", repMeta.getMeta());
			row.addCell("Días gozados", repMeta.getDiasGozados());
			row.addCell("Porcentaje de avance", repMeta.getPorcentajeAvance());
			row.addCell("Días pendientes", repMeta.getDiasPendientes());

			reporte.getCollection().setCurrentRow(row);
			reporte.getCollection().addRow();

			count++;
		}

		try {
			IReport<ByteArrayInputStream> excel = reportFactory.createReport(reporte);
			excel.build();
			return IOUtils.toByteArray(excel.getReult());
		} catch (Exception e) {
			LOGGER.error("[ERROR] enviarCorreoReporteAprobadorNivelI", e);
		}

		return null;

	}

}
