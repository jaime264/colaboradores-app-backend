package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

public class ResponseStatus implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private Integer codeStatus;
	private String msgStatus;
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
		
}
