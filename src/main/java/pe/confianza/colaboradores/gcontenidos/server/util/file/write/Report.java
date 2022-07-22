package pe.confianza.colaboradores.gcontenidos.server.util.file.write;

import java.io.InputStream;

import pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Collection;

public class Report {
	
	private String type;
	private String title;
	private String subTitle;
	private Collection collection;
	private InputStream logo;
	
	public Report() {
		this.collection = new Collection();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public Collection getCollection() {
		return collection;
	}
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	public InputStream getLogo() {
		return logo;
	}
	public void setLogo(InputStream logo) {
		this.logo = logo;
	}
	
	

}
