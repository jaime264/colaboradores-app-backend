package pe.confianza.colaboradores.gcontenidos.server.service;


import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import pe.confianza.colaboradores.gcontenidos.server.api.entity.VacacionPeriodo;
import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity.Vacacion;

public interface VacacionService {
	
	public ResponseStatus importExcel(XSSFSheet hojaExcel, String fechaCorte);

	public Vacacion showVacationByUser(String codigoSpring);	

}
