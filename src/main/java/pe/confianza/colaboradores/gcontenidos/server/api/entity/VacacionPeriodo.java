package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacacionPeriodo {


	private Long idProgramacion;
	private String periodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Integer idEstado;
	private String aprobadorBt;

	

}
