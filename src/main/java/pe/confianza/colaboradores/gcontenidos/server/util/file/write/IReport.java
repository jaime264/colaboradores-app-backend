package pe.confianza.colaboradores.gcontenidos.server.util.file.write;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IReport<T> {
	
	public static final  Logger LOGGER = LoggerFactory.getLogger("IReport");
	
	T generateReport();

}
