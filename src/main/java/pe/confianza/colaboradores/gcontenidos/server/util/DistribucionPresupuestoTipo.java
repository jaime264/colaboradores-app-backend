package pe.confianza.colaboradores.gcontenidos.server.util;

public enum DistribucionPresupuestoTipo {
	
	TODO_PRESUPUESTO(1,  "Todo el presupuesto"),
	UN_PORCENTAJE_TOTAL(2, "Un porcentaje del total"),
	UN_MONTO_TOTAL(3, "Un monto del tota");
	
	public int codigo;
	
	public String descripcion;
	
	private DistribucionPresupuestoTipo(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public static double calcularMontoDistribuir(int codigoDistribucion, double montoTotalPresupuesto, Double porcentaje, Double montoDelTotal) {
		double montoDistribuir = 0;
		switch (codigoDistribucion) {
		case 1:
			montoDistribuir = montoTotalPresupuesto;
			break;
		case 2:
			montoDistribuir = montoTotalPresupuesto * porcentaje / 100;
			break;
		case 3:
			montoDistribuir = montoDelTotal;
			break;
		default:
			montoDistribuir = 0;
			break;
		}
		return montoDistribuir;
	}

}
