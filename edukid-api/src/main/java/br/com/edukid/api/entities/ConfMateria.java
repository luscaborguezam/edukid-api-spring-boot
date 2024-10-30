package br.com.edukid.api.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * CLASSE REPRESENTA A TABELA USER
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
@Entity
@Table(name="conf_materia")
public class ConfMateria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_user_filho")
	private Integer idUserChild;
	@Column(name="id_materia")
	private Integer idSubject;
	@Column(name = "quantidade_questoes")
	private Integer quantityQuestions;
	
	public ConfMateria(Integer idUserChild, Integer idSubject, Integer quantityQuestions) {
		this.idUserChild = idUserChild;
		this.idSubject = idSubject;
		this.quantityQuestions = quantityQuestions;
		
	}
	public Integer getIdUserChild() {
		return idUserChild;
	}
	public void setIdUserChild(Integer idUserChild) {
		this.idUserChild = idUserChild;
	}
	public Integer getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Integer idSubject) {
		this.idSubject = idSubject;
	}
	public Integer getQuantityQuestions() {
		return quantityQuestions;
	}
	public void setQuantityQuestions(Integer quantityQuestions) {
		this.quantityQuestions = quantityQuestions;
	}

}
