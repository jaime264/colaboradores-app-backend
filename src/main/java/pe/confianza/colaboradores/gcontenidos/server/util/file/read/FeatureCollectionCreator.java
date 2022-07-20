package pe.confianza.colaboradores.gcontenidos.server.util.file.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

import pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Collection;


/**
 * 
 * @author kenyo.pecho
 *
 */
public abstract class FeatureCollectionCreator implements IFeatureCollection {

	protected final InputStream inputStream;
	private final Collection collection;
	protected boolean spatialCollection = false;
	protected final Properties properties;
	protected File file = null;
	
	public FeatureCollectionCreator(final Path filePath) throws FileNotFoundException {
		this.inputStream = new FileInputStream(filePath.toFile());
		this.collection = new Collection();
		this.properties = new Properties();
	}
	
	public FeatureCollectionCreator(final File file) throws FileNotFoundException {
		this.file = file; 
		this.inputStream = new FileInputStream(file);
		this.collection = new Collection();
		this.properties = new Properties();
	}
	
	public FeatureCollectionCreator(final InputStream inputStream) throws FileNotFoundException {
		this.inputStream = inputStream;
		this.collection = new Collection();
		this.properties=new Properties();
	}
	
	public void read() {
		read(this.collection);
	}
	


	public Collection getCollection() {
		return collection;
	}

	public Properties getProperties() {
		return properties;
	}
	
	protected abstract void read(final Collection collection);
	
	
}
