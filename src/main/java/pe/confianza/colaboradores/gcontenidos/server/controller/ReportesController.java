package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarReportes;
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
}
