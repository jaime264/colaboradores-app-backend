package pe.confianza.colaboradores.gcontenidos.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.confianza.colaboradores.gcontenidos.server.bean.ResponseStatus;
import pe.confianza.colaboradores.gcontenidos.server.dao.VacacionesDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Vacacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@Service
public class VacacionServiceImpl implements VacacionService {

	@Autowired
	private VacacionesDao vacacionesDao;
	@Override
	public ResponseStatus importExcel(XSSFSheet hojaExcel) {

		vacacionesDao.deleteAll();
		Iterator<Row> rowIterator = hojaExcel.rowIterator();
		DataFormatter dataFormatter = new DataFormatter();
		List<Vacacion> lstVacaciones = new ArrayList<Vacacion>();
		ResponseStatus status = new ResponseStatus();

		int posicionCabecera = 0;

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Vacacion vacacion = new Vacacion();
			int posicionCell = 1;
			if (posicionCabecera > 0) {
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);

					if (posicionCell == Constantes.POSICION_EXCEL.POSICION_CODIGO_SPRING) {
						vacacion.setCodigoSpring(cellValue);
					} else if (posicionCell == Constantes.POSICION_EXCEL.POSICION_USUARIO_BT) {
						vacacion.setUsuarioBT(cellValue);
					} else if (posicionCell == Constantes.POSICION_EXCEL.POSICION_DIAS_VENCIDOS) {
						vacacion.setCantDiasVencidos(Double.parseDouble(cellValue.replace(",", ".")));
					} else if (posicionCell == Constantes.POSICION_EXCEL.POSICION_FECHA_VENCIDOS) {
						vacacion.setFechaDiasVencidos(cellDateToString(cell));
					} else if (posicionCell == Constantes.POSICION_EXCEL.POSICION_DIAS_TRUNCOS) {
						vacacion.setCantDiasTruncos(Double.parseDouble(cellValue.replace(",", ".")));
					} else if (posicionCell == Constantes.POSICION_EXCEL.POSICION_FECHA_TRUNCOS) {
						vacacion.setFechaDiasTruncos(cellDateToString(cell));
					}
					posicionCell++;
				}
				lstVacaciones.add(vacacion);
			}
			posicionCabecera++;
		}

		lstVacaciones = vacacionesDao.saveAll(lstVacaciones);
		if (lstVacaciones.size() > 0) {
			status.setCodeStatus(0);
			status.setMsgStatus("Registro creado correctamente");
			status.setResultObj(lstVacaciones.get(0).get_id());
		}
		return status;

	}

	private String cellDateToString(Cell cell) {
		SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = cell.getDateCellValue();
		return DtFormat.format(date).toString();
	}

	@Override
	public Vacacion showVacationByUser(String codigoSpring) {
		return vacacionesDao.findByCodigoSpring(codigoSpring);
	}

}
