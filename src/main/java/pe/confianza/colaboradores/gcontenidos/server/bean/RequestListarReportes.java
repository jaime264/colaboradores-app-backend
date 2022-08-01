package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import pe.confianza.colaboradores.gcontenidos.server.RequestPaginacion;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

@Getter
@Setter
public class RequestListarReportes extends RequestPaginacion {

	private String usuarioBT;
	private String codigoUsuario;
	private String tipoFiltro;
	private List<String> filtro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constantes.FORMATO_FECHA, timezone = "America/Bogota")
	private LocalDate fechaFin;

}
