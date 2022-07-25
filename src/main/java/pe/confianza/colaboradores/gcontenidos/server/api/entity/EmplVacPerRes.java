package pe.confianza.colaboradores.gcontenidos.server.api.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Corredor;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.Territorio;
import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.UnidadOperativa;

@Getter
@Setter
public class EmplVacPerRes {

	private String nombres;
	private String puesto;
	private Long idEmpleado;
	private String usuarioBt;
	private Long idProgramacion;
	private String periodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Integer idEstado;
	private String descripcionEstado;
	private String leyendaEstado;

	@JsonIgnore
	private String agencia;
	@JsonIgnore
	private String territorio;
	@JsonIgnore
	private String corredor;
	
	private boolean adelantada;

}
