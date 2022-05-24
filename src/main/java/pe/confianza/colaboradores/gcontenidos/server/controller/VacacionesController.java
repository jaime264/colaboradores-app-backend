package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import net.sf.jasperreports.engine.JRException;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestCancelarProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestGenerarProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestListarVacacionProgramacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.RequestProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseProgramacionVacacion;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.negocio.ProgramacionVacacionNegocio;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionService;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@RestController
@RequestMapping("/api")
public class VacacionesController {
	
	private static Logger logger = LoggerFactory.getLogger(VacacionesController.class);

	@Autowired
	private VacacionService vacacionService;

	@Autowired
	private AuditoriaService auditoriaService;
	
	@Autowired
	private ProgramacionVacacionNegocio programacionVacacionNegocio;
	
	@SuppressWarnings("resource")
	@PostMapping("/vacaciones/upload/{fechaCorte}")
	public ResponseStatus uploadExcel(@RequestParam("file") MultipartFile reapExcelDataFile, @PathVariable("fechaCorte") String fechaCorte) throws IOException, JRException {
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		fechaCorte = fechaCorte.replace("-", "/");
		return vacacionService.importExcel(worksheet, fechaCorte);
	}
	
	
	@PostMapping("/vacaciones")
	public Vacacion showVacationByUser(@RequestBody Vacacion vacacion) throws IOException, JRException {
		Vacacion vacacionOut = null;
		logger.info("Empleado: " + vacacion.getCodigoSpring());
		Gson gson = new Gson();
		String jsonData = gson.toJson(vacacion);
		try {
			vacacionOut =  vacacionService.showVacationByUser(vacacion.getCodigoSpring());
			if(vacacionOut != null) {
				auditoriaService.createAuditoria("002", "006", 0, "OK", BsonDocument.parse(jsonData));
			}else {
				logger.error("Error al obtener datos de vacaciones: null");
				auditoriaService.createAuditoria("002", "006", 99, "Error al obtener datos de vacaciones: null", BsonDocument.parse(jsonData));
			}
		} catch(Exception e) {
			logger.error("Error al obtener datos de vacaciones: " + e.getMessage());
			auditoriaService.createAuditoria("002", "006", 99, "Error al obtener datos de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
		}
		return vacacionOut;
	}
	
	
	@PostMapping("/vacaciones/registro-programacion")
	public ResponseEntity<ResponseStatus> registroProgramacion(@Valid @RequestBody RequestProgramacionVacacion programacionRequest) {
		logger.info("Empleado: " + programacionRequest.getUsuarioBT());
		Gson gson = new Gson();
		String jsonData = gson.toJson(programacionRequest);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseProgramacionVacacion programacion = programacionVacacionNegocio.registro(programacionRequest);
			auditoriaService.createAuditoria("002", "006", Constantes.COD_OK, Constantes.OK, BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(programacion);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			auditoriaService.createAuditoria("002", "006", Constantes.COD_ERR, "Error al registrar programación de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/vacaciones/cancelacion-programacion")
	public ResponseEntity<ResponseStatus> cancelacionProgramacion(@Valid @RequestBody RequestCancelarProgramacionVacacion cancelacion) {
		Gson gson = new Gson();
		String jsonData = gson.toJson(cancelacion);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			programacionVacacionNegocio.cancelar(cancelacion);
			auditoriaService.createAuditoria("002", "006", Constantes.COD_OK, Constantes.OK, BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			auditoriaService.createAuditoria("002", "006", Constantes.COD_ERR, "Error al cancelar programación de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/vacaciones/generar-programacion")
	public ResponseEntity<ResponseStatus> generarProgramacion(@Valid @RequestBody RequestGenerarProgramacionVacacion generacion) {
		Gson gson = new Gson();
		String jsonData = gson.toJson(generacion);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			List<ResponseProgramacionVacacion> programaciones = programacionVacacionNegocio.generar(generacion);
			auditoriaService.createAuditoria("002", "006", Constantes.COD_OK, Constantes.OK, BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(programaciones);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			auditoriaService.createAuditoria("002", "006", Constantes.COD_ERR, "Error al generar programación de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/vacaciones/listar-programacion")
	public ResponseEntity<ResponseStatus> consultarProgramacion(@Valid @RequestBody RequestListarVacacionProgramacion request) {
		logger.info("Empleado: " + request.getUsuarioBT());
		Gson gson = new Gson();
		String jsonData = gson.toJson(request);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			List<ResponseProgramacionVacacion> lstProgramacion = programacionVacacionNegocio.consultar(request);
			auditoriaService.createAuditoria("002", "006", Constantes.COD_OK, Constantes.OK, BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_OK);
			responseStatus.setMsgStatus(Constantes.OK);
			responseStatus.setResultObj(lstProgramacion);
			return new ResponseEntity<>(responseStatus, HttpStatus.OK);
		} catch (Exception e) {
			auditoriaService.createAuditoria("002", "006", Constantes.COD_ERR, "Error al consultar programación de vacaciones: " + e.getMessage(), BsonDocument.parse(jsonData));
			responseStatus.setCodeStatus(Constantes.COD_ERR);
			responseStatus.setMsgStatus(e.getMessage());
			return new ResponseEntity<>(responseStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
