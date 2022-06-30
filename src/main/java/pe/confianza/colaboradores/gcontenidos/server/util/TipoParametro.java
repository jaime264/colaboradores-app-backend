package pe.confianza.colaboradores.gcontenidos.server.util;

public enum TipoParametro {
	
	GENERICO("TPAR001"),
	VACACION("TPAR002"),
	PUBLICACION("TPAR003");
	
	public String codigo;
	
	private TipoParametro(String codigo) {
		this.codigo = codigo;
	}
	
	

}
