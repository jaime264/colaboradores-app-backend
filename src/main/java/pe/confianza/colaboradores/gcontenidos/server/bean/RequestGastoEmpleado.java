package pe.confianza.colaboradores.gcontenidos.server.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestGastoEmpleado extends RequestAuditoriaBase {

	private String usuarioBt;
	private Long idEmpleado;
	private Long idAgencia;
	private Long idGastoTipo;
	private Long idConceptoDetalle;
	private Integer monto;
	private Boolean terminosCondiciones;
	private String motivo;
}
