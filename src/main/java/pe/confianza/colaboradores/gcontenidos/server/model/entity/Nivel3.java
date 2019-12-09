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
@Table(name = "nivel3")
public class Nivel3 implements Serializable{	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_nivel2")
	private Nivel2 nivel2;
	
	
	
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



	public Nivel2 getNivel2() {
		return nivel2;
	}


	public void setNivel2(Nivel2 nivel2) {
		this.nivel2 = nivel2;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
