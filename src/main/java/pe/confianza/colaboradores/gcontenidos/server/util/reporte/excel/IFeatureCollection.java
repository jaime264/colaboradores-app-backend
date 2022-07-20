package pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel;

import java.util.Properties;

import pe.confianza.colaboradores.gcontenidos.server.util.reporte.excel.collection.Collection;

/**
 * 
 * @author kenyo.pecho
 *
 */
public interface IFeatureCollection {

	Collection getCollection();
	
	Properties getProperties();
}
