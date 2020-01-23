package pe.confianza.colaboradores.gcontenidos.server.bean.bt;

import java.io.Serializable;

public class ResponseJsonVersion implements Serializable{
	private String cerr;
	private String merr;
	private int terr;

	public String getCerr() {
		return cerr;
	}

	public void setCerr(String cerr) {
		this.cerr = cerr;
	}

	public String getMer() {
		return merr;
	}

	public void setMerr(String merr) {
		this.merr = merr;
	}

	public int getTerr() {
		return terr;
	}

	public void setTerr(int terr) {
		this.terr = terr;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5590469522500171940L;
}
