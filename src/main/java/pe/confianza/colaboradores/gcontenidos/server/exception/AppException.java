package pe.confianza.colaboradores.gcontenidos.server.exception;

public class AppException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException() {
		super();
	}
	
	public AppException(String mensaje) {
		super(mensaje);
	}
	
	public AppException(String mensaje,Throwable throwable) {
		super(mensaje, throwable);
	}

}