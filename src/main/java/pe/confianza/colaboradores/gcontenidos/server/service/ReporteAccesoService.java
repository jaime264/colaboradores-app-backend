package pe.confianza.colaboradores.gcontenidos.server.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity.ReporteAcceso;

public interface ReporteAccesoService {
	
	ReporteAcceso registrar(ReporteAcceso acceso, String usuarioOperacion);
	
	ReporteAcceso buscarPorId(long id);
	
	ReporteAcceso actualizar(long id, LocalDate fechaEnvio, String usuarioOperacion);
	
	void eliminar(long id);
	
	Page<ReporteAcceso> listar(Long idPuesto, Pageable pageable);

}
