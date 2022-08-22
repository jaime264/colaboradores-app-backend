package pe.confianza.colaboradores.gcontenidos.server.util;

public enum EstadoGasto {

	SOLICITADO("S"),
	RESUELTO("R");
	
	public String valor;
	
	private EstadoGasto(String valor) {
		this.valor = valor;
	}
	
}
