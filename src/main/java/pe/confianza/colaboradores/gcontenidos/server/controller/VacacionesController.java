package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import net.sf.jasperreports.engine.JRException;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.service.AuditoriaService;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100",
		"http://localhost:4200", "http://172.20.9.12:7445" })
public class VacacionesController {

	@Autowired
	private VacacionService vacacionService;

	@Autowired
	private AuditoriaService auditoriaService;
	
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
		Vacacion vacacionOut =  vacacionService.showVacationByUser(vacacion.getCodigoSpring());

		Gson gson = new Gson();
		String jsonData = gson.toJson(vacacion);
		if(vacacionOut != null) {
			auditoriaService.createAuditoria("002", "006", 0, "OK", BsonDocument.parse(jsonData));
		}else {
			auditoriaService.createAuditoria("002", "006", 99, "Not Ok", BsonDocument.parse(jsonData));
		}
		
		return vacacionOut;
	}
	

	

}
