package pe.confianza.colaboradores.gcontenidos.server.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestPresupuestoGasto extends RequestAuditoriaBase {

	private String usuarioBT;
	private String nombrePresupuesto;
}
