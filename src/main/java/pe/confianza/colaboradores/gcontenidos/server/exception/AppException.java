package pe.confianza.colaboradores.gcontenidos.server.exception;

public class AppException extends RuntimeException {
	
	private String titulo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException() {
		super();
		this.titulo = null;
	}
	
	public AppException(String mensaje) {
		super(mensaje);
		this.titulo = null;
	}
	
	public AppException(String mensaje, String titulo) {
		super(mensaje);
		this.titulo = titulo;
	}
	
	public AppException(String mensaje,Throwable throwable) {
		super(mensaje, throwable);
		this.titulo = null;
	}
	
	public AppException(String mensaje, String titulo, Throwable throwable) {
		super(mensaje, throwable);
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	

}