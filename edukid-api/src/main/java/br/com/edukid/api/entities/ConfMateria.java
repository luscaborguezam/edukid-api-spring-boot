package br.com.edukid.api.entities;

import java.io.Serializable;

import br.com.edukid.api.entities.ids.ConfMateriaId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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
	
    @EmbeddedId
    private ConfMateriaId id;
    
	@Column(name = "quantidade_questoes")
	private Integer quantityQuestions;
	
	public ConfMateria() {
		id = new ConfMateriaId();
	}
	
	public ConfMateria(Integer idUserChild, Integer idSubject, Integer quantityQuestions) {
		id.setIdUserChild(idUserChild);
		id.setIdSubject(idSubject);
		this.quantityQuestions = quantityQuestions;
		
	}
	
	public ConfMateriaId getId() {
		return id;
	}
	public void setId(ConfMateriaId id) {
		this.id = id;
	}
	public Integer getQuantityQuestions() {
		return quantityQuestions;
	}
	public void setQuantityQuestions(Integer quantityQuestions) {
		this.quantityQuestions = quantityQuestions;
	}

}
