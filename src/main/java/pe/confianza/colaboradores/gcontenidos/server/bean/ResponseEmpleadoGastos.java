package pe.confianza.colaboradores.gcontenidos.server.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseEmpleadoGastos {

	private long id;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String usuarioBt;
	private Long codigo;
	private String agencia;
	private Long angenciaId;
	private String cuentaAhorro;
	private String linkTerminos;
	
}
