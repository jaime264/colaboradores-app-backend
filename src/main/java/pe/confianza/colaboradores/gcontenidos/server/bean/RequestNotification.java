package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;
import java.util.List;

public class RequestNotification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] include_player_ids;
	private List<Heading> headings;
	
	public String[] getInclude_player_ids() { return include_player_ids; }
	public void setInclude_player_ids(String[] include_player_ids) { this.include_player_ids = include_player_ids; }
	
	public List<Heading> getHeadings() { return headings; }
	public void setHeadings(List<Heading> headings) { this.headings = headings; }
	
	

}
