package pe.confianza.colaboradores.gcontenidos.server.bean;

import lombok.Getter;
import lombok.Setter;
import pe.confianza.colaboradores.gcontenidos.server.RequestPaginacion;

@Getter
@Setter
public class RequestListarReportes extends RequestPaginacion {

	private String usuarioBT;
	private String codigoUsuario;

}
