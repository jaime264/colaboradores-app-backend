package pe.confianza.colaboradores.gcontenidos.server.service;


import org.apache.poi.xssf.usermodel.XSSFSheet;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Vacacion;

public interface VacacionService {
	
	public ResponseStatus importExcel(XSSFSheet hojaExcel);

	public Vacacion showVacationByUser(String codigoSpring);
	

}
