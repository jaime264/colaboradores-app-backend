package pe.confianza.colaboradores.gcontenidos.server.bean;

import java.io.Serializable;

public class CabeceraCTS implements Serializable{
		private int numero;
		private String descripcion;
		private Long fechaIni;
		private Long fechaFin;
		private Long fechaPago;
		private String estado;
		private String estadoCTS;
		private String periodoPlanilla;
		
		
		
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public Long getFechaIni() {
			return fechaIni;
		}
		public void setFechaIni(Long fechaIni) {
			this.fechaIni = fechaIni;
		}
		public Long getFechaFin() {
			return fechaFin;
		}
		public void setFechaFin(Long fechaFin) {
			this.fechaFin = fechaFin;
		}
		public Long getFechaPago() {
			return fechaPago;
		}
		public void setFechaPago(Long fechaPago) {
			this.fechaPago = fechaPago;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public String getEstadoCTS() {
			return estadoCTS;
		}
		public void setEstadoCTS(String estadoCTS) {
			this.estadoCTS = estadoCTS;
		}
		public String getPeriodoPlanilla() {
			return periodoPlanilla;
		}
		public void setPeriodoPlanilla(String periodoPlanilla) {
			this.periodoPlanilla = periodoPlanilla;
		}
		
		public int getNumero() {
			return numero;
		}
		public void setNumero(int numero) {
			this.numero = numero;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 3673304877008899026L;

}
