package pe.confianza.colaboradores.gcontenidos.server.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reaccion")
public class Reaccion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int flgreaccion;
	private int like;
	private Long contadorLike;
	private int nolike;
	private Long contadorNoLike;

	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getFlgreaccion() {
		return flgreaccion;
	}



	public void setFlgreaccion(Integer flgreaccion) {
		this.flgreaccion = flgreaccion;
	}



	public Integer getLike() {
		return like;
	}



	public void setLike(Integer like) {
		this.like = like;
	}



	public Long getContadorLike() {
		return contadorLike;
	}



	public void setContadorLike(Long contadorLike) {
		this.contadorLike = contadorLike;
	}



	public Integer getNolike() {
		return nolike;
	}



	public void setNolike(Integer nolike) {
		this.nolike = nolike;
	}



	public Long getContadorNoLike() {
		return contadorNoLike;
	}



	public void setContadorNoLike(Long contadorNoLike) {
		this.contadorNoLike = contadorNoLike;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

}
