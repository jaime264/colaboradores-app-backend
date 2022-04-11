package pe.confianza.colaboradores.gcontenidos.server.bean;

public class Notification {

	private String app_id;
	private String[] included_segments;
	private String[] include_player_ids;
	private Object data;
	private Heading headings;
	private Heading contents;
	
	public String getApp_id() { return app_id; }
	public void setApp_id(String app_id) { this.app_id = app_id; }
	
	public String[] getIncluded_segments() { return included_segments; }
	public void setIncluded_segments(String[] included_segments) { this.included_segments = included_segments; }
	
	public String[] getInclude_player_ids() { return include_player_ids; }
	public void setInclude_player_ids(String[] include_player_ids) { this.include_player_ids = include_player_ids; }
	
	public Object getData() { return data; }
	public void setData(Object data) { this.data = data; }
	
	public Heading getHeadings() { return headings; }
	public void setHeadings(Heading headings) { this.headings = headings; }
	
	public Heading getContents() { return contents; }
	public void setContents(Heading contents) { this.contents = contents; }
	
}
