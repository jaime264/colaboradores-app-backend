package pe.confianza.colaboradores.gcontenidos.server.util;

public enum ProcesoMigracionEstado {
	
	REGISTRADO("R"),
	PROCESADO("P"),
	ERROR("E");
	
	public String valor;
	
	private ProcesoMigracionEstado(String valor) {
		this.valor = valor;
	}

}
