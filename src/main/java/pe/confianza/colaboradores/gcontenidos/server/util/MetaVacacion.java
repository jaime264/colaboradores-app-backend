package pe.confianza.colaboradores.gcontenidos.server.util;

public enum MetaVacacion {

	ENERO(1, 30.0),
	FEBRERO(2, 30.0),
	MARZO(3, 25.0),
	ABRIL(4, 22.0),
	MAYO(5, 20.0),
	JUNIO(6, 18.0),
	JULIO(7, 15.0),
	AGOSTO(8, 12.0),
	SETIEMBRE(9, 10.0),
	OCTUBRE(10, 8.0),
	NOVIEMBRE(11, 7.0),
	DICIEMBRE (12, 0.0);
	
	
	public int numeroeMes;
	public Double cantidadDias;

	private MetaVacacion(int numeroeMes, Double cantidadDias) {
		this.numeroeMes = numeroeMes;
		this.cantidadDias = cantidadDias;
	}
	
	public static Double cantidadDias(int numeroMes) {
		
		for (MetaVacacion metaVac : MetaVacacion.values()) {
			
			if(numeroMes == metaVac.numeroeMes) {
				return metaVac.cantidadDias;
			}
			
		}
		
		return null;
				
	}
	
	

}
