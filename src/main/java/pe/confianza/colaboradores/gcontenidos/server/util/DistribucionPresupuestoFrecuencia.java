package pe.confianza.colaboradores.gcontenidos.server.util;

public enum DistribucionPresupuestoFrecuencia {

	MENSUAL(1, 1, "Mensual"),
	TRIMESTRAL(2, 3, "Trimestral"),
	SEMESTRAL(3, 6, "Semestral");
	
	public int codigo;
	public int valor;
	public String descripcion;
	
	private DistribucionPresupuestoFrecuencia(int codigo, int valor, String descripcion) {
		this.codigo = codigo;
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public static DistribucionPresupuestoFrecuencia buscar(int codigo) {
		DistribucionPresupuestoFrecuencia frecuencia = null;
		for (DistribucionPresupuestoFrecuencia f : DistribucionPresupuestoFrecuencia.values()) {
			if(f.codigo == codigo) {
				frecuencia = f;
				break;
			}
		}
		return frecuencia;
	}
}
