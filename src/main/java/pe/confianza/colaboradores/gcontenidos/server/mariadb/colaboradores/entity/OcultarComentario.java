package pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "ocultar_comentario")
public class OcultarComentario extends EntidadAuditoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String idUsuario;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(nullable = true, name = "idComentario")
	private Comentario comentario;
	
	@JsonInclude
	@Transient
	private Long comentarioId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public Long getComentarioId() {
		return comentarioId;
	}

	public void setComentarioId(Long comentarioId) {
		this.comentarioId = comentarioId;
	}
	
	
}
