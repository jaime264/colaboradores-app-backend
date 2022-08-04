package pe.confianza.colaboradores.gcontenidos.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@Service
public class ReportesServiceImpl implements ReportesService {

	@Autowired
	ReportesDao reporteDao;

	@Autowired
	ReporteMetaDao reporteMetaDao;

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
		// TODO Auto-generated method stub
		Pageable paginacion = PageRequest.of(request.getNumeroPagina(), request.getTamanioPagina());

		Page<ReporteColaboradores> reporteColaboradores = null;

		switch (request.getTipoFiltro().trim().toUpperCase()) {
		case "CODIGO":
			reporteColaboradores = reporteDao.reporteColaboradoresCodigo(request.getCodigoUsuario(),
					request.getFiltro(), paginacion);
			break;
		case "NOMBRE":
			reporteColaboradores = reporteDao.reporteColaboradoresNombre(request.getCodigoUsuario(),
					request.getFiltro(), paginacion);
			break;
		case "CARGO":
			reporteColaboradores = reporteDao.reporteColaboradoresPuesto(request.getCodigoUsuario(),
					request.getFiltro(), paginacion);
			break;
		case "AGENCIA":
			reporteColaboradores = reporteDao.reporteColaboradoresAgencia(request.getCodigoUsuario(),
					request.getFiltro(), paginacion);
			break;
		case "TERRITORIO":
			reporteColaboradores = reporteDao.reporteColaboradoresTerritorio(request.getCodigoUsuario(),
					request.getFiltro(), paginacion);
			break;
		case "CORREDOR":
			reporteColaboradores = reporteDao.reporteColaboradoresCorredor(request.getCodigoUsuario(),
					request.getFiltro(), paginacion);
			break;
		case "INGRESO":
			reporteColaboradores = reporteDao.reporteColaboradoresIngreso(request.getCodigoUsuario(),
					request.getFechaInicio(), request.getFechaFin(), paginacion);
			break;
		default:
			reporteColaboradores = reporteDao.reporteColaboradores(request.getCodigoUsuario(), paginacion);
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
		List<ReporteColaboradores> reporteColaboradores = reporteDao.reporteColaboradoresList(reqFiltros.getCodigo());

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
		List<ResponseReporteMeta> listResponse = new ArrayList<ResponseReporteMeta>();
		ResponseReporteMeta reporteRedComecial = new ResponseReporteMeta();
		ResponseReporteMeta reporteOperaciones = new ResponseReporteMeta();
		ResponseReporteMeta reporteRecuperaciones = new ResponseReporteMeta();
		ResponseReporteMeta reporteStaf = new ResponseReporteMeta();
		int meta = 0;
		int diasGozados = 0;

		switch (request.getTipoReporte().trim().toUpperCase()) {
		case "TOTALDIASMETA":
			List<IReporteMeta> list = reporteMetaDao.reporteMeta();

			list.stream().forEach(m -> {
				ResponseReporteMeta reporte = new ResponseReporteMeta();
				reporte.setCategoria("Meta");
				reporte.setMeta(m.getMeta());
				reporte.setDiasGozados(m.getDiasgozados());
				reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
				reporte.setPorcentajeAvance(Double.parseDouble(String.format("%.2f", (double)m.getDiasgozados() / m.getMeta())));
				listResponse.add(reporte);
			});
			break;
		case "REDCOMERCIALIZACION":

			break;

		case "REDPRODUCCIONTECNOLOGIA":

			break;

		case "STAF":

			break;

		case "TOTALCOLECTIVOS":
			List<IReporteMeta> listColectivos = reporteMetaDao.reporteMetaColectivo();

			for (IReporteMeta m : listColectivos) {

				// RED COMERCIAL
				if (Constantes.ASESOR_NEGOCIO.equals(m.getCategoria())
						|| Constantes.ASESOR_NEGOCIO_INDIVIDUAL.equals(m.getCategoria())
						|| Constantes.ASESOR_NEGOCIO_GRUPAL.equals(m.getCategoria())
						|| Constantes.GERENTE_CORREDOR.equals(m.getCategoria())
						|| Constantes.ADMINISTRADOR_NEGOCIO.equals(m.getCategoria())) {

					meta += m.getMeta();
					diasGozados += m.getDiasgozados();

					reporteRedComecial.setCategoria("RED COMERCIAL");
					reporteRedComecial.setMeta(meta);
					reporteRedComecial.setDiasGozados(diasGozados);

				}
				// OPERACIONES
				else if (Constantes.ASESOR_SERVICIO.equals(m.getCategoria())
						|| Constantes.SUPERVISOR_OFICINA.equals(m.getCategoria())
						|| Constantes.ASESOR_PLATAFORMA.equals(m.getCategoria())) {
					meta += m.getMeta();
					diasGozados += m.getDiasgozados();

					reporteOperaciones.setCategoria("OPERACIONES");
					reporteOperaciones.setMeta(meta);
					reporteOperaciones.setDiasGozados(diasGozados);
				}
				// RECUPERACIONES
				else if (Constantes.ANALISTA_COBRANZA.equals(m.getCategoria())
						|| Constantes.ANALISTA_RECUPERACIONES.equals(m.getCategoria())
						|| Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA.equals(m.getCategoria())) {
					meta += m.getMeta();
					diasGozados += m.getDiasgozados();

					reporteRecuperaciones.setCategoria("RECUPERACIONES");
					reporteRecuperaciones.setMeta(meta);
					reporteRecuperaciones.setDiasGozados(diasGozados);
				}
				// STAF
				else {
					meta += m.getMeta();
					diasGozados += m.getDiasgozados();

					reporteStaf.setCategoria("STAFF");
					reporteStaf.setMeta(meta);
					reporteStaf.setDiasGozados(diasGozados);
				}

			}
			listResponse.add(reporteRedComecial);
			listResponse.add(reporteOperaciones);
			listResponse.add(reporteRecuperaciones);
			listResponse.add(reporteStaf);

			listResponse.stream().forEach(r -> {
				r.setDiasPendientes(r.getMeta() - r.getDiasGozados());
				r.setPorcentajeAvance(Double.parseDouble(String.format("%.2f", (double) r.getDiasGozados() / r.getMeta())));
			});

			break;

		case "TOTALTERRITORIOS":
			List<IReporteMeta> listTerritorios = reporteMetaDao.reporteMetaTerritorio();

			listTerritorios.stream().forEach(m -> {
				ResponseReporteMeta reporte = new ResponseReporteMeta();
				reporte.setCategoria(m.getCategoria());
				reporte.setMeta(m.getMeta());
				reporte.setDiasGozados(m.getDiasgozados());
				reporte.setDiasPendientes(m.getMeta() - m.getDiasgozados());
				reporte.setPorcentajeAvance(Double.parseDouble(String.format("%.2f", (double)m.getDiasgozados() / m.getMeta())));
				listResponse.add(reporte);
			});
			break;

		case "TERRITORIOCENTROSUR":
			List<IReporteMeta> listTerritorioCentroSur = reporteMetaDao.reporteMetaTerritorioColectivo();

			for (IReporteMeta m : listTerritorioCentroSur) {
				if (Constantes.CENTRO_SUR.equals(m.getOpcional())) {
					// RED COMERCIAL
					if (Constantes.ASESOR_NEGOCIO.equals(m.getCategoria())
							|| Constantes.ASESOR_NEGOCIO_INDIVIDUAL.equals(m.getCategoria())
							|| Constantes.ASESOR_NEGOCIO_GRUPAL.equals(m.getCategoria())
							|| Constantes.GERENTE_CORREDOR.equals(m.getCategoria())
							|| Constantes.ADMINISTRADOR_NEGOCIO.equals(m.getCategoria())) {

						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteRedComecial.setCategoria("RED COMERCIAL");
						reporteRedComecial.setMeta(meta);
						reporteRedComecial.setDiasGozados(diasGozados);

					}
					// OPERACIONES
					else if (Constantes.ASESOR_SERVICIO.equals(m.getCategoria())
							|| Constantes.SUPERVISOR_OFICINA.equals(m.getCategoria())
							|| Constantes.ASESOR_PLATAFORMA.equals(m.getCategoria())) {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteOperaciones.setCategoria("OPERACIONES");
						reporteOperaciones.setMeta(meta);
						reporteOperaciones.setDiasGozados(diasGozados);
					}
					// RECUPERACIONES
					else if (Constantes.ANALISTA_COBRANZA.equals(m.getCategoria())
							|| Constantes.ANALISTA_RECUPERACIONES.equals(m.getCategoria())
							|| Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA.equals(m.getCategoria())) {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteRecuperaciones.setCategoria("RECUPERACIONES");
						reporteRecuperaciones.setMeta(meta);
						reporteRecuperaciones.setDiasGozados(diasGozados);
					}
					// STAF
					else {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteStaf.setCategoria("STAFF");
						reporteStaf.setMeta(meta);
						reporteStaf.setDiasGozados(diasGozados);
					}
				}
			}

			listResponse.add(reporteRedComecial);
			listResponse.add(reporteOperaciones);
			listResponse.add(reporteRecuperaciones);
			listResponse.add(reporteStaf);

			listResponse.stream().forEach(r -> {
				r.setDiasPendientes(r.getMeta() - r.getDiasGozados());
				r.setPorcentajeAvance(Double.parseDouble(String.format("%.2f", (double) r.getDiasGozados() / r.getMeta())));
			});

			break;

		case "TERRITORIOLIMAORIENTE":
			List<IReporteMeta> listTerritorioLimaOriente = reporteMetaDao.reporteMetaTerritorioColectivo();
			for (IReporteMeta m : listTerritorioLimaOriente) {
				if (Constantes.LIMA_ORIENTE.equals(m.getOpcional())) {
					// RED COMERCIAL
					if (Constantes.ASESOR_NEGOCIO.equals(m.getCategoria())
							|| Constantes.ASESOR_NEGOCIO_INDIVIDUAL.equals(m.getCategoria())
							|| Constantes.ASESOR_NEGOCIO_GRUPAL.equals(m.getCategoria())
							|| Constantes.GERENTE_CORREDOR.equals(m.getCategoria())
							|| Constantes.ADMINISTRADOR_NEGOCIO.equals(m.getCategoria())) {

						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteRedComecial.setCategoria("RED COMERCIAL");
						reporteRedComecial.setMeta(meta);
						reporteRedComecial.setDiasGozados(diasGozados);

					}
					// OPERACIONES
					else if (Constantes.ASESOR_SERVICIO.equals(m.getCategoria())
							|| Constantes.SUPERVISOR_OFICINA.equals(m.getCategoria())
							|| Constantes.ASESOR_PLATAFORMA.equals(m.getCategoria())) {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteOperaciones.setCategoria("OPERACIONES");
						reporteOperaciones.setMeta(meta);
						reporteOperaciones.setDiasGozados(diasGozados);
					}
					// RECUPERACIONES
					else if (Constantes.ANALISTA_COBRANZA.equals(m.getCategoria())
							|| Constantes.ANALISTA_RECUPERACIONES.equals(m.getCategoria())
							|| Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA.equals(m.getCategoria())) {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteRecuperaciones.setCategoria("RECUPERACIONES");
						reporteRecuperaciones.setMeta(meta);
						reporteRecuperaciones.setDiasGozados(diasGozados);
					}
					// STAF
					else {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteStaf.setCategoria("STAFF");
						reporteStaf.setMeta(meta);
						reporteStaf.setDiasGozados(diasGozados);
					}
				}
			}

			listResponse.add(reporteRedComecial);
			listResponse.add(reporteOperaciones);
			listResponse.add(reporteRecuperaciones);
			listResponse.add(reporteStaf);

			listResponse.stream().forEach(r -> {
				r.setDiasPendientes(r.getMeta() - r.getDiasGozados());
				r.setPorcentajeAvance(Double.parseDouble(String.format("%.2f", (double) r.getDiasGozados() / r.getMeta())));
			});

			break;
		case "TERRITORIONORANDINO":
			List<IReporteMeta> listTerritorioNorAndino = reporteMetaDao.reporteMetaTerritorioColectivo();
			for (IReporteMeta m : listTerritorioNorAndino) {
				if (Constantes.NOR_ANDINO.equals(m.getOpcional())) {
					// RED COMERCIAL
					if (Constantes.ASESOR_NEGOCIO.equals(m.getCategoria())
							|| Constantes.ASESOR_NEGOCIO_INDIVIDUAL.equals(m.getCategoria())
							|| Constantes.ASESOR_NEGOCIO_GRUPAL.equals(m.getCategoria())
							|| Constantes.GERENTE_CORREDOR.equals(m.getCategoria())
							|| Constantes.ADMINISTRADOR_NEGOCIO.equals(m.getCategoria())) {

						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteRedComecial.setCategoria("RED COMERCIAL");
						reporteRedComecial.setMeta(meta);
						reporteRedComecial.setDiasGozados(diasGozados);

					}
					// OPERACIONES
					else if (Constantes.ASESOR_SERVICIO.equals(m.getCategoria())
							|| Constantes.SUPERVISOR_OFICINA.equals(m.getCategoria())
							|| Constantes.ASESOR_PLATAFORMA.equals(m.getCategoria())) {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteOperaciones.setCategoria("OPERACIONES");
						reporteOperaciones.setMeta(meta);
						reporteOperaciones.setDiasGozados(diasGozados);
					}
					// RECUPERACIONES
					else if (Constantes.ANALISTA_COBRANZA.equals(m.getCategoria())
							|| Constantes.ANALISTA_RECUPERACIONES.equals(m.getCategoria())
							|| Constantes.RESPONSABLE_DEPARTAMENTO_COBRANZA.equals(m.getCategoria())) {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteRecuperaciones.setCategoria("RECUPERACIONES");
						reporteRecuperaciones.setMeta(meta);
						reporteRecuperaciones.setDiasGozados(diasGozados);
					}
					// STAF
					else {
						meta += m.getMeta();
						diasGozados += m.getDiasgozados();

						reporteStaf.setCategoria("STAFF");
						reporteStaf.setMeta(meta);
						reporteStaf.setDiasGozados(diasGozados);
					}
				}
			}

			listResponse.add(reporteRedComecial);
			listResponse.add(reporteOperaciones);
			listResponse.add(reporteRecuperaciones);
			listResponse.add(reporteStaf);

			listResponse.stream().forEach(r -> {
				r.setDiasPendientes(r.getMeta() - r.getDiasGozados());
				r.setPorcentajeAvance(Double.parseDouble(String.format("%.2f", (double) r.getDiasGozados() / r.getMeta())));
			});

			break;

		default:
			break;
		}

		return listResponse;
	}
}
