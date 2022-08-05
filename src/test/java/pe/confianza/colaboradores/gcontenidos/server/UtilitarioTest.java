package pe.confianza.colaboradores.gcontenidos.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.apache.poi.util.IOUtils;
import org.junit.jupiter.api.Test;

import pe.confianza.colaboradores.gcontenidos.server.util.MesesAnio;
import pe.confianza.colaboradores.gcontenidos.server.util.ParametroUnidad;
import pe.confianza.colaboradores.gcontenidos.server.util.Utilitario;
import pe.confianza.colaboradores.gcontenidos.server.util.file.collection.Row;
import pe.confianza.colaboradores.gcontenidos.server.util.file.read.ColumnType;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.IReport;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.Report;
import pe.confianza.colaboradores.gcontenidos.server.util.file.write.ReportFactory;

public class UtilitarioTest {
	
	@Test
	public void calcualrDerecho() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("09/08/1999", formatter);
		LocalDate fechaCorte = LocalDate.parse("31/05/2022", formatter);
		System.out.println(fechaIngreso + " " + fechaCorte);
		double derecho = Utilitario.calcularDerechoVacaciones(fechaIngreso, fechaCorte);
		System.out.println(derecho);
	}
	
	@Test
	public void calcualrDerecho1() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaIngreso = LocalDate.parse("01/11/2012", formatter);
		LocalDate fechaCorte = LocalDate.parse("31/05/2022", formatter);
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
	
	@Test
	public void addDays() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		int diasAgregar = 8;
		LocalDate fechaIngreso = LocalDate.parse("01/01/2018", formatter);
		LocalDate fechaAdd = Utilitario.agregarDias(fechaIngreso, diasAgregar);
		assertEquals(diasAgregar, Utilitario.obtenerDiferenciaDias(fechaIngreso, fechaAdd));
	}
	
	@Test
	public void lessDays() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		int diasQuitar = 8;
		LocalDate fechaIngreso = LocalDate.parse("08/01/2018", formatter);
		LocalDate fechaLess = Utilitario.quitarDias(fechaIngreso, diasQuitar);
		assertEquals(diasQuitar, Utilitario.obtenerDiferenciaDias(fechaLess, fechaIngreso));
	}
	
	@Test
	public void fechaEntrePeriodo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaInicio = LocalDate.parse("02/08/2022", formatter);
		LocalDate fechaFIn = LocalDate.parse("31/08/2022", formatter);
		LocalDate fecha = LocalDate.parse("31/08/2022", formatter);
		LocalDate fecha2 = LocalDate.parse("19/09/2022", formatter);
		System.out.println(Utilitario.fechaEntrePeriodo(fechaInicio, fechaFIn, fecha2));
	}
	
	@Test
	public void mensaje() {
		String mensaje = "Estimado Colaborador, Ud puede reprogramar sus vacaciones del mes XX.";
		String res = Utilitario.generarMensajeNotificacion(mensaje, MesesAnio.buscarPorValor(LocalDate.now().getMonthValue() + 1).descripcion);
		System.out.println(res);
	}
	
	@Test
	public void validarParam() {
		boolean es =ParametroUnidad.esValidoValor(ParametroUnidad.NUMERO_GENERAL, "-1");
		System.out.println(es);
	}
	
	/*@Test
	public void generarReporte() {
		Report reporte = new Report();
		reporte.setType("XLSX");
		reporte.setTitle("REPORTE VACACIONES");
		reporte.getCollection().addHeader("Colaborador", ColumnType.STRING);
		reporte.getCollection().addHeader("Agencia", ColumnType.STRING);
		Row row = new Row();
		row.addCell("Colaborador", "Juan Perez");
		row.addCell("Agencia", "AAAA");
		reporte.getCollection().setCurrentRow(row);
		reporte.getCollection().addRow();
		row = new Row();
		row.addCell("Colaborador", "Luis Perez");
		row.addCell("Agencia", "BBB");
		reporte.getCollection().setCurrentRow(row);
		reporte.getCollection().addRow();
		ReportFactory reportFactory = new ReportFactory();
		IReport<ByteArrayInputStream> excel = reportFactory.createReport(reporte);
		try {
			excel.build();
			IOUtils.copy(excel.getReult(), new FileOutputStream("/kenyo/reporte1.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}*/

}
