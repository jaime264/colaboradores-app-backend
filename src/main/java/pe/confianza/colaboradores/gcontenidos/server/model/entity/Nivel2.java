package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nivel2")
public class Nivel2 implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_nivel1")
	private Nivel1 nivel1;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Nivel1 getNivel1() {
		return nivel1;
	}


	public void setNivel1(Nivel1 nivel1) {
		this.nivel1 = nivel1;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
