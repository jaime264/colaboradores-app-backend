package pe.confianza.colaboradores.gcontenidos.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;

public class UtilitarioTest {
	
	@Test
	public void calcualrDerecho() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("09/08/1999", formatter);
		LocalDate fechaCorte = LocalDate.parse("30/04/2022", formatter);
		System.out.println(fechaIngreso + " " + fechaCorte);
		double derecho = Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaCorte);
		System.out.println(derecho);
	}
	
	@Test
	public void calcualrDerecho1() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("09/11/1999", formatter);
		LocalDate fechaCorte = LocalDate.parse("30/04/2022", formatter);
		System.out.println(fechaIngreso + " " + fechaCorte);
		double derecho = Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaCorte);
		System.out.println(derecho);
	}
	
	@Test
	public void calcualrDerecho2() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("01/01/2018", formatter);
		LocalDate fechaCorte = LocalDate.parse("01/05/2022", formatter);
		System.out.println(fechaIngreso + " " + fechaCorte);
		double derecho = Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaCorte);
		System.out.println(derecho);
	}
	
	@Test
	public void calcularPeriodos() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("01/05/2022", formatter);
		LocalDate[] periodoTrunco = Utilitario.rangoPeriodoTrunco(fechaIngreso);
		LocalDate[] periodoVencido = Utilitario.rangoPeriodoVencido(fechaIngreso);
		assertEquals(periodoVencido, null);
		assertEquals(periodoTrunco[0].getYear(), 2022);
		assertEquals(periodoTrunco[1].getYear(), 2023);
		System.out.println("Fecha Ingreso: "  + fechaIngreso);
		System.out.println("Periodo Trunco: " +Arrays.toString(periodoTrunco));
		System.out.println("Periodo Vencido: " + Arrays.toString(periodoVencido));
	}
	
	@Test
	public void calcularPeriodos2() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("01/01/2018", formatter);
		LocalDate[] periodoTrunco = Utilitario.rangoPeriodoTrunco(fechaIngreso);
		LocalDate[] periodoVencido = Utilitario.rangoPeriodoVencido(fechaIngreso);
		assertEquals(periodoVencido[0].getYear(), 2021);
		assertEquals(periodoVencido[1].getYear(), 2021);
		assertEquals(periodoTrunco[0].getYear(), 2022);
		assertEquals(periodoTrunco[1].getYear(), 2022);
		System.out.println("Fecha Ingreso: "  + fechaIngreso);
		System.out.println("Periodo Trunco: " +Arrays.toString(periodoTrunco));
		System.out.println("Periodo Vencido: " + Arrays.toString(periodoVencido));
	}

}
