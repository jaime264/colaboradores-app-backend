package pe.confianza.colaboradores.gcontenidos.server.util;

public enum MesesAnio {
	
	Enero(1, "Enero"),
	Febrero(2, "Febrero"),
	Marzo(3, "Marzo"),
	Abril(4, "Abril"),
	Mayo(5, "Mayo"),
	Junio(6, "Junio"),
	Julio(7, "Julio"),
	Agosto(8, "Agosto"),
	Setiembre(9, "Setiembre"),
	Octubre(10, "Octubre"),
	Noviembre(11, "Noviembre"),
	Diciembre(12, "Diciembre");
	
	public String descripcion;
	public int valor;
	
	private MesesAnio(int valor,  String descripcion) {
		this.valor = valor;
		this.descripcion = descripcion;
	}
	
	public static MesesAnio buscarPorValor(int valor) {
		for (MesesAnio mes : MesesAnio.values()) {
			if(mes.valor == valor)
				return mes;
		}
		return null;
	}
}
