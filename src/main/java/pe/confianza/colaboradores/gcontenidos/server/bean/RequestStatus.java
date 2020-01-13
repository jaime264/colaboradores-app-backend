package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class RequestStatus implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private Integer codeStatus;
	private String msgStatus;
	
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
		
}
