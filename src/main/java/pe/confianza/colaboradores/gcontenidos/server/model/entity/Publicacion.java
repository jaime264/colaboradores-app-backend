package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "publicacion")
public class Publicacion implements Serializable{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private Long fecha;
	private Long fechaInicio;
	private Long fechaFin; 
	private String detalle;
	private Integer flgmultimedia;
	private Integer flgpermanente;
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public Long getFecha() {
		return fecha;
	}



	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}



	public Long getFechaInicio() {
		return fechaInicio;
	}



	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}



	public Long getFechaFin() {
		return fechaFin;
	}



	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}



	public String getDetalle() {
		return detalle;
	}



	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}



	public Integer getFlgmultimedia() {
		return flgmultimedia;
	}



	public void setFlgmultimedia(Integer flgmultimedia) {
		this.flgmultimedia = flgmultimedia;
	}



	public Integer getFlgpermanente() {
		return flgpermanente;
	}



	public void setFlgpermanente(Integer flgpermanente) {
		this.flgpermanente = flgpermanente;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
