package pe.confianza.colaboradores.gcontenidos.server.util;

public enum EstadoMigracion {
	
	IMPORTADO("I"),
	NUEVO("N"),
	MODIFICADO("M"),
	EXPORTADO("E");
	
	public String valor;
	
	private EstadoMigracion(String valor) {
		this.valor = valor;
	}

}
