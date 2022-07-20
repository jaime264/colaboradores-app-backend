package pe.confianza.colaboradores.gcontenidos.server.util.file.read;

import java.util.Properties;

import pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Collection;


/**
 * 
 * @author kenyo.pecho
 *
 */
public interface IFeatureCollection {

	Collection getCollection();
	
	Properties getProperties();
}
