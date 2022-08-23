package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class ResponseStatus implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private Integer codeStatus;
	private String msgStatus;
	private String titleStatus;
	private Object resultObj;
	
	public Integer getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(Integer codeStatus) {
		this.codeStatus = codeStatus;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	public Object getResultObj() {
		return resultObj;
	}
	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}
	public String getTitleStatus() {
		return titleStatus;
	}
	public void setTitleStatus(String titleStatus) {
		this.titleStatus = titleStatus;
	}
	
	
		
}
