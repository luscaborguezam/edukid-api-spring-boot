package br.com.edukid.api.entities.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ConfMateriaId implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_user_filho")
	private Integer idUserChild;
	@Column(name="id_materia")
	private Integer idSubject;
	
	public ConfMateriaId() {
		// TODO Auto-generated constructor stub
	}
	
	public ConfMateriaId(Integer idUserChild, Integer idSubject) {
		this.idUserChild = idUserChild;
		this.idSubject= idSubject;
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
	


    
}
