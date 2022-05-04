package pe.confianza.colaboradores.gcontenidos.server.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import pe.confianza.colaboradores.gcontenidos.server.dao.ParametrosDao;
import pe.confianza.colaboradores.gcontenidos.server.model.entity.Parametro;
import pe.confianza.colaboradores.gcontenidos.server.service.ParametrosServiceImpl;

@Component
public class ParametrosConstants {

	@Autowired
	private ParametrosServiceImpl parametrosServiceImpl;

	private static List<Parametro> listParams = new ArrayList<>();

	private void populateParametros() {
		listParams = parametrosServiceImpl.listParams();
	}

	private String populateParametro(String cod) {
		for (Parametro parametro : listParams) {
			if (parametro.getCodigo().equals(cod)) {
				return parametro.getValor();
			}
		}
		return StringUtils.EMPTY;
	}

	// Parametros Vacaciones
	public String FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = null;
	public String FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = null;

	@PostConstruct
	protected void initialize() {
		populateParametros();

		FECHA_INICIO_REGISTRO_PROGRAMACION_VACACIONES = populateParametro("FECHA_INICIO_REG_PROG_VACACIONES");
		FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES = populateParametro("FECHA_FIN_REGISTRO_PROGRAMACION_VACACIONES");
		
	}

}
