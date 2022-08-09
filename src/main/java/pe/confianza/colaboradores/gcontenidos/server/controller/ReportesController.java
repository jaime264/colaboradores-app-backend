package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestFiltroVacacionesAprobacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestReporteMeta;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteColaboradores;
import pe.confianza.colaboradores.gcontenidos.server.service.ReportesService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api/reporte")
@Api(value = "Reportes API REST Endpoint", description = "Operaciones con reportes")
public class ReportesController {

	private static Logger logger = LoggerFactory.getLogger(PublicacionController.class);

	@Autowired
	ReportesService reportesService;

	@PostMapping("/reporte/list")
	public ResponseEntity<List<ReporteColaboradores>> list() {
		List<ReporteColaboradores> lstReportes = reportesService.obtenerTodos();

		return new ResponseEntity<List<ReporteColaboradores>>(lstReportes, HttpStatus.OK);
	}

	@ApiOperation(notes = "Consulta de reporte de colaboradores", value = "url proxy /reportecolaboradores")
	@PostMapping("/colaboradores")
	public ResponseEntity<ResponseStatus> consultar(@RequestBody RequestListarReportes request) {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(reportesService.listarColaboradores(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@ApiOperation(notes = "Consulta de reporte de colaboradores", value = "url proxy /reportecolaboradores")
	@PostMapping("/obtener-filtros")
	public ResponseEntity<?> getFitrosVacAprobador(@RequestBody RequestFiltroVacacionesAprobacion reqFiltros)
			throws IOException {

		logger.info("RequestFiltroVacacionesAprobacion: " + reqFiltros.toString());

		List<Map<String, String>> vacpro = reportesService.listarFiltrosReporteColaborador(reqFiltros);

		return new ResponseEntity<List<Map<String, String>>>(vacpro, HttpStatus.OK);
	}

	@ApiOperation(notes = "Consulta de reporte de la meta de los colaboradoes", value = "url proxy /reportecolaboradoresmeta")
	@PostMapping("/meta")
	public ResponseEntity<?> getReortesMeta(@RequestBody RequestReporteMeta request) throws IOException {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(reportesService.listarReporteMeta(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@ApiOperation(notes = "Consulta de reporte de la meta de los colaboradoes", value = "url proxy /reportecolaboradorescolectivo")
	@PostMapping("/colectivos")
	public ResponseEntity<?> getReortesColectivos(@RequestBody RequestReporteMeta request) throws IOException {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(reportesService.listarReporteColectivos(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@ApiOperation(notes = "Consulta de reporte de la meta de los colaboradoes", value = "url proxy /reportecolaboradoresterritorio")
	@PostMapping("/territorios")
	public ResponseEntity<?> getReortesTerritorios(@RequestBody RequestReporteMeta request) throws IOException {
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setCodeStatus(Constantes.COD_OK);
		responseStatus.setMsgStatus(Constantes.OK);
		responseStatus.setResultObj(reportesService.listarReporteTerritorios(request));
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}
	
	@PostMapping(value = "/colaboradores/excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> excelReporteColaboradores(@RequestBody RequestListarReportes req) {
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Disposition", "inline; filename=reporte.xlsx");
		 return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				 .body(new InputStreamResource(reportesService.reporteColaboradores(req)));
		
	}

}
