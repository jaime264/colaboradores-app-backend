package pe.confianza.colaboradores.gcontenidos.server.util;

public enum EstadoRegistro {
	
	ACTIVO("A"),
	INACTIVO("I");
	
	public String valor;
	
	private EstadoRegistro(String valor) {
		this.valor = valor;
	}

}
