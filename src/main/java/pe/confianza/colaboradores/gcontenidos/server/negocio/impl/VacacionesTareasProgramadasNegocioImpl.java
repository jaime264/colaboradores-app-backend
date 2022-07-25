package pe.confianza.colaboradores.gcontenidos.server.negocio.impl;

import java.io.ByteArrayInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Empleado;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.NotificacionTipo;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionAprobadorNivelI;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionMetaResumen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionPeriodoResumen;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.VacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.EnvioNotificacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.EmpleadoService;
import pe.confianza.colaboradores.gcontenidos.server.service.NotificacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.PeriodoVacacionService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionAprobadorService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaResumenService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionMetaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionPeriodoResumenService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionProgramacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.CargaParametros;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoRegistro;
import pe.confianza.colaboradores.gcontenidos.server.util.EstadoVacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.MesesAnio;
import pe.confianza.colaboradores.gcontenidos.server.util.TipoNotificacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;
import pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Row;
import pe.confianza.colaboradores.gcontenidos.server.util.file.read.ColumnType;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.IReport;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.Report;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.ReportFactory;

@Service
public class VacacionesTareasProgramadasNegocioImpl implements VacacionesTareasProgramadasNegocio {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VacacionesTareasProgramadasNegocioImpl.class);
	
	@Autowired
	private PeriodoVacacionService periodoVacacionService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private VacacionProgramacionService vacacionProgramacionService;
	
	@Autowired
	private CargaParametros cargaParametros;
	
	@Autowired
	private VacacionMetaService vacacionMetaService;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private VacacionMetaResumenService vacacionMetaResumenService;
	
	@Autowired
	private VacacionPeriodoResumenService vacacionPeriodoResumenService;
	
	@Autowired
	private VacacionAprobadorService vacacionAprobadorService;
	
	@Autowired
	private EnvioNotificacionNegocio envioNotificacionNegocio;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ReportFactory reportFactory;

	@Override
	public void actualizarEstadoProgramaciones() {
		LOGGER.info("[BEGIN] actualizarEstadoProgramaciones " + LocalDate.now());
		vacacionProgramacionService.actualizarEstadoProgramaciones();
		LOGGER.info("[END] actualizarEstadoProgramaciones " + LocalDate.now());
	}

	@Override
	public void actualizarPeriodos() {
		LOGGER.info("[BEGIN] actualizarPeridos " + LocalDate.now());
		List<Empleado> lstEmpleado = empleadoService.listar();
		for (Empleado empleado : lstEmpleado) {
			periodoVacacionService.actualizarPeriodos(empleado, "TAREA_PROGRAMADA");
		}
		LOGGER.info("[END] actualizarPeridos " + LocalDate.now());
	}

	@Override
	public void consolidarMetasAnuales(boolean prod) {
		LOGGER.info("[BEGIN] consolidarMetasAnuales " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaCorte = cargaParametros.getFechaCorteMeta();
		if(prod) {
			if(fechaActual.getDayOfMonth() == fechaCorte.getDayOfMonth() && fechaActual.getMonthValue() == fechaCorte.getMonthValue()) {
				List<Empleado> lstEmpleado = empleadoService.listar();
				lstEmpleado.forEach(e -> {
					vacacionMetaService.consolidarMetaAnual(e, fechaCorte.getYear() + 1, "TAREA_PROGRAMADA");
				});
			}
		} else {
			List<Empleado> lstEmpleado = empleadoService.listar();
			lstEmpleado.forEach(e -> {
				vacacionMetaService.consolidarMetaAnual(e, fechaCorte.getYear() + 1, "TAREA_PROGRAMADA");
			});
		}
		LOGGER.info("[END] consolidarMetasAnuales " + LocalDate.now());
	}

	@Override
	public void registroNotificacionesInicioRegistroProgramacion() {
		LOGGER.info("[BEGIN] registroNotificacionesInicioRegistroProgramacion " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioProgramacion = cargaParametros.getFechaInicioRegistroProgramacion();
		LocalDate fechaFinProgramacion = cargaParametros.getFechaFinRegistroProgramacion();
		int anio = cargaParametros.getMetaVacacionAnio();
		LocalDate fechaEnvioNotificaicon = fechaInicioProgramacion.minusDays(1);
		if(fechaActual.getDayOfMonth() == fechaEnvioNotificaicon.getDayOfMonth() && fechaActual.getMonthValue() == fechaEnvioNotificaicon.getMonthValue()) {
			Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_COLABORADOR.valor);
			String titulo = "VACACIONES - INICIO PROGRAMACION";
			String descripcion = Utilitario.obtenerMensaje(messageSource, "vacaciones.notificacion.inicio_programacion",
					new Object[] { anio, fechaInicioProgramacion.toString(), fechaFinProgramacion.toString() });
			if(opt.isPresent()) {
				List<Empleado> lstEmpleado = empleadoService.listar();
				for (Empleado empleado : lstEmpleado) {
					notificacionService.registrar(titulo, descripcion, "", opt.get(), empleado, "TAREA_PROGRAMADA");
				}
			}
		}
		LOGGER.info("[END] registroNotificacionesInicioRegistroProgramacion " + LocalDate.now());
	}

	@Override
	public void registroNotificacionesMetaIncompleta() {
		LOGGER.info("[BEGIN] registroNotificacionesMetaIncompleta " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioProgramacion = cargaParametros.getFechaInicioRegistroProgramacion();
		LocalDate fechaFinProgramacion = cargaParametros.getFechaFinRegistroProgramacion();
		int intervaloRecordatorios = cargaParametros.getIntervaloDiasRecordatorioVacaciones();
		int anio = cargaParametros.getMetaVacacionAnio();
		LocalDate fechaRecordatorio = fechaInicioProgramacion.plusDays(intervaloRecordatorios);
		while (fechaRecordatorio.isBefore(fechaFinProgramacion)) {
			if(fechaRecordatorio.getDayOfWeek() != DayOfWeek.SATURDAY && fechaRecordatorio.getDayOfWeek() != DayOfWeek.SUNDAY) {
				if(fechaActual.getDayOfMonth() == fechaRecordatorio.getDayOfMonth() && fechaActual.getMonthValue() == fechaRecordatorio.getMonthValue()) {
					Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_COLABORADOR.valor);
					String titulo = "VACACIONES - META INCOMPLETA";
					
					if(opt.isPresent()) {
						
							List<VacacionMetaResumen> resumenes = vacacionMetaResumenService.listarResumenAnio(anio);
							for (VacacionMetaResumen resumen : resumenes) {
								if(resumen.getMeta() > 0) {
									String descripcion = Utilitario.obtenerMensaje(messageSource, "vacaciones.notificacion.meta_incompleta",resumen.getMeta(), anio);
									Optional<Empleado> optEmpleado = empleadoService.buscarPorId(resumen.getEmpleadoId());
									if(optEmpleado.isPresent()) {
										notificacionService.registrar(titulo, descripcion, "", opt.get(), optEmpleado.get(), "TAREA_PROGRAMADA");
									}
								}
							}
						
					}
				}
				fechaRecordatorio = fechaRecordatorio.plusDays(intervaloRecordatorios);
			} else {
				fechaRecordatorio = fechaRecordatorio.plusDays(fechaRecordatorio.getDayOfWeek() == DayOfWeek.SATURDAY ? 2 : 1);
			}			
		}
		LOGGER.info("[END] registroNotificacionesMetaIncompleta " + LocalDate.now());
	}

	@Override
	public void registroNotificacionesSinRegistroProgramacion() {
		LOGGER.info("[BEGIN] registroNotificacionesSinRegistroProgramacion " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioProgramacion = cargaParametros.getFechaInicioRegistroProgramacion();
		LocalDate fechaFinProgramacion = cargaParametros.getFechaFinRegistroProgramacion();
		int intervaloRecordatorios = cargaParametros.getIntervaloDiasRecordatorioVacaciones();
		int anio = cargaParametros.getMetaVacacionAnio();
		LocalDate fechaRecordatorio = fechaInicioProgramacion.plusDays(intervaloRecordatorios);
		while (fechaRecordatorio.isBefore(fechaFinProgramacion)) {
			if(fechaRecordatorio.getDayOfWeek() != DayOfWeek.SATURDAY && fechaRecordatorio.getDayOfWeek() != DayOfWeek.SUNDAY) {
				if(fechaActual.getDayOfMonth() == fechaRecordatorio.getDayOfMonth() && fechaActual.getMonthValue() == fechaRecordatorio.getMonthValue()) {
					Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_COLABORADOR.valor);
					String titulo = "VACACIONES - NO HA REGISTRADO VACACIONES";
					if(opt.isPresent()) {
						
							List<VacacionMetaResumen> resumenes = vacacionMetaResumenService.listarResumenAnio(anio);
							for (VacacionMetaResumen resumen : resumenes) {
								if(resumen.getMetaInicial() == resumen.getMeta()) {
									Optional<Empleado> optEmpleado = empleadoService.buscarPorId(resumen.getEmpleadoId());
									if(optEmpleado.isPresent()) {
										String descripcion = Utilitario.obtenerMensaje(messageSource, "vacaciones.notificacion.sin_registro_programacion",
												 cargaParametros.getFechaFinRegistroProgramacion());
										notificacionService.registrar(titulo, descripcion, "", opt.get(), optEmpleado.get(), "TAREA_PROGRAMADA");
									}
								}
							}
						
					}
				}
				fechaRecordatorio = fechaRecordatorio.plusDays(intervaloRecordatorios);
			} else {
				fechaRecordatorio = fechaRecordatorio.plusDays(fechaRecordatorio.getDayOfWeek() == DayOfWeek.SATURDAY ? 2 : 1);
			}			
		}
		LOGGER.info("[END] registroNotificacionesSinRegistroProgramacion " + LocalDate.now());
	}

	@Override
	public void registroNotificacionesJefeColaboradoresSinRegistroProgramacion() {
		LOGGER.info("[BEGIN] registroNotificacionesJefeColaboradoresSinRegistroProgramacion " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioProgramacion = cargaParametros.getFechaInicioRegistroProgramacion();
		LocalDate fechaFinProgramacion = cargaParametros.getFechaFinRegistroProgramacion();
		int intervaloRecordatorios = cargaParametros.getIntervaloDiasRecordatorioVacaciones();
		int anio = cargaParametros.getMetaVacacionAnio();
		LocalDate fechaRecordatorio = fechaInicioProgramacion.plusDays(intervaloRecordatorios);
		Map<Long, List<String>> aprobadores = new HashMap<Long, List<String>>();
		while (fechaRecordatorio.isBefore(fechaFinProgramacion)) {
			if(fechaRecordatorio.getDayOfWeek() != DayOfWeek.SATURDAY && fechaRecordatorio.getDayOfWeek() != DayOfWeek.SUNDAY) {
				if(fechaActual.getDayOfMonth() == fechaRecordatorio.getDayOfMonth() && fechaActual.getMonthValue() == fechaRecordatorio.getMonthValue()) {
					Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_APROBADOR.valor);
					if(opt.isPresent()) {
						
							List<VacacionPeriodoResumen> resumenes = vacacionPeriodoResumenService.listarPorAnio(anio);
							for (VacacionPeriodoResumen resumen : resumenes) {
								double vacacionesDiasSolicitados = resumen.getPeriodoVencidoDiasRegistradosGozar() + resumen.getPeriodoVencidoDiasGeneradosGozar() + resumen.getPeriodoVencidoDiasAprobadosGozar()
																+ resumen.getPeriodoTruncoDiasRegistradosGozar() + resumen.getPeriodoTruncoDiasGeneradosGozar() + resumen.getPeriodoTruncoDiasAprobadosGozar();
								if(vacacionesDiasSolicitados == 0) {
									if(aprobadores.get(resumen.getAprobadorNivel1Id()) == null) {
										aprobadores.put(resumen.getAprobadorNivel1Id(), new ArrayList<>());
									}
									aprobadores.get(resumen.getAprobadorNivel1Id()).add(resumen.getNombres() + " " + resumen.getApellidoPaterno());
								}
							}
							if(aprobadores.size() > 0) {
								String titulo = "VACACIONES - COLABORADORES PENDIENTES DE REGISTRO";
								for (Map.Entry<Long, List<String>> aprobador : aprobadores.entrySet()) {
									Optional<Empleado> optEmpleado = empleadoService.buscarPorId(aprobador.getKey());
									if(optEmpleado.isPresent()) {
										String descripcion = Utilitario.obtenerMensaje(messageSource, "vacaciones.notificacion.jefe.colaboradores_pendiente_registro",
												new Object[] { String.join(", ", aprobador.getValue()) });
										notificacionService.registrar(titulo, descripcion, "", opt.get(), optEmpleado.get(), "TAREA_PROGRAMADA");
									}
								}
							}
						
					}
					
				}
				fechaRecordatorio = fechaRecordatorio.plusDays(intervaloRecordatorios);
			} else {
				fechaRecordatorio = fechaRecordatorio.plusDays(fechaRecordatorio.getDayOfWeek() == DayOfWeek.SATURDAY ? 2 : 1);
			}
		}
		LOGGER.info("[END] registroNotificacionesJefeColaboradoresSinRegistroProgramacion " + LocalDate.now());
	}

	@Override
	public void registroNotificacionJefePendienteAprobacionProgramacion() {
		LOGGER.info("[BEGIN] registroNotificacionJefePendienteAprobacionProgramacion " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioProgramacion = cargaParametros.getFechaInicioRegistroProgramacion();
		LocalDate fechaFinProgramacion = cargaParametros.getFechaFinRegistroProgramacion();
		int intervaloRecordatorios = cargaParametros.getIntervaloDiasRecordatorioVacaciones();
		int anio = cargaParametros.getMetaVacacionAnio();
		LocalDate fechaRecordatorio = fechaInicioProgramacion.plusDays(intervaloRecordatorios);
		Map<Long, List<Long>> aprobadores = new HashMap<>();
		while (fechaRecordatorio.isBefore(fechaFinProgramacion)) {
			if (fechaRecordatorio.getDayOfWeek() != DayOfWeek.SATURDAY	&& fechaRecordatorio.getDayOfWeek() != DayOfWeek.SUNDAY) {
				if (fechaActual.getDayOfMonth() == fechaRecordatorio.getDayOfMonth()
						&& fechaActual.getMonthValue() == fechaRecordatorio.getMonthValue()) {
					Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_APROBADOR.valor);
					if (opt.isPresent()) {
						List<VacacionPeriodoResumen> resumenes = vacacionPeriodoResumenService.listarPorAnio(anio);
						for (VacacionPeriodoResumen resumen : resumenes) {
							double vacacionesDiasAprobadas = resumen.getPeriodoVencidoDiasAprobadosGozar()	+ resumen.getPeriodoTruncoDiasAprobadosGozar();
							if (vacacionesDiasAprobadas > 0) {
								if (aprobadores.get(resumen.getAprobadorNivel1Id()) == null) {
									aprobadores.put(resumen.getAprobadorNivel1Id(), new ArrayList<>());
								}
								aprobadores.get(resumen.getAprobadorNivel1Id()).add(resumen.getIdEmpleado());
							}
						}
						if (aprobadores.size() > 0) {
							String titulo = "VACACIONES - PENDIENTES DE APROBACIÓN";
							for (Map.Entry<Long, List<Long>> aprobador : aprobadores.entrySet()) {
								Optional<Empleado> optEmpleado = empleadoService.buscarPorId(aprobador.getKey());
								if (optEmpleado.isPresent()) {
									int empleadosAprobados = aprobador.getValue().size();
									String descripcion = Utilitario.obtenerMensaje(messageSource, "vacaciones.notificacion.jefe.pendiente_aprobacion", empleadosAprobados);
									notificacionService.registrar(titulo, descripcion, "", opt.get(), optEmpleado.get(), "TAREA_PROGRAMADA");
								}
							}
						}
					}
				}
				fechaRecordatorio = fechaRecordatorio.plusDays(intervaloRecordatorios);
			} else {
				fechaRecordatorio = fechaRecordatorio.plusDays(fechaRecordatorio.getDayOfWeek() == DayOfWeek.SATURDAY ? 2 : 1);
			}
		}
		LOGGER.info("[END] registroNotificacionJefePendienteAprobacionProgramacion " + LocalDate.now());
	}
	
	@Override
	public void registrarNotificacionesAutomaticas() {
		LOGGER.info("[BEGIN] registrarNotificacionesAutomaticas " + LocalDate.now());
		registroNotificacionesInicioRegistroProgramacion();
		registroNotificacionesMetaIncompleta();
		registroNotificacionesSinRegistroProgramacion();
		registroNotificacionesJefeColaboradoresSinRegistroProgramacion();
		registroNotificacionJefePendienteAprobacionProgramacion();
		registrarNotificacionesReprogramacionMensual();
		enviarCorreoReporteAprobadorNivelI();
		LOGGER.info("[END] registrarNotificacionesAutomaticas " + LocalDate.now());
	}

	@Override
	public void enviarNotificacionesAppPendienteVacaciones() {
		LOGGER.info("[BEGIN] enviarNotificacionesAppPendienteVacaciones " + LocalDate.now());
		envioNotificacionNegocio.enviarNotificacionesApp(TipoNotificacion.VACACIONES_APROBADOR);
		envioNotificacionNegocio.enviarNotificacionesApp(TipoNotificacion.VACACIONES_COLABORADOR);
		LOGGER.info("[END] enviarNotificacionesAppPendienteVacaciones " + LocalDate.now());
	}

	@Override
	public void enviarNotificacionesCorreoPendienteVacaciones() {
		LOGGER.info("[BEGIN] enviarNotificacionesCorreoPendienteVacaciones " + LocalDate.now());
		envioNotificacionNegocio.enviarNotificacionesCorreo(TipoNotificacion.VACACIONES_APROBADOR);
		envioNotificacionNegocio.enviarNotificacionesCorreo(TipoNotificacion.VACACIONES_COLABORADOR);
		LOGGER.info("[END] enviarNotificacionesCorreoPendienteVacaciones " + LocalDate.now());
	}

	@Override
	public void registrarNotificacionesReprogramacionMensual() {
		LOGGER.info("[BEGIN] registrarNotificacionesReprogramacionMensual " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		if(fechaActual.getDayOfMonth() >= cargaParametros.getDiaNotificacionReprogramacion()) {
			Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_COLABORADOR.valor);
			if (opt.isPresent()) {
				List<Empleado> lstEmpleado = empleadoService.listar();
				String titulo = "VACACIONES - REPROGRAMACIÓN";
				String descripcion = Utilitario.obtenerMensaje(messageSource, "vacaciones.notificacion.reprogramacion", MesesAnio.buscarPorValor(fechaActual.getMonthValue() + 1));
				for (Empleado empleado : lstEmpleado) {
					if(empleado.getEstadoRegistro().equals(EstadoRegistro.ACTIVO.valor)) {
						List<VacacionProgramacion> programaciones = vacacionProgramacionService.listarProgramacionesPorAnio(cargaParametros.getAnioPresente(), empleado.getUsuarioBT())
								.stream()
								.filter(p -> p.getIdEstado() == EstadoVacacion.APROBADO.id && p.getFechaFin().getMonthValue() == fechaActual.getMonthValue() + 1)
								.collect(Collectors.toList());
						programaciones = programaciones == null ? new ArrayList<>() : programaciones;
						if(!programaciones.isEmpty()) {
							notificacionService.registrar(titulo, descripcion, "", opt.get(), empleado, "TAREA_PROGRAMADA");
						}
					}
				}
			}
		}
		LOGGER.info("[BEGIN] registrarNotificacionesReprogramacionMensual " + LocalDate.now());
	}

	@Override
	public void enviarCorreoReporteAprobadorNivelI() {
		LOGGER.error("[BEGIN] enviarCorreoReporteAprobadorNivelI " + LocalDate.now());
		LocalDate fechaActual = LocalDate.now();
		if(fechaActual.getDayOfMonth() == cargaParametros.getDiaNotificacionReprogramacion()) {
			Optional<NotificacionTipo> opt = notificacionService.obtenerTipoNotificacion(TipoNotificacion.VACACIONES_APROBADOR.valor);
			if (opt.isPresent()) {
				List<VacacionAprobadorNivelI> aprobadores = vacacionAprobadorService.listarAprobadoresNivelI();
				for (VacacionAprobadorNivelI aprobador : aprobadores) {
					LOGGER.error("[APROBADOR] " + aprobador.getNombreCompleto());
					Report reporte = new Report();
					reporte.setType("XLSX");
					reporte.setTitle("REPORTE VACACIONES");
					reporte.getCollection().addHeader("Colaborador", ColumnType.STRING);
					reporte.getCollection().addHeader("Agencia", ColumnType.STRING);
					reporte.getCollection().addHeader("Cargo", ColumnType.STRING);
					reporte.getCollection().addHeader("Fecha inicio", ColumnType.LOCALDATE);
					reporte.getCollection().addHeader("Fecha fin", ColumnType.LOCALDATE);
					reporte.getCollection().addHeader("Días", ColumnType.INTEGER);
					Map<Empleado, List<VacacionProgramacion>> empleadoProg = vacacionProgramacionService.listarProgramacionesPorAnioYAprobadorNivelI(LocalDate.now().getYear(), aprobador.getCodigo());
					for (Map.Entry<Empleado, List<VacacionProgramacion>> programacionesEmpl : empleadoProg.entrySet()) {
						LOGGER.error("[EMPLEADO] " + programacionesEmpl.getKey().getNombreCompleto());
						for (VacacionProgramacion programacion : programacionesEmpl.getValue()) {
							if(programacion.getFechaInicio().getMonthValue() == LocalDate.now().getMonthValue() + 1) {
								String colaborador = programacion.getPeriodo().getEmpleado().getNombreCompleto();
								String agencia = programacion.getPeriodo().getEmpleado().getAgencia() == null ? "" : programacion.getPeriodo().getEmpleado().getAgencia().getDescripcion();
								String cargo = programacion.getPeriodo().getEmpleado().getPuesto() == null ? "" : programacion.getPeriodo().getEmpleado().getPuesto().getDescripcion();
								LocalDate fechaInicio = programacion.getFechaInicio();
								LocalDate fechaFin = programacion.getFechaFin();
								int cantidadDias = programacion.getNumeroDias();
								Row row = new Row();
								row.addCell("Colaborador", colaborador);
								row.addCell("Agencia", agencia);
								row.addCell("Cargo", cargo);
								row.addCell("Fecha inicio", fechaInicio);
								row.addCell("Fecha fin", fechaFin);
								row.addCell("Días", cantidadDias);
								reporte.getCollection().setCurrentRow(row);
								reporte.getCollection().addRow();
							}
						}
					}
					try {
						IReport<ByteArrayInputStream> excel = reportFactory.createReport(reporte);
						notificacionService.enviarCorreoReporte("REPORTE VACACIONES", "", aprobador.getEmail(), aprobador.getNombreCompleto(),
								"coloboradores_vacaciones.xlsx", "application/octet-stream", new byte[excel.build().available()]);
					} catch (Exception e) {
						LOGGER.error("[ERROR] enviarCorreoReporteAprobadorNivelI", e);
					}
				}
			}
		}
		LOGGER.error("[BEGIN] enviarCorreoReporteAprobadorNivelI " + LocalDate.now());
	}

	@Override
	public void enviarCorreoReporteJefe() {
		// TODO Auto-generated method stub
		
	}

}
