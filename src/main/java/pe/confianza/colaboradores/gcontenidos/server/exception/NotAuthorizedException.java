package pe.confianza.colaboradores.gcontenidos.server.exception;

public class NotAuthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NotAuthorizedException(String usuario) {
		super(usuario + " no concuerda con el usuario de sesión");
	}

}
