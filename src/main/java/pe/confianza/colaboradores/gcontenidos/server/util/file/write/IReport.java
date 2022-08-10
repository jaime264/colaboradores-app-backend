package pe.confianza.colaboradores.gcontenidos.server.util.file.write;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author kenyo.pecho
 *
 * @param <T>
 */
public interface IReport<T> {
	
	public static final  Logger LOGGER = LoggerFactory.getLogger("IReport");
	
	void build();
	
	T getReult();

}
