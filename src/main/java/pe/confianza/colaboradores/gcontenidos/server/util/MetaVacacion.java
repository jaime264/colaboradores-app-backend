package pe.confianza.colaboradores.gcontenidos.server.util;

public enum MetaVacacion {

	ENERO(1, 30),
	FEBRERO(2, 30),
	MARZO(3, 25),
	ABRIL(4, 22),
	MAYO(5, 20),
	JUNIO(6, 18),
	JULIO(7, 15),
	AGOSTO(8, 12),
	SETIEMBRE(9, 10),
	OCTUBRE(10, 8),
	NOVIEMBRE(11, 7),
	DICIEMBRE (12, 0);
	
	
	public int numeroeMes;
	public int cantidadDias;

	private MetaVacacion(int numeroeMes, int cantidadDias) {
		this.numeroeMes = numeroeMes;
		this.cantidadDias = cantidadDias;
	}
	
	public static Integer cantidadDias(int numeroMes) {
		
		for (MetaVacacion metaVac : MetaVacacion.values()) {
			
			if(numeroMes == metaVac.numeroeMes) {
				return metaVac.cantidadDias;
			}
			
		}
		
		return null;
				
	}
	
	

}
