package pe.confianza.colaboradores.gcontenidos.server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

public class UtilitarioTest {
	
	@Test
	public void calcualrDerecho() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("09/08/1999", formatter);
		LocalDate fechaCorte = LocalDate.parse("06/06/2022", formatter);		
		double derecho = Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaCorte);
		System.out.println(derecho);
	}

}
