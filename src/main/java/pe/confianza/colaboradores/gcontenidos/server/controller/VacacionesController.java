package pe.confianza.colaboradores.gcontenidos.server.controller;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JRException;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.service.VacacionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://200.107.154.52:6020", "http://localhost", "http://localhost:8100",
		"http://localhost:4200", "http://172.20.9.12:7445" })
public class VacacionesController {

	@Autowired
	private VacacionService vacacionService;
	
	@SuppressWarnings("resource")
	@PostMapping("/vacaciones/upload")
	public ResponseStatus uploadExcel(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException, JRException {
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		return vacacionService.importExcel(worksheet);
	}
	
	
	@GetMapping("/vacaciones/{id}")
	public Vacacion showVacationByUser(@PathVariable("id") String codigoSpring) throws IOException, JRException {
		return vacacionService.showVacationByUser(codigoSpring);
	}
	

	

}
