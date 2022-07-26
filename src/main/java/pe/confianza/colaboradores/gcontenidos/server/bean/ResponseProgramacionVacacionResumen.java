package pe.confianza.colaboradores.gcontenidos.server.bean;

public class ResponseProgramacionVacacionResumen extends ResponseProgramacionVacacion {
	
	private String nombreEmpleado;
	private int diasGozados;
	private int diasPorGozar;
	private int diasAnulados;
	private int diasReprogramados;
	
	public String getNombreEmpleado() {
		return nombreEmpleado;
	}
	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	public int getDiasGozados() {
		return diasGozados;
	}
	public void setDiasGozados(int diasGozados) {
		this.diasGozados = diasGozados;
	}
	public int getDiasPorGozar() {
		return diasPorGozar;
	}
	public void setDiasPorGozar(int diasPorGozar) {
		this.diasPorGozar = diasPorGozar;
	}
	public int getDiasAnulados() {
		return diasAnulados;
	}
	public void setDiasAnulados(int diasAnulados) {
		this.diasAnulados = diasAnulados;
	}
	public int getDiasReprogramados() {
		return diasReprogramados;
	}
	public void setDiasReprogramados(int diasReprogramados) {
		this.diasReprogramados = diasReprogramados;
	}
	
	

}
